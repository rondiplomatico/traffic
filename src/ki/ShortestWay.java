/**
 * 
 */
package ki;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import classes.Node;
import classes.Station;
import classes.Street;

public class ShortestWay extends PathAlgorithm {

	private Hashtable<Node, Double> openList;
	private Hashtable<Node, Double> tempList;
	private Hashtable<Node, Node> temp2List;
	private ArrayList<Node> closedList;	
	private ArrayList<Node> tempway;
	private ArrayList<Node> way;
	protected boolean adv;
	private double minWay;
	
	@Override
	public void Calculate(Node start, Node target) {
		super.Calculate(start, target);
		
		openList = new Hashtable<Node, Double>();
		tempList = new Hashtable<Node, Double>();
		temp2List = new Hashtable<Node, Node>();
		closedList = new ArrayList<Node>();
		tempway = new ArrayList<Node>();
		way = new ArrayList<Node>();
		
		minWay=999999;					


		openList.put(start, 0.0);
		tempList.put(start, 0.0);
		do
		{
			Node actualNode = removeMinNode();

			if (actualNode == target)
			{				
				double completeDistance = 0;
				tempway.add(actualNode);
				Node lastNode = temp2List.get(actualNode);
				do
				{
					lastNode = temp2List.get(actualNode);
					if (lastNode != null)
					{
						completeDistance += lastNode.distanceTo(actualNode);
						tempway.add(lastNode);
						actualNode = lastNode;
					}
				} while (lastNode != null);
			}
			
			expandNode(actualNode);			
			
			closedList.add(actualNode);
			
		} while (openList.size() > 0);		
		if(tempList.get(target)<minWay){
			minWay=tempList.get(target);
			way=tempway;
		}		
	}
	
	private Node removeMinNode()
	{
		double minValue = 99999;
		Node minNode = null;
		Enumeration<Node> nodes = openList.keys();
		while (nodes.hasMoreElements())
		{
			Node actualNode = (Node)nodes.nextElement();
			double actualValue = tempList.get(actualNode);
			if (actualValue < minValue)
			{
				minValue = actualValue;
				minNode = actualNode;
			}
		}
		openList.remove(minNode);
		return minNode;
	}
	
	private void expandNode(Node actualNode)
	{		
		ArrayList<Node> nextNodes = actualNode.getNextNodes();
		for (Node nextNode: nextNodes)
		{			
			if (closedList.indexOf(nextNode)!=-1){
				continue;
			}
			
			double tmp=0;
			for(Street s:actualNode.getStreets()){
				if (s.getOtherEnd(actualNode)==nextNode){
					tmp=s.getWeight();
				}
			}
			
			//double f = g(actualNode) + actualNode.distanceTo(nextNode)+h(nextNode);
			double f = g(actualNode) + tmp + h(nextNode);

			if (this.openList.get(nextNode) != null)
			{
				if(f>openList.get(nextNode)){
					continue;	
				}
			}
			
			if(!(nextNode instanceof Station)||(nextNode==targetNode)){
				openList.put(nextNode, f);
				tempList.put(nextNode, f);
				temp2List.put(nextNode, actualNode);								
			}else{
				continue;
			}
		}
	}
	
	private double g(Node actualNode)
	{
		int g = 0;
		Node lastNode = null;
		Node actualNode1 = actualNode;
		do
		{
			lastNode = temp2List.get(actualNode1);
			if (lastNode != null)
			{
				//g += lastNode.distanceTo(actualNode1);
				double tmp=0;
				for(Street s:lastNode.getStreets()){
					if (s.getOtherEnd(lastNode)==actualNode1){
						tmp=s.getWeight();
					}
				}
				g += tmp;
				actualNode1 = lastNode;
			}
		} while (lastNode != null);
		return g;
	}


	private double h(Node actualNode)
	{
		//TODO: Heuristics
		return 1;
	}
	
	@Override
	public Color getColor() {
		return Color.YELLOW;
	}
	
	@Override
	public Node nextNode(){
		return nextNode(currentNode);
	}
	
	private Node nextNode(Node n) {
		if (n != targetNode) {
			return way.get(way.indexOf(n)-1);
		}
		return null;
	}
	
	/**
	 * Beim Start muss die nächste Richtung zuerst die 
	 * direkt an die Station anknüpfende Strasse sein,
	 * danach die Strasse ab der nächsten Node
	 * (man fährt ja am anfang noch nicht auf einer strasse)
	 */
	@Override
	public Street getDirection(Node from){
		/*if (currentNode == startNode) {
			for (Street s:from.getStreets()) {
				if (s.getOtherEnd(currentNode) == nextNode()) 
					return s;
			}
		} else {
			for (Street s:nextNode().getStreets()) {
				if (s.getOtherEnd(nextNode()) == nextNode(nextNode()))
					return s;
			}
		}*/
		for (Street s:from.getStreets()) {
			if (s.getOtherEnd(from) == nextNode(from)) 
				return s;
		}
		return null;
	}
	
	@Override
	public void advance() {
		currentNode = nextNode();
	}
}
