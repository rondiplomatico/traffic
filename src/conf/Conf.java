package conf;

/**
 * Enthält die Konfigurationseinstellungen
 * @author CreaByte
 *
 */
public final class Conf {
		
		/**
		 * Frames pro Sekunde
		 */
		public static final int fps = 22;
		/**
		 * Standard-Zeitschritt für die Simulation
		 */
		public static final long defaultTimeStep = 1000/fps; // 22fps
	
	    /**
	     * Effektive Umrechnung Längeneinheiten in Pixel
	     * LINKS OBEN ist der Nullpunkt "der Welt"
	     */ 
		public static double pixelPerMeter=4;
		public static final double MAX_ZOOM=10,MIN_ZOOM=0.01;
		
		public static int convertToPixel(double meters) {
			return (int)(meters * pixelPerMeter);
		}
		
		/**
		 * Typische Größen
		 */
	    public static final double carWidth = 1.5; // Meter
	    public static final double carLength = 3; // Meter
	    public static final double laneWidth = 3; // Meter
	    
	    /**
	     * Typische Größen für die Autobewegung
	     */
	    public static final double L = 10; // [m] Typische Längeneinheit
	    public static final double V = 10; // [km/h] Typische Geschwindigkeit
	    public static final double T = L/V; // [h] Typische Zeiteinheit
	    
	    public static final double d = 1; // [m] Standard-Sicherheitsabstand
	    public static final double c = 1; // [] Wie schnell wird beim auffahren gebremst?
	    public static final double v0 = 10; // [km/h] Standard - Wunschgeschwindigkeit
	    public static final double tau = 1; // [Sek] Standard - Relaxionszeit in (wie schnell p  
	    public static final double a = 3.85; // [m/s²] Typische Längeneinheit in Metern
	    
	    public static final double minCarDistance = 2; // Meter
		public static final double eyeSight = 10; // Meter
	    
	    
	    /**
	     * Grenze der Stationen zum Rand des Stadtgebiets in Meter
	     */
	    public static final double border = 4; // Meter
		
		public static int random(int max) {
			return (int)Math.round(Math.random() * max);
		}
}
