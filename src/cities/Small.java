/**
 * 
 */
package cities;

import java.awt.Color;

import classes.LightCrossing;
import classes.Roundabout;
import classes.SimCity;
import classes.Station;
import classes.Street;

/**
 * @author CreaByte
 *
 */
public class Small extends City {

	@Override
	public int getSpeedLimit() {
		return 50;
	}

	@Override
	public double getCharDistance() {
		return 1;
	}

	@Override
	public double getCharSpeed() {
		return getSpeedLimit();
	}

	/**
	 * @see cities.City#build(classes.SimCity)
	 */
	@Override
	protected void build(SimCity sim) {
		Station s1 = new Station(sim, 1, "1st", 30, 40, Color.GREEN);
		Station s2 = new Station(sim, .7, "2nd", 80, 20, Color.GREEN.darker());
		Station s3 = new Station(sim, .4, "side1", 16, 90, Color.GREEN.darker().darker());
		Station s4 = new Station(sim, .3, "side2", 22, 156, Color.GREEN.darker().darker());
		Station s5 = new Station(sim, 0, "sink1", 184, 10, Color.PINK);
		Station s6 = new Station(sim, 0, "sink2", 210, 22, Color.RED);		
		Station s7 = new Station(sim, 0, "sink3", 230, 50, Color.CYAN);
		Station s8 = new Station(sim, 0, "sink3", 240, 126, Color.ORANGE);
		Station s9 = new Station(sim, 0, "sink4", 106, 200, Color.MAGENTA);
		
		LightCrossing b = new LightCrossing(50, 64, 0, 2000);
		Roundabout c = new Roundabout("C",42, 120);
		Roundabout d = new Roundabout("D",100, 100);
		Roundabout e = new Roundabout("E",112, 164);
		Roundabout f = new Roundabout("F",200, 136);
		Roundabout g = new Roundabout("G",180, 100);
		Roundabout h = new Roundabout("H",196, 50);
		
		new Street(s1,b,2,1);
		new Street(s2,b,2,1);
		new Street(b,c);
		new Street(s3,c);
		new Street(s4,c);
		new Street(b,d,2,1);
		new Street(c,d,2,1);
		new Street(d,g,4,2);
		new Street(c,e,2,1);
		new Street(s9,e);
		new Street(g,h,3,2);
		new Street(g,f,1,2);
		new Street(e,f,2,1);
		new Street(f,s8);
		new Street(h,s5);
		new Street(h,s6);
		new Street(h,s7);
		
		sim.addNode(s1);
		sim.addNode(s2);
		sim.addNode(s3);
		sim.addNode(s4);
		sim.addNode(s5);
		sim.addNode(s6);
		sim.addNode(s7);
		sim.addNode(s8);
		sim.addNode(s9);
		sim.addNode(b);
		sim.addNode(c);
		sim.addNode(d);
		sim.addNode(e);
		sim.addNode(f);
		sim.addNode(g);
		sim.addNode(h);

	}

}