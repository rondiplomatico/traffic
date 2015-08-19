package conf;

import java.awt.Color;

/**
 * Beschreibt das Fahrverhalten eines Autos/Fahrers
 * 
 * @param getWantedSpeed() 		[km/h] 	Standard - Wunschgeschwindigkeit
 * @param getSafetyDistance() 	[m] 	Standard-Sicherheitsabstand
 * @param getRelaxation()		[Sek] 	Standard - Relaxionszeit
 * @param getBreakIntensity() 	[] 		Wie schnell wird beim auffahren gebremst?
 * @param getAccRate()			[m/s²] 	Typische Beschleunigung
 * @param getColor()			Color	Zur Verwendung in CarColoring
 */
public interface IDriverBehaviour {

	public abstract double getWantedSpeed();

	public abstract double getSafetyDistance();

	public abstract double getRelaxation();

	public abstract double getBreakIntensity();

	public abstract double getAccRate();
	
	public abstract Color getColor();

}