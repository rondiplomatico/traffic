/**
 * 
 */
package conf;

import java.awt.Color;

/**
 * @author CreaByte
 *
 */
public class CustomDriverBehaviour implements IDriverBehaviour {

	private double v0,tau,c,a,d;
	private Color color; 
	
	/**
	 * Legt das Fahrverhalten des Fahrers fest.
	 * 
	 * @param wanted_speed 		[km/h] 	Standard - Wunschgeschwindigkeit
	 * @param safety_distance 	[m] 	Standard-Sicherheitsabstand
	 * @param relaxation_time	[Sek] 	Standard - Relaxionszeit
	 * @param break_intensity 	[] 		Wie schnell wird beim auffahren gebremst?
	 * @param acceleration_rate	[m/s²] 	Typische Beschleunigung
	 */
	public CustomDriverBehaviour(double wanted_speed, double safety_distance, double relaxation_time, double break_intensity, double acceleration_rate, Color color) {
		v0 = wanted_speed;
		tau = relaxation_time;
		c = break_intensity;
		a = acceleration_rate;
		d = safety_distance;
		this.color = color;
	}
	
	/**
	 * @see conf.IDriverBehaviour#getAccRate()
	 */
	@Override
	public double getAccRate() {
		return a;
	}

	/**
	 * @see conf.IDriverBehaviour#getBreakIntensity()
	 */
	@Override
	public double getBreakIntensity() {
		return c;
	}

	/**
	 * @see conf.IDriverBehaviour#getRelaxation()
	 */
	@Override
	public double getRelaxation() {
		return tau;
	}

	/**
	 * @see conf.IDriverBehaviour#getSafetyDistance()
	 */
	@Override
	public double getSafetyDistance() {
		return d;
	}

	/**
	 * @see conf.IDriverBehaviour#getWantedSpeed()
	 */
	@Override
	public double getWantedSpeed() {
		return v0;
	}
	
	/**
	 * @see conf.IDriverBehaviour#getColor()
	 */
	public Color getColor() {
		return color;
	}

}
