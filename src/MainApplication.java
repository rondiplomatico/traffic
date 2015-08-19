import cities.Big;
import cities.City;
import cities.Minden;
import cities.Small;
import conf.CarColoring;

/**
 * 
 */

/**
 * @author CreaByte
 *
 */
public class MainApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		City city = new Big();
//		City city = new Minden();
		City city = new Small();
		
		city.simulate(false, CarColoring.Behaviour);
	}

}
