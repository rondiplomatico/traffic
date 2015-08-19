/**
 * 
 */
package classes;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.util.ArrayList;

import conf.Conf;



/**
 * @author Bloodyhand
 *
 */
public class Lane extends Container {

	private static final long serialVersionUID = 0;
	
	private ArrayList<Car> cars = new ArrayList<Car>();
	private Car lastadded;
	private Street street;
	
	private boolean inverted;
	
	/**
	 * Der Winkel wird übergeben, da die erstellende Strasse weiß
	 * in welcher Richtung die Lane läuft. Da bisher
	 * keine weiteren eigenschaften benötigt werden, ist es nicht notwendig
	 * die Start- und Zielnodes A und B zu übergeben.
	 * @param s
	 * @param angle
	 */
	public Lane(Street s, boolean inverted) {
		street = s;
		this.inverted = inverted;
	}
	
	public void addCar(Car c) {
		// GUI
		this.add(c);
		c.createImage();
		
		cars.add(c);
		lastadded = c;
		c.setLane(this);
	}
	
	public Street getStreet() {
		return street;
	}
	
	public void removeCar(Car c) {
		cars.remove(c);
		this.remove(c);
	}
	
	/**
	 * Prüft ob eine Lane voll ist.
	 * Dabei gilt eine Strasse als voll, falls die Summe aller
	 * Längen plus jeweils der minimale Autoabstand zusammen
	 * mit der Länge des Autos die Strassenlänge übertreffen.
	 * @param c zu prüfendes Auto
	 * @return
	 */
	public boolean mayAddCar(Car c) {
		if(!cars.isEmpty()){
			return lastadded.getPos() > Conf.minCarDistance;
		}
		return true;
		
		//double sum = 0;
		//for (Car car:cars) {
		//	sum += car.getLength()+Constants.minCarDistance;
		//}
		//return getStreet().getRealLength()-(sum + c.getLength() + Constants.minCarDistance) <= 0;
	}
	
	/**
	 * Returns the preceding Car, null if the Car is the first one
	 * @param c
	 * @return
	 */
	public Car getPrecessor(Car c) {
		int idx = cars.indexOf(c);
		if (idx > 0) return cars.get(idx-1);
		return null;
	}
	
	public void timeTick() {
		for (Car c:new ArrayList<Car>(cars)) {
			c.move();
		}
		repaint();
	}
	
	public int numCars() {
		return cars.size();
	}

	@Override
	public void paint(Graphics g) {
		//System.out.println("Lane: paint()");
		/*if (inverted) {
			Graphics2D g2d = (Graphics2D)g;
			//street.getCrossingA().getX() - street.getCrossingB().getX(), street.getCrossingA().getY() - street.getCrossingB().getY()
			AffineTransform aft = AffineTransform.getRotateInstance(Math.PI,getWidth()/2,getHeight()/2);
			g2d.setTransform(aft);
			g.setColor(g.getColor().darker());
		}*/
		g.setColor(Color.WHITE);
		g.drawLine(0, Conf.convertToPixel(street.getLaneWidth()), Conf.convertToPixel(street.getRealLength()), Conf.convertToPixel(street.getLaneWidth()));
		g.drawLine(0, 0, Conf.convertToPixel(street.getRealLength()), 0);
		
		//g.setColor(Color.GRAY);
		//g.fillRect(0, 0, Constants.convertToPixel(street.getRealLength()), Constants.convertToPixel(street.getLaneWidth()-.2));
		
		// Autos zeichnen (cars-liste kopieren um concurrent-mod-exception zu vermeiden)
		for (Car c:cars.toArray(new Car[0])) {
			int pos = inverted ? Conf.convertToPixel(street.getRealLength()-c.getPos()):Conf.convertToPixel(c.getPos());
			//g.setColor(Color.RED); //+Constants.convertToPixel(c.getLength())
			//g.fillRect(pos, (getHeight()-c.getHeight())/2, c.getWidth(), c.getHeight());
			//g.setColor(Color.BLACK);
			//g.drawRect(pos, (getHeight()-c.getHeight())/2, c.getWidth(), c.getHeight());
			g.drawImage(c.getImage(), pos, (getHeight()-c.getHeight())/2, this);
		}
	}
}
