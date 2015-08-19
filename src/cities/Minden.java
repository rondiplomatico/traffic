/**
 * 
 */
package cities;

import classes.Roundabout;
import classes.SimCity;
import classes.Station;
import classes.Street;

/**
 * @author CreaByte
 *
 */
public class Minden extends City {

	@Override
	public double getCharDistance() {
		return 500;
	}

	@Override
	public double getCharSpeed() {
		return 50;
	}

	@Override
	public int getSpeedLimit() {
		return 50;
	}

	protected void build(SimCity city) {

		Station s1 = new Station(city, 1,"",1350, 300);
		Station s2 = new Station(city, 1,"",1800, 300);
		Station s3 = new Station(city, .1,"",3000, 300);
		Station s4 = new Station(city, 1,"",4000, 300);
		Station s5 = new Station(city, .1,"",4500, 800);		
		Station s6 = new Station(city, 1,"",4500, 1200);		
		Station s7 = new Station(city, .1,"",3300, 3900);
		Station s8 = new Station(city, 1,"",2400, 4200);
		Station s9 = new Station(city, .1,"",2100, 4000);
		Station s10 = new Station(city, 1,"",200, 3200);
		Station s11 = new Station(city, 1,"",200, 2800);
		Station s12 = new Station(city, .1,"",400, 2100);
		Station s13 = new Station(city, .1,"",400, 1800);		
		Station s14 = new Station(city, .1,"",400, 1200);
		Station s15 = new Station(city, .01,"",2500, 1800);
		Station s16 = new Station(city, .01,"",2400, 1500);
		Station s17 = new Station(city, .01,"",3600, 1500);
		Station s18 = new Station(city, .01,"",3000, 1800);
		Station s19 = new Station(city, .01,"",3000, 2400);
		Station s20 = new Station(city, .01,"",2500, 3000);		
		Station s21 = new Station(city, .01,"",2600, 3650);

		
		Roundabout n1 = new Roundabout("",1400, 900);
		Roundabout n2 = new Roundabout("",1800, 800);
		Roundabout n3 = new Roundabout("",3100, 700);
		Roundabout n4 = new Roundabout("",3900, 600);
		Roundabout n5 = new Roundabout("",4000, 900);
		Roundabout n6 = new Roundabout("",4100, 1200);
		Roundabout n7 = new Roundabout("",3900, 1500);
		Roundabout n8 = new Roundabout("",3800, 1700);
		Roundabout n9 = new Roundabout("",4100, 1800);
		Roundabout n10 = new Roundabout("",3600, 2070);
		Roundabout n11 = new Roundabout("",3300, 2600);
		Roundabout n12 = new Roundabout("",3400, 2400);
		Roundabout n13 = new Roundabout("",3000, 3205);
		Roundabout n14 = new Roundabout("",2700, 3800);
		Roundabout n15 = new Roundabout("",2300, 3800);
		Roundabout n16 = new Roundabout("",500, 3800);
		Roundabout n17 = new Roundabout("",550, 3600);
		Roundabout n18 = new Roundabout("",600, 3500);
		Roundabout n19 = new Roundabout("",650, 3300);
		Roundabout n20 = new Roundabout("",700, 3100);
		Roundabout n21 = new Roundabout("",750, 2900);
		Roundabout n22 = new Roundabout("",800, 2700);
		Roundabout n23 = new Roundabout("",850, 2500);
		Roundabout n24 = new Roundabout("",900, 2300);
		Roundabout n25 = new Roundabout("",950, 2000);
		Roundabout n26 = new Roundabout("",1000, 1800);
		Roundabout n27 = new Roundabout("",1005, 1500);
		Roundabout n28 = new Roundabout("",2100, 2100);
		Roundabout n29 = new Roundabout("",1900, 1300);
		Roundabout n30 = new Roundabout("",2600, 1200);
		Roundabout n31 = new Roundabout("",2650, 1500);
		Roundabout n32 = new Roundabout("",2700, 1700);
		Roundabout n33 = new Roundabout("",3100, 1600);
		Roundabout n34 = new Roundabout("",3000, 1400);
		Roundabout n35 = new Roundabout("",3200, 1305);
		Roundabout n36 = new Roundabout("",3300, 1600);
		Roundabout n37 = new Roundabout("",3100, 1100);
		Roundabout n38 = new Roundabout("",3650, 1000);
		Roundabout n39 = new Roundabout("",3700, 1200);
		Roundabout n40 = new Roundabout("",3900, 1100);
		Roundabout n41 = new Roundabout("",3800, 950);
		Roundabout n42 = new Roundabout("",3100, 2105);
		Roundabout n43 = new Roundabout("",3100, 2250);
		Roundabout n45 = new Roundabout("",2800, 3200);
		Roundabout n46 = new Roundabout("",2600, 3100);
		Roundabout n47 = new Roundabout("",2650, 3050);
		Roundabout n48 = new Roundabout("",2700, 3000);
		Roundabout n49 = new Roundabout("",2900, 3050);
		Roundabout n50 = new Roundabout("",2750, 3400);
		Roundabout n51 = new Roundabout("",2700, 3500);
		Roundabout n52 = new Roundabout("",2550, 3600);
		Roundabout n53 = new Roundabout("",2250, 3500);
		Roundabout n81 = new Roundabout("",1800, 900);
		
		new Street(s1,n1,2,2);
		new Street(n3,n37);
		new Street(n1,n2,2,2);
		new Street(s2,n2,2,2);
		new Street(n2,n3,2,2);
		new Street(s3,n3);
		new Street(n3,n4,2,2);
		new Street(s4,n4,2,2);
		new Street(s5,n4);
		new Street(n4,n5,2,2);
		new Street(n5,n6,2,2);
		new Street(s6,n6,2,2);
		new Street(n6,n7,2,2);
		new Street(n7,n8,2,2);
		new Street(n8,n9);
		new Street(n8,n12,2,2);
		new Street(n9,n10);
		new Street(n10,n11);
		new Street(n11,n12,2,2);		
		new Street(n11,n13,2,2);
		new Street(n13,n14,2,2);
		new Street(s7,n14);
		new Street(s8,n14,2,2);
		new Street(n14,n15,2,2);
		new Street(s9,n15);
		new Street(n15,n16,2,2);
		new Street(n16,n17,2,2);
		new Street(s10,n17,2,2);
		new Street(n17,n18,2,2);
		new Street(n18,n19,2,2);
		new Street(s11,n19,2,2);
		new Street(n19,n20,2,2);
		new Street(n20,n21,2,2);
		new Street(n21,n22,2,2);
		new Street(n22,n23,2,2);
		new Street(s12,n23);
		new Street(n23,n24,2,2);
		new Street(s13,n24);
		new Street(n24,n25,2,2);
		new Street(n25,n26,2,2);
		new Street(n26,n27,2,2);
		new Street(s14,n27,2,2);
		new Street(s15,n28);
		new Street(n28,n29);
		new Street(n29,n81);
		new Street(n29,n30);
		new Street(n30,n31);
		new Street(s16,n31);
		new Street(n31,n32);
		new Street(n32,n33);
		new Street(n33,n34);
		new Street(n34,n35);
		new Street(n35,n36);
		new Street(s17,n36);
		new Street(n35,n37);
		new Street(n30,n37);
		new Street(n37,n38);
		new Street(n38,n39);
		new Street(n39,n40);
		new Street(n40,n41);
		new Street(n38,n41);
		new Street(n41,n5);
		new Street(n7,n42);
		new Street(s18,n42);
		new Street(n12,n43);
		new Street(s19,n43);
		new Street(n45,n13);
		new Street(n45,n46);
		new Street(n45,n49);
		new Street(n46,n47);
		new Street(n47,n48);
		new Street(n48,n49);
		new Street(s20,n47);
		new Street(n45,n50);
		new Street(n50,n51);
		new Street(n50,n53);
		new Street(n51,n52);
		new Street(s21,n52);
		new Street(n52,n15);
		
		city.addNode(s1);
		city.addNode(s2);
		city.addNode(s3);
		city.addNode(s4);
		city.addNode(s5);
		city.addNode(s6);
		city.addNode(s7);
		city.addNode(s8);
		city.addNode(s9);
		city.addNode(s10);
		city.addNode(s11);
		city.addNode(s12);
		city.addNode(s13);
		city.addNode(s14);
		city.addNode(s15);
		city.addNode(s16);
		city.addNode(s17);
		city.addNode(s18);
		city.addNode(s19);
		city.addNode(s20);
		city.addNode(s21);	
		city.addNode(n1);
		city.addNode(n2);
		city.addNode(n3);
		city.addNode(n4);
		city.addNode(n5);
		city.addNode(n6);
		city.addNode(n7);
		city.addNode(n8);
		city.addNode(n9);
		city.addNode(n10);
		city.addNode(n11);
		city.addNode(n12);
		city.addNode(n13);
		city.addNode(n14);
		city.addNode(n15);
		city.addNode(n16);
		city.addNode(n17);
		city.addNode(n18);
		city.addNode(n19);
		city.addNode(n20);
		city.addNode(n21);
		city.addNode(n22);
		city.addNode(n23);
		city.addNode(n24);
		city.addNode(n25);
		city.addNode(n26);
		city.addNode(n27);
		city.addNode(n28);
		city.addNode(n29);
		city.addNode(n30);
		city.addNode(n31);
		city.addNode(n32);
		city.addNode(n33);
		city.addNode(n34);
		city.addNode(n35);
		city.addNode(n36);
		city.addNode(n37);
		city.addNode(n38);
		city.addNode(n39);
		city.addNode(n40);
		city.addNode(n41);
		city.addNode(n42);
		city.addNode(n43);
		city.addNode(n45);
		city.addNode(n46);
		city.addNode(n47);
		city.addNode(n48);
		city.addNode(n49);
		city.addNode(n50);
		city.addNode(n51);
		city.addNode(n52);
		city.addNode(n53);
		city.addNode(n81);
	}

}
