/**
 * 
 */
package cities;

import classes.SimCity;
import conf.CarColoring;

/**
 * @author CreaByte
 *
 */
public abstract class City {
	
	public final void simulate(){
		simulate(false, CarColoring.getDefault());
	}
	
	public final void simulate(boolean withThreads, CarColoring coloring){
		SimCity simcity = new SimCity(this, withThreads, coloring);
		
		build(simcity);
		
		simcity.init();
		simcity.startSimCity();
	}
	
	/**
	 * Baut die Stadt mit ihren Strassen & Nodes
	 * @param city
	 */
	protected abstract void build(SimCity city);
	
	/**
	 * Gibt eine typische Länge für die Stadt aus
	 * @return [m] typische Längeneiheit
	 */
	public abstract double getCharDistance();
	
	/**
	 * Gibt eine typische Geschwindigkeit für die Stadt zurück 
	 * @return [km/h] typische Geschwindigkeit
	 */
	public abstract double getCharSpeed();
	
	/**
	 * Standard - Geschwindigkeitsbegrenzung für die Stadt
	 * @return
	 */
	public abstract int getSpeedLimit();
	
	/**
	 * Typische Zeiteinheit - automatisch berechnet
	 * @return
	 */
	public final double getCharTime() {
		return getCharDistance()/getCharSpeed();
	}

}
