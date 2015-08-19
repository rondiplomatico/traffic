/**
 * 
 */
package ki;

import java.awt.Color;

import classes.Node;
import classes.Street;

/**
 * @author Bloodyhand
 *
 */
public abstract class PathAlgorithm {
	
	protected Node currentNode,targetNode;
		
	/**
	 * Gibt die nächste Strasse des berechneten Weges gesehen
	 * von der nextNode() aus an.
	 * 
	 * WICHTIG:
	 * Ist die Node bereits das aktuelle Ziel, soll null ausgegeben werden.
	 * @param from TODO
	 * @param c Kreuzung ab der gefahren werden soll
	 * 
	 * @return Von Kreuzung abgehende Strasse
	 */
	public abstract Street getDirection(Node from);
	
	/**
	 * Gibt mir die nächste Kreuzung des berechneten Weges
	 * aus.
	 * 
	 * !!!!!!
	 * Gibt null zurück, falls die aktuelle Node schon die Zielnode ist.
	 * !!!!!!
	 * 
	 * @param n Aktuelle Node
	 * @return Node nächste Kreuzung
	 */
	public abstract Node nextNode();
	
	/**
	 * Farbe des Algorithmus, für Darstellungszwecke
	 * @return
	 */
	public abstract Color getColor();
	
	/**
	 * Berechnet einen Weg von start nach end durch die Stadt
	 * und setzt die aktuelle Node auf start.
	 * @param start
	 * @param end
	 */
	public void Calculate(Node start, Node end) {
		targetNode = end;
		currentNode = start;
	}
	
	/**
	 * Gehe eine Node weiter im berechneten Pfad
	 * @param newNode
	 */
	public abstract void advance();	
}
