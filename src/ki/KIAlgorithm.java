package ki;

import java.awt.Color;

import conf.Conf;

public enum KIAlgorithm {

	Random(Color.RED),
	RandomNoUTurn(Color.BLUE),
	Test(Color.YELLOW),
	Default(Color.BLACK);
	
	private Color c;
	
	KIAlgorithm(Color c) {
		this.c = c;
	}
	
	public PathAlgorithm createAlgorithm() {
		switch (this) {
			case Random: return new Random();
			case RandomNoUTurn: return new RandomNoUTurn();
			case Test: return new ShortestWay();
			case Default: return Random.createAlgorithm();
			default: return Default.createAlgorithm();
		}
	}
	
	public static KIAlgorithm getRandom() {
		int len = values().length;
		return values()[Conf.random(len-1)];
	}
	
	public Color getColor() {
		return c;
	}
}
