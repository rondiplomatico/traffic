/**
 * 
 */
package ki;

import java.awt.Color;

import classes.Node;
import classes.Street;
import conf.Conf;

/**
 * @author Bloodyhand
 *
 */
public class Random extends PathAlgorithm {

	protected int nextstreetidx;
	private int curstreetidx;
	
	public Random() {
		// Eine strasse muss vorhanden sein!
		nextstreetidx = 0;
		curstreetidx = 0;
	}
	
	@Override
	public Node nextNode(){
		return currentNode.getStreets().get(curstreetidx).getOtherEnd(currentNode);
	}

	/* (non-Javadoc)
	 * @see interfaces.IPathAlgorithm#nextStreet()
	 */
	@Override
	public Street getDirection(Node from) {
		// Einhalten der Konvention, wäre aber bei aktueller Implementierung nicht notwendig.
		if (nextNode() == targetNode) return null;
		return nextNode().getStreets().get(nextstreetidx);
	}
	
	@Override
	public void advance() {
		currentNode = nextNode();
		curstreetidx = nextstreetidx;
		nextstreetidx = randomStreetIdx(nextNode());	
	}
	
	protected int randomStreetIdx(Node n) {
		return Conf.random(n.getStreets().size()-1);
	}
	
	@Override
	public void Calculate(Node start, Node end) {
		super.Calculate(start, end);
		curstreetidx = randomStreetIdx(start);
		nextstreetidx = randomStreetIdx(nextNode());
	}
	
	@Override
	public Color getColor() {
		return Color.RED;
	}
}
