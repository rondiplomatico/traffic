/**
 * 
 */
package ki;

import java.awt.Color;

import conf.Conf;

import classes.Node;
import classes.Street;

/**
 * @author Bloodyhand
 *
 */
public class RandomNoUTurn extends Random {

	private Street currentStreet = null;
	private int maxTries = 1000;

	/**
	 * Gibt eine nächste Strasse aus
	 */
	@Override
	public Street getDirection(Node from) {
		if (nextNode() == targetNode) return null;
		Street next; int _try = 0;
		do {			
			nextstreetidx = randomStreetIdx(nextNode());
			next = super.getDirection(from);
			_try++;
		} while (_try < maxTries && next == currentStreet);
		currentStreet = next;
		return next; 
	}
	
	@Override
	public Color getColor() {
		return Color.BLUE;
	}

}
