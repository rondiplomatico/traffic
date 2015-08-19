/**
 * 
 */
package classes;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * @author Bloodyhand
 *
 */
public abstract class Node extends Container {

	private static int nodeCnt=0;
	private SimCity city;
	private double distanceToStart;
	
	protected ArrayList<Street> streets = new ArrayList<Street>();
	
	// Ort der Node
	protected double lx,ly,radius;
	
	protected String name;
	
	public Node(String name, double xloc, double yloc) {
		nodeCnt++;
		if (name == null) {
			this.name = this.getClass().getSimpleName()+nodeCnt;
		} else this.name = name;
		// Standarddurchmesser in Metern
		radius=4;
		lx = xloc;
		ly = yloc;
	}
	
	/**
	 * Realisiert den Übergang eines Autos von einer Strasse über eine
	 * Kreuzung in die nächste Strasse to.
	 * 
	 * @param car Kreuzendes Auto
	 * @param to Abgehende Zielstrasse
	 * @return true für erfolgreichen Wechsel, false bei fehlschlag.
	 */
	public boolean cross(Car car, Street to) /*throws IllegalTargetCrossingException*/ {
		car.getLane().removeCar(car);
		if (to.addCar(car, to.getOtherEnd(this))) {
			//System.out.println("Auto "+car+" durchfährt "+this);
			return true;
		}
		return false;
	}
	
	/**
	 * Prüft ob ein gegebenes Auto die Kreuzung bei gegebener
	 * Herkunftsrichtung & Zielrichtung überqueren kann.
	 * Dient dazu festzustellen ob die Zielstrasse voll ist und
	 * daher ein einfügen nicht möglich ist.
	 * 
	 * Diese Methode kann in abgeleiteten Kreuzungen angepasst werden
	 * @param c Aktuelles Auto
	 * @param from Ankunftsstrasse
	 * @param to Zielstrasse
	 * @return true falls das Auto passieren darf und Platz in der Zielstrasse ist
	 */
	public synchronized boolean mayCross(Car c, Street from, Street to) {
		return to.mayAddCar(c, to.getOtherEnd(this));
	}
	
	public void addStreet(Street s){
		streets.add(s);
		if (s.getRealWidth() > radius) {
			radius = s.getRealWidth();
			// Radius geändert -> neu layouten (evtl. ampellichter etc)
			this.invalidate();
		}
	}

	
	/**
	 * Gibt die Koordinaten des ZENTRUMS in X-Richtung
	 * in Metern an
	 * @return X-Zentrum in Meter
	 */
	public double getRealX() {
		return lx;
	}
	
	/**
	 * Gibt die Koordinaten des ZENTRUMS in Y-Richtung
	 * in Metern an
	 * @return Y-Zentrum in Meter
	 */
	public double getRealY() {
		return ly;
	}
	
	public ArrayList<Street> getStreets() {
		return streets;
	}
	
	/**
	 * Berechnet den Abstand zwischen der Node und einer anderen Node
	 * @param c Entfernte Node
	 * @return Abstand in Metern
	 */
	public double distanceTo(Node c){
		return Math.sqrt((Math.pow(lx-c.lx, 2)+Math.pow(ly-c.ly,2)));
	}
	
	/**
	 * Gibt den Winkel zwischen hier und der Node c zurück,
	 * argument zwischen 0 und Math.pi
	 * @param c
	 * @return
	 */
	public double angleTo(Node c) {
		return Math.acos((c.lx*lx + c.ly*ly) / (norm() * c.norm()));
	}
	
	/**
	 * Gibt die euklidische Norm der Node zurück
	 * @return
	 */
	public double norm() {
		return Math.sqrt(Math.pow(lx, 2) +  Math.pow(ly, 2));
	}
	
	/**
	 * Berechnet den Radius [Meter] der Node
	 * abhängig von der größten Anliegenden Strasse.
	 * @return
	 */
	private double getRealRadius() {
		return radius;
	}
	
	/**
	 * Aktuell: Jede Node wird durch einen Kreis
	 * dargestellt und hat daher gleiche
	 * Höhe wie Breite
	 * @return
	 */
	public double getRealWidth() {
		return getRealRadius();
	}
	
	/**
	 * Aktuell: Jede Node wird durch einen Kreis
	 * dargestellt und hat daher gleiche
	 * Höhe wie Breite
	 * @return
	 */
	public double getRealHeight() {
		return getRealRadius();
	}
	
	public String toString() {
		return name +" an "+lx+","+ly;
	}

	public SimCity getSimCity() {
		return city;
	}
	
	public void setSimCity(SimCity simCity) {
		city = simCity;
	}
	
	@Override
	public void doLayout() {
		// TODO: Hier evtl Ampellichter o.ä. arrangieren
	}
	public void setDistanceToStart(double distance){
		distanceToStart=distance;
	}
	public double getDistanceToStart(){
		return distanceToStart;
	}
	public ArrayList<Node> getNextNodes(){
		ArrayList<Node> NextNodes = new ArrayList<Node>();
		for(Street s:streets){
			NextNodes.add(s.getOtherEnd(this));
		}
		return NextNodes;
	}
	
	@Override
	public void paint(Graphics g) {
		// Erst alle Kinder malen
		//super.paint(g);
		// Dann sich selber (drüber)
		g.setColor(Color.GRAY);
		//g.fillRect(0, 0, getWidth(), getHeight());
		g.fillOval(0, 0, getWidth()-1, getHeight()-1);
		g.setColor(Color.darkGray);
		g.fillOval(getWidth()/4, getWidth()/4, (getWidth()-1)/2, (getHeight()-1)/2);
		g.setColor(Color.WHITE);
		g.drawOval(0, 0, getWidth()-1, getHeight()-1);
		//g.setColor(Color.BLACK);
		//g.drawString((name.length() > 5 ? name.substring(0, 5):name), getWidth()/2, getHeight()/2);
		
		//System.out.println("Paint: "+name +" - " + this.getBounds()+", "+getBounds().getCenterX());
	}
}
