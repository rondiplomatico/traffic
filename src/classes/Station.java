/**
 * 
 */
package classes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import ki.KIAlgorithm;
import conf.Conf;
import conf.DriverBehaviour;

/**
 * @author Bloodyhand
 *
 */
public class Station extends Node implements Runnable {
	
	private static final long serialVersionUID = 0;
	private boolean isThreaded=false;
	private Timer t;
	private Color color;
	
	/**
	 * Startmethode die bei ThreadSimulation benutzt wird
	 */
	@Override
	public void run() {
		isThreaded = true;
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() { 
			public void run(){ 
				timeTick(); 
				} 
			}, 0, timeInterval);
	}
	
	/**
	 * Stoppt bei Threadbasierter Ausführung den (Spawn-)Timer
	 */
	public void stop() {
		if (isThreaded) t.cancel();
	}

	private double spawnRate;
	private long timeInterval;
	private SimCity city;
	
	/**
	 * 
	 * @param city
	 * @param carSpawnRate
	 */
	public Station(SimCity city, double carSpawnRate) {
		this(city,carSpawnRate,null);
	}
	
	/**
	 * 
	 * @param city
	 * @param carSpawnRate
	 * @param name
	 */
	public Station(SimCity city, double carSpawnRate, String name) {
		this(city, carSpawnRate, name, 0, 0);
	}
	
	public Station(SimCity city, double carSpawnRate, String name, Color color) {
		this(city, carSpawnRate, name, 0, 0, color);
	}
	
	public Station(SimCity city, double carSpawnRate, String name, double xloc, double yloc) {
		this(city, carSpawnRate, name, xloc,yloc, carSpawnRate > 0 ? Color.GREEN:Color.RED);
	}
	
	public Station(SimCity city, double carSpawnRate, String name, double xloc, double yloc, Color color) {
		super(name, xloc, yloc);
		spawnRate = carSpawnRate;
		this.city = city;
		timeInterval = Conf.defaultTimeStep;
		this.color = color;
	}
	
	public void setTimeInterval(long interval) {
		timeInterval = interval;
	}

	@Override
	public boolean mayCross(Car c, Street from, Street to) {
		return true;
	}
	
	@Override
	public boolean cross(Car car, Street to) {
		//System.out.println("Auto "+car+" erreicht Endstation "+this);
		car.getLane().removeCar(car);
		car = null;
		return true;
	}

	/**
	 * Simuliert einen Zeitschritt.
	 * Wird einmal bei Simulation mit und ohne Threads benutzt.
	 */
	public void timeTick() {
		if (Math.random() < spawnRate) {
			
			// Ziel
			Station target = city.getRandomStation(this, true);
			
			// Neues Auto erzeugen
			KIAlgorithm alg = KIAlgorithm.getRandom();
			Car n = new Car(this, target, alg, city.getCarColoring());
			
			// Fahrerverhalten festlegen
			n.setBehaviour(DriverBehaviour.getRandom());
			
			
			
			n.Algorithm().Calculate(this, target);
			Street s = n.Algorithm().getDirection(this);
			if (s.mayAddCar(n, n.Algorithm().nextNode())) {
				s.addCar(n, n.Algorithm().nextNode());
				// Der Stadt hinzufügen
				city.addCar(n);
				
				// Falls mit Threads simuliert wird, das Auto starten!
				if (isThreaded) {
					new Thread(n).start();
				}
			} else n = null;
		}
	}
	
	public boolean isSpawning() {
		return spawnRate > 0;
	}
	
	public Color getColor() {
		return color;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.drawString((name.length() > 5 ? name.substring(0, 5):name), getWidth()/2, getHeight()/2);
	}
}
