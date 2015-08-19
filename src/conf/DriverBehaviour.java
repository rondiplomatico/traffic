/**
 * 
 */
package conf;

import java.awt.Color;

import ki.KIAlgorithm;

/**
 * @author CreaByte
 * @see IDriverBehaviour
 */
public enum DriverBehaviour implements IDriverBehaviour {
	
    /*       v    d  t  c     a  color      */
	Default	(50,  3, 1, 1, 3.85, Color.RED),
	Med		(25,  6, 1, 2, 	  2, Color.BLUE),
	LKW		(10, 10, 3, 1,    1, Color.BLACK);
	
	private double v0,tau,c,a,d;
	private Color col;
	
	/**
	 * Legt das Fahrverhalten des Fahrers fest.
	 * 
	 * @param wanted_speed 		[km/h] 	Standard - Wunschgeschwindigkeit
	 * @param safety_distance 	[m] 	Standard-Sicherheitsabstand
	 * @param relaxation_time	[Sek] 	Standard - Relaxionszeit
	 * @param break_intensity 	[] 		Wie schnell wird beim auffahren gebremst?
	 * @param acceleration_rate	[m/s²] 	Typische Beschleunigung
	 */
	private DriverBehaviour(double wanted_speed, double safety_distance, double relaxation_time, double break_intensity, double acceleration_rate, Color color) {
		v0 = wanted_speed;
		tau = relaxation_time;
		c = break_intensity;
		a = acceleration_rate;
		d = safety_distance;
		col = color;
	}
	
	public static DriverBehaviour getRandom() {
		return values()[Conf.random(values().length-1)];
	}
	
	/**
	 * @see conf.IDriverBehaviour#getWantedSpeed()
	 */
	public double getWantedSpeed() {
		return v0;
	}
	
	/**
	 * @see conf.IDriverBehaviour#getSafetyDistance()
	 */
	public double getSafetyDistance() {
		return d;
	}
	
	/**
	 * @see conf.IDriverBehaviour#getRelaxation()
	 */
	public double getRelaxation() {
		return tau;
	}
	
	/**
	 * @see conf.IDriverBehaviour#getBreakIntensity()
	 */
	public double getBreakIntensity() {
		return c;
	}
	
	/**
	 * @see conf.IDriverBehaviour#getAccRate()
	 */
	public double getAccRate() {
		return a;
	}
	
	/**
	 * @see conf.IDriverBehaviour#getColor()
	 */
	public Color getColor() {
		return col;
	}
	
	
}
