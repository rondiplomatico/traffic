/**
 * 
 */
package conf;

/**
 * @author CreaByte
 *
 */
public enum CarColoring {
	
	// Zufällig
	Fancy,
	// Nach Algorithmus
	Algorithm,
	// Bildchen
	Image,
	// Nach Zielstation
	Target,
	// Nach Fahrerverhalten
	Behaviour;
	
	public static CarColoring getDefault() {
		return Algorithm;
	}

}
