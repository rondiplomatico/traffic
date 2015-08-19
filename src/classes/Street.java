/**
 * 
 */
package classes;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import conf.Conf;

/**
 * @author Bloodyhand
 *
 */
public class Street extends Container {
	private static final long serialVersionUID = 0;
	
	private Node A,B;
	private double length;
	private ArrayList<Lane> lanesAB, lanesBA;
	private int speedLimit;
	private double weight;
	
	// TODO: evtl. max geschwindigkeit hinzuf�gen. PRIO -500
	
	public Street(Node A, Node B) {
		this(A,B,1,1);
	}
	public Street(Node A, Node B, int speedLimit) {
		this(A,B,1,1, speedLimit);
	}
	public Street(Node A, Node B, int numLanesAB, int numLanesBA){
		this(A,B,numLanesAB,numLanesBA, Integer.MAX_VALUE);
		
	}	
	public Street(Node A, Node B, int numLanesAB, int numLanesBA, int speedLimit){
		if (A == B) {
			System.out.println("Street.java: public Street(Node A, Node B) mit gleicher Strasse aufgerufen!!");
		}
		this.A=A;
		this.B=B;
		
		// GUI
		length = A.distanceTo(B);
		
		this.speedLimit=speedLimit;
				
		lanesAB = new ArrayList<Lane>();
		for (int x=0;x< numLanesAB;x++) {
			Lane l = new Lane(this, true);
			lanesAB.add(l);
			add(l);
		}		
		
		lanesBA = new ArrayList<Lane>();
		for (int x=0;x< numLanesBA;x++) {
			Lane l = new Lane(this, false);
			lanesBA.add(l);
			add(l);
		}
		
		weight=length/speedLimit;
		
		A.addStreet(this);
		B.addStreet(this);
	}
	
	public Node getOtherEnd(Node start) {
		if (A == start) return B;
		if (B == start) return A;
		return null;
	}
	
	public synchronized boolean addCar(Car c, Node towards) {
		//System.out.println("Auto "+c+" fährt Richtung "+towards);
		Lane l = getEmptyLane(c,towards);
		if (l != null) {
			l.addCar(c);
			if (A.getSimCity().isThreadedSimulation()) notify();
			return true;
		}
		return false;
	}
	
	public synchronized boolean mayAddCar(Car c, Node towards) {
		/*
		 * Abfrage für Thread-Benutzung.
		 * Thread stoppen, falls man nicht in
		 */
		if (A.getSimCity().isThreadedSimulation()) {
			boolean res = false;
			do {
				res = getEmptyLane(c,towards) != null;
				if (!res) { 
					try {
						wait();
					} catch (InterruptedException e) {
						
					}
				}
			} while(!res);
			return res;
		/*
		 * Standardimplementierung mit Zeitschritt-Schleifen
		 */
		} else {
			return getEmptyLane(c,towards) != null;
		}
	}
	
	private Lane getEmptyLane(Car c, Node towards) {
		//ge�ndert , da Autos immer die Lane m�glichst weit rechts nutzen 
		//sollen und nicht die erste freie der Liste...
		if(towards==B){
			for (Lane l : lanesAB) {
				if (l.mayAddCar(c)) return l;
			}
		}else if(towards==A){
			for (int i=lanesBA.size()-1; i>=0; i--){
				if (lanesBA.get(i).mayAddCar(c)) return lanesBA.get(i);
			}
		}		
		return null;
	}
	
	public Node getCrossingA(){
		return A;
	}
	public Node getCrossingB(){
		return B;
	}
	public double getRealLength(){
		return length;
	}
	
	private int numCars() {
		int sum = 0;
		for (Lane l:lanesAB) sum += l.numCars();
		for (Lane l:lanesBA) sum += l.numCars();
		return sum;
	}
	
	/**
	 * @return Breite der Strasse in Metern
	 */
	public double getRealWidth() {
		return Conf.laneWidth * (lanesAB.size() + lanesBA.size());
	}
	
	/**
	 * Methode zur Simulation eines Zeitschritts auf der Strasse.
	 * Benutzung sowohl bei Simulation mit und ohne Thread.
	 */
	public void timeTick() {
		for (Lane l : lanesAB) {
			l.timeTick();
		}
		for (Lane l : lanesBA) {
			l.timeTick();
		}
	}
	
	/**
	 * Einfach den Lanes die gleiche Zeichenfläche zusprechen
	 */
	public void doLayout() {
		//System.out.println("Street:doLayout()");
		int w = Conf.convertToPixel(Conf.laneWidth);
		int cnt = 0;
		for (Lane l:lanesAB) {
			l.setBounds(0, w*cnt++,Conf.convertToPixel(length), w);
		}
		for (Lane l:lanesBA) {
			l.setBounds(0, w*cnt++,Conf.convertToPixel(length), w);
		}
	}
	
	private int getEffectiveX() {
		return (int)getWidth()/2 - Conf.convertToPixel(getRealLength()/2);
	}
	private int getEffectiveY() {
		return (int)getHeight()/2 - Conf.convertToPixel(getRealWidth()/2);
	}
	
	public double getLaneWidth(){
		return Conf.laneWidth;
	}
	
	public int getSpeedLimit(){
		return speedLimit != Integer.MAX_VALUE ? speedLimit:A.getSimCity().getCity().getSpeedLimit();
	}
	
	public double getWeight(){
		return weight;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform aft = AffineTransform.getRotateInstance(A.getX()+A.getWidth()/2 - B.getX()-B.getWidth()/2,A.getY()+A.getHeight()/2-B.getY()-B.getHeight()/2,getWidth()/2,getHeight()/2);
		g2d.setTransform(aft);
		
		/**
		 * Die Ecke links oben der später zu zeichnenden Strasse berechnen, gesehen vom Zentrum aus
		 */
		g2d.translate(getEffectiveX(),getEffectiveY());
		
		// Eigentliches Zeichnen
		g2d.setColor(Color.GRAY);
		g2d.fillRect(0, 0, Conf.convertToPixel(getRealLength()), Conf.convertToPixel(getRealWidth()));
		
		// Lanes zeichnen
		super.paint(g2d);
		
		g2d.setColor(Color.BLACK);
		g2d.drawString("#C:"+numCars(), getWidth()/2, 0);
		//g2d.setColor(Color.DARK_GRAY);
		//g2d.drawRect(0,0, Constants.convertToPixel(getRealLength()), Constants.convertToPixel(getRealWidth()));
	}
	
}
