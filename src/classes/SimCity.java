/**
 * 
 */
package classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cities.Big;
import cities.City;
import cities.Minden;
import conf.CarColoring;
import conf.Conf;

/**
 * @author Bloodyhand
 *
 */
public class SimCity extends Container {
	
	private static final long serialVersionUID = 0;
		
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Street> streets = new ArrayList<Street>();
	private ArrayList<Station> stations = new ArrayList<Station>();
	private ArrayList<Car> cars = new ArrayList<Car>();
	
	private boolean withThreads;
	
	private double dimX,dimY;
	
	private CarColoring coloring;
	
	private City city;
	
	private Timer t;
	
	public SimCity(City city) {
		this(city, false);
	}
	
	public SimCity(City city, boolean withThreads) {
		this(city, withThreads, CarColoring.getDefault());
	}
	
	public SimCity(City city, CarColoring coloring) {
		this(city, false, coloring);
	}
	
	public SimCity(City city, boolean withThreads, CarColoring coloring) {
		this.withThreads = withThreads;
		dimX = dimY = 0;
		this.coloring = coloring;
		this.city = city;
	}
	
	public void init() {
		// Größe festlegen
		//setSize(getPreferredSize());
		// Frame zum Darstellen erzeugen
		JFrame frame = new JFrame();
		frame.setTitle("SimCity");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scroll = new JScrollPane(this);
		scroll.setPreferredSize(getPreferredSize());
		
		initMenu(scroll, this);
		
		
		frame.add(scroll, BorderLayout.CENTER);
		
		/**
		 * Hinzufügen der Componenten.
		 * Wird hier gemacht und nicht in addNode, da die
		 * Street-Componenten VOR den Nodes gezeichnet werden
		 * sollen; die wird erreicht indem man sie zuletzt zum 
		 * Container SimCity hinzufügt.
		 */
		for (Street s:streets) {
			this.add(s);
		}
		
		// Zooming
		scroll.addMouseWheelListener(new Zoomer(this));
		// noch nicht fertig
		this.addMouseMotionListener(new Mover(scroll));
		
		// Frame anpassen + Layouten
		frame.pack();
		// Anzeigen
		frame.setVisible(true);
	}
	
	private class Zoomer implements MouseWheelListener {
		SimCity s;
		public Zoomer(SimCity s) {
			this.s=s;
		}
		
		@Override
		public synchronized void mouseWheelMoved(MouseWheelEvent e) {
			double step = ((double)e.getWheelRotation())/10;
			if (Conf.pixelPerMeter+step < Conf.MAX_ZOOM && Conf.pixelPerMeter+step > Conf.MIN_ZOOM) {
				Conf.pixelPerMeter += step;
				setSize(getPreferredSize());
				//s.doLayout();
				s.validate();
			}
		}
	}
	
	/**
	 * Soll später mal das Stadtgebiet bei gedrückter Maus
	 * im ScrollPane umherschieben damit nach Zoom auch hin- und
	 * hergeschaut werden kann.
	 * Status: NICHT FERTIG
	 * @author CreaByte
	 *
	 */
	private class Mover extends MouseAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			/*if (oldx == Integer.MAX_VALUE) {
				oldx = e.getX();
				oldy = e.getY();
			}
			
			Rectangle a = p.getViewportBorderBounds();
			System.out.println("vorher:"+a);
			//a.x -= oldx - e.getX();
			//a.y -= oldy - e.getY();
			a.x = e.getX();
			a.y = e.getY();
			
			p.scrollRectToVisible(a);
			System.out.println("nachher:"+a);
			
			oldx = e.getX();
			oldy = e.getY();*/
		}

		private boolean isPressed=false;
		private int oldx=Integer.MAX_VALUE,oldy=0;
		private JScrollPane p;
		
		public Mover(JScrollPane pane) {
			p = pane;
		}

		/*@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("move");
			if (isPressed) {
				if (oldx == Integer.MAX_VALUE) {
					oldx = e.getX();
					oldy = e.getY();
				}
				
				Rectangle a = p.getViewportBorderBounds();
				System.out.println(a);
				a.x += oldx - e.getX();
				a.y += oldy - e.getY();
				System.out.println(a);
				p.scrollRectToVisible(a);
				System.out.println(a);
				
				oldx = e.getX();
				oldy = e.getY();
			}
		}*/

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				isPressed = true;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			isPressed = false;
		}
		
	}
	
	/**
	 * Andy: Hier kann man ein Menü basteln, das über der City angezeigt wird;
	 * wäre evtl später ganz sinnvoll um einstellungen aus Constants.* irgendwie
	 * zur laufzeit per menü einstellen zu können!
	 * @param pane
	 */
	private void initMenu(JScrollPane pane, SimCity city) {
		final SimCity city2 = city;
		
		JPanel p = new JPanel();
		//p.setSize(new Dimension(100,100));
		JButton btnExit = new JButton();
		btnExit.setText("Ende");
		btnExit.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}});
		
		
		JButton btnStop = new JButton();
		btnStop.setText("Pause");
		
		btnStop.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				city2.stopSimCity();
			}});
		
		
		JButton btnStart = new JButton();
		btnStart.setText("Fortsetzen");
		btnStart.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				city2.startSimCity();
			}});
		
		final JComboBox box = new JComboBox();
		for (CarColoring c:CarColoring.values()) {
			box.addItem(c);
		}
		// Aktuelles Coloring einstellen
		box.setSelectedItem(coloring);
		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setColoring(CarColoring.values()[box.getSelectedIndex()]);
			}
			
		});
		
		
		p.add(btnStart);
		p.add(btnStop);
		p.add(btnExit);
		p.add(box);
		pane.setColumnHeaderView(p);	
	}
	
	public void startSimCity() {
		startSimCity(Conf.defaultTimeStep);
	}
	
	private class painterThread extends Thread {
		private SimCity c;
		private long intvl;
		public painterThread(SimCity c, long intvl) {
			this.c = c;
			this.intvl = intvl;
		}
		
		public void run() {
			try {
				sleep(intvl);
				c.validate();
			} catch(InterruptedException e) {
				
			}
		}
		
	}
	
	public void startSimCity(long timeInterval) {
		if (withThreads) {
			// Painter
			//new painterThread(this,timeInterval).start();
			
			for (Station s:stations) {
				s.setTimeInterval(timeInterval);
				new Thread(s).start();
			}
		} else {
			t = new Timer();
			t.scheduleAtFixedRate(new TimerTask(){ public void run(){ timeTick(); }}, 0, timeInterval);
		}
		
	}
	
	public void stopSimCity() {
		if (withThreads) {
			for (Station s:stations) {
				s.stop();
			}
			for (Car c:cars) {
				c.stop();
			}
		} else {
			t.cancel();
		}
	}
	
	/**
	 * Run-Methode für Simulation ohne Threads
	 */
	public void timeTick() {
		for (Station s:stations) {
			s.timeTick();
		}
		for (Street s:streets) {
			s.timeTick();
		}	
	}
	
	public boolean isThreadedSimulation() {
		return withThreads;
	}

	public void addNode(Node n) {
		nodes.add(n);
		
		// Zum SimCity-Container hinzufügen
		//this.add(n);
		
		n.setSimCity(this);
		if (n instanceof Station) {
			stations.add((Station)n);
		}
		for (Street s:n.streets) {
			if (!streets.contains(s)) {
				streets.add(s);
			}
		}
		// Layoutgröße anpassen
		double newx = n.getRealX() + n.getRealWidth()/2 + Conf.border;
		double newy = n.getRealY() + n.getRealHeight() + Conf.border; 
		if (newx > dimX) dimX = newx;
		if (newy > dimY) dimY = newy;
	}
	
	public void addCar(Car c){
		cars.add(c);
	}
	
	public City getCity() {
		return city;
	}
	
	/**
	 * Ändert die aktuelle Colorierung für alle Autos
	 * die da sind und noch entstehen
	 * @param color
	 */
	public void setColoring(CarColoring color) {
		coloring = color;
		for (Car car: new ArrayList<Car>(cars)) {
			car.newColoring(color);
		}
	}
	
	public ArrayList<Node> getNodes(){
		return nodes;
	}
	public ArrayList<Street> getStreets(){
		return streets;
	}	
	public ArrayList<Car> getCars(){
		return cars;
	}
	public ArrayList<Station> getStations(){
		return stations;
	}
	
	/**
	 * Wählt eine zufällige Station aus unter evtl.
	 * ausschluss einer Startstation
	 * 
	 * @param source null für keine, sonst zu vermeidende Station
	 * @param onlyEnds legt fest ob nur stationen die selber keine autos erzeugen
	 * 				   gewählt werden sollen
	 * @return zufällige Station in der City
	 */
	public Station getRandomStation(Station source, boolean onlyEnds) {
		Station res=null;
		do {
			res = stations.get(Conf.random(stations.size()-1));
		} while (res == source || (onlyEnds && res.isSpawning()));
		return res;
	}	
	
	public CarColoring getCarColoring() {
		return coloring;
	}
	
	/**
	 * Berechnet seine eigene Größe anhand der
	 * vorhandenen Nodes und aktualisiert die
	 * Orte der Nodes.
	 * 
	 * Wird rekursiv durch alle Container automatisch
	 * bei validate() aufgerufen
	 */
	@Override
	public void doLayout() {
		//System.out.println("SimCity:doLayout()");
		//double maxX = 0, maxY = 0;
		int x,y;
		// Alle Nodes platzieren
		for (Node n:nodes) {
			// Node platzieren
			x = Conf.convertToPixel(n.getRealX() - n.getRealWidth()/2);
			y = Conf.convertToPixel(n.getRealY() - n.getRealHeight()/2);
			n.setBounds(x,y,Conf.convertToPixel(n.getRealWidth()),Conf.convertToPixel(n.getRealHeight()));
		}
		// Die Größe wird von allen Nodes bestimmt, also jetzt die Gesamtgröße festlegen
		//this.setSize((int)(maxX * Constants.pixelPerMeter), (int)(maxY * Constants.pixelPerMeter));
		
		/**
		 * Alle Strassen platzieren
		 * Dabei müssen die Strassen immer im kompletten
		 */
		int xleft,ytop,xright,ybottom;
		Node A,B;
		for (Street s:streets) {
			A = s.getCrossingA();
			B = s.getCrossingB();
			/**
			 * Eckpunkte ganz oben links und unten rechts bestimmen
			 * Ist notwendig, damit die Strasse richtig drin zeichnen kann.
			 */ 
			xleft = Conf.convertToPixel(Math.min(A.getRealX()-A.getRealWidth()/2, B.getRealX()-B.getRealWidth()/2));
			ytop = Conf.convertToPixel(Math.min(A.getRealY()-A.getRealHeight()/2, B.getRealY()-B.getRealHeight()/2));
			xright = Conf.convertToPixel(Math.max(A.getRealX()-A.getRealWidth()/2, B.getRealX()-B.getRealWidth()/2));
			ybottom = Conf.convertToPixel(Math.max(A.getRealY()-A.getRealHeight()/2, B.getRealY()-B.getRealHeight()/2));
			s.setBounds(xleft,ytop,
					    // Differenz plus den mittleren Abstand
						xright-xleft+(A.getWidth()+B.getWidth())/2,
						ybottom-ytop+(A.getHeight()+B.getHeight())/2);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		// Markieren der Stadt durch ein Rechteck
		//g.setColor(Color.RED);
		//g.drawRect(1, 1, (int)getPreferredSize().getWidth()-2, (int)getPreferredSize().getHeight()-2);
		
		// TODO: Hier evtl. Hintergrundbild zeichnen
		
		/**
		 * Alle enthaltenen Komponenten zeichnen
		 */
		super.paint(g);
		
		/**
		 * Double-Buffer:
		 */
		/*if(offscreen == null) {
	         offscreen = createImage(getSize().width, getSize().height);
	      }
	      Graphics og = offscreen.getGraphics();
	      og.setClip(0,0,getSize().width, getSize().height);
	      super.paint(og);
	      g.drawImage(offscreen, 0, 0, null);
	      og.dispose();*/
		
		/**
		 * Strassenrahmen
		 */
		/*for (Street s:streets) {
		g.setColor(Color.CYAN);
		g.drawRect(s.getX(), s.getY(), s.getWidth(), s.getHeight());
		}*/

	}
	
	@Override
	public Dimension getPreferredSize() {
		// Die Größe wird von allen Nodes bestimmt, also jetzt die Gesamtgröße festlegen
		return new Dimension((int)(dimX * Conf.pixelPerMeter), (int)(dimY * Conf.pixelPerMeter));
    }
	
}

