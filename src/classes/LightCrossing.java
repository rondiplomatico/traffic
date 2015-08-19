/**
 * 
 */
package classes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Bloodyhand
 *
 */
public class LightCrossing extends Node {
	private static final long serialVersionUID = 0;
	
	private boolean red = false ;
	private Timer t;
	
	public LightCrossing(double xloc, double yloc, int wait, int interval) {
		super(null, xloc, yloc);
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() { public void run(){ switchLight(); } }, wait, interval);
	}

	public void switchLight(){
		if(red){
			red=false;
		}else{
			red=true;
		}
	}
	@Override
	public boolean mayCross(Car c, Street from, Street to) {
		if(red&&((streets.indexOf(from)==0)||(streets.indexOf(from)==2))){
			return false;
		}else if(!red&&!((streets.indexOf(from)==0)||(streets.indexOf(from)==2))){
			return false;
		}
		boolean res = super.mayCross(c, from, to);
		// TODO: Akutelle Ampelphase einbauen: WICHTIG: VOR super.mayCross
		return res;
	}
	
	public void paint(Graphics g) {
		if(red){
			g.setColor(Color.RED);
		}else{
			g.setColor(Color.GREEN);
		}
		g.fillOval(0, 0, getWidth()-1, getHeight()-1);		
	}

}
