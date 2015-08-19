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
public class Big extends City {

	@Override
	public int getSpeedLimit() {
		return 50;
	}

	@Override
	public double getCharDistance() {
		return 100;
	}

	@Override
	public double getCharSpeed() {
		return 50;
	}

	/**
	 * @see cities.City#build(classes.SimCity)
	 */
	@Override
	protected void build(SimCity sim) {
		Station s1 = new Station(sim, 1, "Start", 10, 10, Color.GREEN);
		Station s2 = new Station(sim, 1, "Start", 3600, 2100, Color.BLUE);
		Station s3 = new Station(sim, .1, "Start", 2050, 800, Color.CYAN);
		Station s4 = new Station(sim, .1, "Start", 2850, 3500, Color.MAGENTA);
		Station s5 = new Station(sim, .1, "Start", 1850, 1100, Color.PINK);
		Station s6 = new Station(sim, .1, "Start", 1500, 1850, Color.YELLOW);		
		Station s7 = new Station(sim, .1, "Start", 2500, 1400, Color.ORANGE);
		
		LightCrossing a = new LightCrossing(600, 100,0,2000);
		LightCrossing b = new LightCrossing(1100, 100,2000,2000);
		Roundabout c = new Roundabout("C",2300, 150);
		Roundabout d = new Roundabout("D",2950, 50);
		Roundabout e = new Roundabout("E",3400, 200);
		LightCrossing f = new LightCrossing(600, 600,0,2000);
		LightCrossing g = new LightCrossing(1100, 600,2000,2000);
		Roundabout h = new Roundabout("H",2400, 600);
		Roundabout i = new Roundabout("I",2900, 650);
		Roundabout j = new Roundabout("J",2400, 1100);
		Roundabout k = new Roundabout("K",3100, 1100);
		Roundabout l = new Roundabout("L",2100, 1600);
		Roundabout m = new Roundabout("M",600, 2100);
		Roundabout n = new Roundabout("N",1200, 2100);
		Roundabout o = new Roundabout("O",1600, 2400);
		Roundabout p = new Roundabout("P",2400, 2300);
		Roundabout q = new Roundabout("Q",3000, 2000);
		
		new Street(s1,a,2,2);
		new Street(a,f,2,2);
		new Street(a,b,2,2);
		new Street(f,g,2,2);
		new Street(f,m,2,2);
		new Street(g,n,2,2);
		new Street(g,h,2,2);
		new Street(b,g,2,2);
		new Street(b,c,2,2);		

		new Street(c,d,2,2);
		new Street(d,e,2,2);
		new Street(h,s3);
		new Street(s4,i);
		new Street(s5,j);
		new Street(j,k,2,2);
		new Street(l,s7);
		new Street(m,n,2,2);
		new Street(n,o,2,2);
		new Street(o,p,2,2);
		new Street(p,q,2,2);
		new Street(q,s2,2,2);
		new Street(s6,o);
		new Street(c,h,2,2);
		new Street(h,j,2,2);
		new Street(j,l,2,2);
		new Street(l,p,2,2);
		new Street(d,s4);
		new Street(e,i,2,2);
		new Street(i,k,2,2);
		new Street(k,q,2,2);
		
		sim.addNode(s1);
		sim.addNode(s2);
		sim.addNode(s3);
		sim.addNode(s4);
		sim.addNode(s5);
		sim.addNode(s6);
		sim.addNode(s7);
		sim.addNode(a);
		sim.addNode(b);
		sim.addNode(c);
		sim.addNode(d);
		sim.addNode(e);
		sim.addNode(f);
		sim.addNode(g);
		sim.addNode(h);
		sim.addNode(i);
		sim.addNode(j);
		sim.addNode(k);
		sim.addNode(l);
		sim.addNode(m);
		sim.addNode(n);
		sim.addNode(o);
		sim.addNode(p);
		sim.addNode(q);

	}

}
