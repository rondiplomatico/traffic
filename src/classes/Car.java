/**
 * 
 */
package classes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import cities.City;

import ki.KIAlgorithm;
import ki.PathAlgorithm;
import conf.CarColoring;
import conf.Conf;
import conf.DriverBehaviour;
import conf.IDriverBehaviour;

/**
 * @author Bloodyhand TODO: Überholen / Lanewechsel TODO:
 *         Geschwindigkeitsbegrenzung für Strassen TODO: Abbremsen beim Nähern
 *         auf eine Kreuzung wenn man abbiegt und nicht geradeaus fährt
 */
public class Car extends Component implements Runnable {

	private static final long serialVersionUID = 0;
	private static int carCnt = 0;
	private static final int VARIANT = 1;

	// Umgebung des Autos
	private Node lastNode, nextNode;
	private Station target;
	private City city;
	private Lane lane;

	private double pos, length, streetlength, lastPixelPerMeter;
	private String name;
	private int nr;

	private CarColoring coloring;

	private Image img;

	private Timer t;

	// Bewegungsvariablen
	private double v;

	private PathAlgorithm alg;
	private IDriverBehaviour behav;

	public Car(Station startNode, Station target) {
		this(startNode, target, "");
	}

	public Car(Station startNode, Station target, String name) {
		this(startNode, target, KIAlgorithm.Default, name);
	}

	public Car(Station startNode, Station target, CarColoring coloring) {
		this(startNode, target, KIAlgorithm.Default, coloring);
	}

	public Car(Station startNode, Station target, KIAlgorithm alg,
			CarColoring coloring) {
		this(startNode, target, alg, null, coloring);
	}

	public Car(Station startNode, Station target, KIAlgorithm alg, String name) {
		this(startNode, target, alg, name, CarColoring.getDefault());
	}

	public Car(Station startNode, Station target, KIAlgorithm alg, String name,
			CarColoring coloring) {
		this(startNode, target, alg, name, coloring, Conf.carLength);
	}

	public Car(Station startNode, Station target, KIAlgorithm alg, String name,
			CarColoring coloring, double length) {
		carCnt++;

		// GUI
		if (name == null) {
			this.name = "Foo" + carCnt;
		} else
			this.name = name;
		this.length = length;
		this.nr = carCnt;
		this.coloring = coloring;
		this.lastPixelPerMeter = Conf.pixelPerMeter;

		// Logic
		this.pos = 0;
		this.alg = alg.createAlgorithm();
		this.lastNode = startNode;
		this.city = startNode.getSimCity().getCity();
		this.target = target;

		// Verhalten
		behav = DriverBehaviour.Default;
		v = 0;
	}

	/**
	 * Erlaubt es das Verhalten des Autos / "Fahrers" genauer festzulegen.
	 * 
	 * @see IDriverBehaviour
	 * @param b
	 */
	public void setBehaviour(IDriverBehaviour b) {
		behav = b;
	}

	public void setLane(Lane value) {
		lane = value;
		streetlength = value.getStreet().getRealLength();
		pos = 0;
	}

	public PathAlgorithm Algorithm() {
		return alg;
	}

	public double getLength() {
		return length;
	}

	@Override
	public int getWidth() {
		return Conf.convertToPixel(length);
	}

	@Override
	public int getHeight() {
		return Conf.convertToPixel(Conf.carWidth);
	}

	public Lane getLane() {
		return lane;
	}

	public double getPos() {
		return pos;
	}

	public String getName() {
		return name;
	}

	/**
	 * Startmethode die bei ThreadSimulation benutzt wird
	 */
	@Override
	public void run() {
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				move();
			}
		}, 0, Conf.defaultTimeStep);
	}

	/**
	 * Hält das Auto bei Threadbasierter ausführung an
	 */
	public void stop() {
		if (t != null)
			t.cancel();
	}

	public double getSpeed() {
		return v;
	}

	public void newColoring(CarColoring coloring) {
		this.coloring = coloring;
		this.createImage();
		repaint();
	}

	/**
	 * Hauptmethode der Autos um einen Simulationsschritt durchzuführen. Ändert
	 * Position und GEschwindigkeit gemäß der Differentialgleichung dx/dt = v_i
	 * dv/dt = (v_0-v) / (t/T) - (a_i*T)/V * (x_(i+1) - x_i - d_i)^-c_i
	 * 
	 * @see Lane#timeTick()
	 * @see Car#run()
	 */
	public void move() {
		// Position ändern
		pos += dxdt();
		// Car ahead
		if (lane.getPrecessor(this) != null) {
			Car pre = lane.getPrecessor(this);
			// Geschwindigkeit anpassen
			v += dvdt(pre.getPos());

			// Node ahead!
		} else {
			Street next = alg.getDirection(alg.nextNode());
			// Falls wir jetzt auf der Kreuzung stehen
			if (pos + (length / 2) > streetlength) {
				alg.nextNode().cross(this, next);
				pos = 0;
				alg.advance();
			}

			// Nachher: Geschwindigkeit anpassen
			if (streetlength - pos > Conf.eyeSight
					|| alg.nextNode().mayCross(this, lane.getStreet(), next)) {
				v += dvdt(Double.MAX_VALUE);
			} else {
				/*
				 * Falls man nicht kreuzen darf und zu nah dran ist, automatisch
				 * bremsen. dann kann es auch nicht vorkommen das man in eine
				 * kreuzung einfährt (also obiger code ausgeführt wird)
				 */
				v += dvdt(streetlength);
			}
		}
		// lane.repaint();
		/*
		 * if (name.equals("Foo1")) { System.out.println("x="+pos+", v="+v); }
		 */
	}

	/**
	 * Änderung der Position - "dx nach dt"
	 * 
	 * @return
	 */
	private double dxdt() {
		return v * city.getCharTime();
	}

	/**
	 * Änderung der Geschwindigkeit - "dv nach dt"
	 * 
	 * @param precessor_pos
	 *            Position des Vorgängers
	 * @return
	 */
	private double dvdt(double precessor_pos) {
		if (VARIANT == 1) {
			return (getWantedSpeed() - v)
					/ (behav.getRelaxation() / city.getCharTime())
					- ((behav.getAccRate() * city.getCharTime()) / (city
							.getCharSpeed()))
					* Math.pow(precessor_pos - pos - behav.getSafetyDistance(),
							-behav.getBreakIntensity());

		} else {
			/*
			 * (v_0 - v) / (tau / T)
			 */
			double v1 = (getWantedSpeed() - v)
					/ (behav.getRelaxation() / city.getCharTime());

			/*
			 * a* T / V
			 */
			double v2 = (behav.getAccRate() * city.getCharTime())
					/ city.getCharSpeed();
			// System.out.println("v2="+v2);
			/*
			 * (pre_pos - pos - d) ^ -c
			 */
			double v3 = Math.pow(
					precessor_pos - pos /*- behav.getSafetyDistance()*/,
					-behav.getBreakIntensity() * 3);
			// System.out.println("v3="+v3);
			return v1 - v2 * v3;
		}
	}

	/**
	 * Gibt die aktuell gewünschte Geschwindigkeit zurück. Entspricht dem
	 * Speedlimit auf der Strasse falls der Fahrer schneller fahren will.
	 * 
	 * @return
	 */
	private double getWantedSpeed() {
		return lane.getStreet().getSpeedLimit() < behav.getWantedSpeed() ? lane
				.getStreet().getSpeedLimit() : behav.getWantedSpeed();
	}

	public String toString() {
		return "'" + name + "' bei x=" + pos + ", v=" + v;
	}

	/**
	 * Erzeugt das Bild, das das Auto repräsentiert. Kann nicht im Konstruktor
	 * gemacht werden, da dann der Parent nicht feststeht und somit die methode
	 * Component.createImage null liefern würde.
	 */
	public void createImage() {
		// Autobild erzeugen
		img = this.createImage(getWidth(), getHeight());
		if (img != null) {
			Graphics g = img.getGraphics();
			float[] hsb = new float[3];
			switch (coloring) {
			case Fancy: {
				Color.RGBtoHSB(nr * 17 % 255, nr * 9 % 255, nr * 60 % 255, hsb);
				g.setColor(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
				g.fillRect(0, 0, getWidth(), getHeight());
			}
				break;
			case Algorithm: {
				g.setColor(alg.getColor());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
				break;
			case Image: {
			}
				break;
			case Target: {
				g.setColor(target.getColor());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
				break;
			case Behaviour: {
				g.setColor(behav.getColor());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
				break;
			default: {
			}
				;
			}

			g.setColor(Color.BLACK);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		} // else System.err.println("Konnte keine Autografik erzeugen: "+this);
	}

	public Image getImage() {
		if (lastPixelPerMeter != Conf.pixelPerMeter) {
			createImage();
			lastPixelPerMeter = Conf.pixelPerMeter;
		}
		return img;
	}
}
