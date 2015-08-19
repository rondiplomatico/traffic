/**
 * 
 */
package classes;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author Bloodyhand
 *
 */
public class Roundabout extends Node {
	private static final long serialVersionUID = 0;
	
	public Roundabout(String name, double xloc, double yloc) {
		super(name,xloc,yloc);
	}
	
	public void paint(Graphics g) {
		// Erst alle Kinder malen
		//super.paint(g);
		// Dann sich selber (drüber)
		g.setColor(Color.GRAY);
		//g.fillRect(0, 0, getWidth(), getHeight());
		g.fillOval(0, 0, getWidth()-1, getHeight()-1);
		g.setColor(Color.darkGray);
		g.fillOval(getWidth()/4, getWidth()/4, (getWidth()-1)/2, (getHeight()-1)/2);
		g.setColor(Color.WHITE);
		g.drawOval(0, 0, getWidth()-1, getHeight()-1);
		//g.setColor(Color.BLACK);
		//g.drawString((name.length() > 5 ? name.substring(0, 5):name), getWidth()/2, getHeight()/2);
		
		//System.out.println("Paint: "+name +" - " + this.getBounds()+", "+getBounds().getCenterX());
	}
}
