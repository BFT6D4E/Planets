import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;


public class Main {
	
	public static void main(String[] args) throws Exception{
		//create and open file
		File f = new File("resources/generatedGraph.json");
		FileReader fr = new FileReader(f);
		JsonReader jr = Json.createReader(fr);
		
		//get the object
		JsonObject jo = jr.readObject();
		System.out.println("Size of Json-Object: " + jo.size());
		
		//get the values
		JsonArray nodesJson = jo.getJsonArray("nodes");
		JsonArray edgesJson = jo.getJsonArray("edges");
	
		//close files, they are not needed furthermore
		jr.close();
		fr.close();
		
		//get sizes of JSON arrays
		int numberOfNodes = nodesJson.size();
		int numberOfEdges = edgesJson.size();
		System.out.println("Number of nodes: " + numberOfNodes);
		System.out.println("Number of edges: " + numberOfEdges);
		
		
		Node earth = null, r7 = null; //start, success
		
		//create a array list of all the nodes
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int i = 0; i < numberOfNodes; i++) {
			nodes.add(new Node(nodesJson.getJsonObject(i).get("label").toString(), i));
			if(nodes.get(i).getName().contains("rde")) {
				earth = nodes.get(i);
			}else if(nodes.get(i).getName().contains("r7")) {
				r7 = nodes.get(i);
			}
		}
		
		
		
		//create a array list of all the edges
		ArrayList<Edge> edges = new ArrayList<Edge>();
		for(int i = 0; i < numberOfEdges; i++){
			int source = Integer.parseInt(edgesJson.getJsonObject(i).get("source").toString());
			int target = Integer.parseInt(edgesJson.getJsonObject(i).get("target").toString());
			double cost = Double.parseDouble(edgesJson.getJsonObject(i).get("cost").toString());
			
			if(nodes.get(source).getId() != source || nodes.get(target).getId() != target) {
				System.out.println("Error: not matching; " + nodes.get(source).getId() + source + nodes.get(target).getId() + target);
				throw new Exception();
			}
			//both directions
			Edge e1 = new Edge(nodes.get(source), nodes.get(target), cost);
			Edge e2 = new Edge(nodes.get(target), nodes.get(source), cost);
			edges.add(e1);
			edges.add(e2);
			//write edge to starting edges
			nodes.get(source).getStartingEdges().add(e1);
			nodes.get(target).getStartingEdges().add(e2);
			
		}
		//Debug information
		System.out.println("Size of AraryList nodes: " + nodes.size());
		System.out.println("Size of AraryList edges: " + edges.size());
		
		// how to get the values out of JSON arrays
		// System.out.println(nodesJson.getJsonObject(0).get(key).toString());
		
		
		//Searching the universe using Dijkstra
		System.out.println("Start searching...");
		dijkstra(earth, nodes, r7);
		System.out.println("Searching terminated.");
		
	}
	
	/**
	 * Dijkstra�s algorithm
	 * 
	 * @param start starting node
	 * @param nodes all nodes of the graph
	 * @param goal destination
	 * @throws Exception in case of fatal error
	 */
	private static void dijkstra(Node start, ArrayList<Node> nodes, Node goal) throws Exception {
		//using heap would be better (O(n)), but LinkedList is also fine enough(O(n*n)); n: numberOfNodes
		LinkedList<Node> q = dijkstraInit(start, nodes); //initial values: 0 for start; cost of edge for neighbors of start
														//Double.MAX_VALUE for all the others
		Node min = getMin(q); //minimum in q
		
		
		while(q.size() > 0) { //while unhandled nodes are available
			if(!q.remove(min)) //remove minimum out of q
				throw new Exception();
			for(Edge e : min.getStartingEdges()) {
				relax(e, q); //update cost of neighbor
			}
			
			if(min == goal) { //test for reaching goal
				System.out.println("Distance: " + min.getDistance()); //solution part 1
				String path = min.getName();
				while(min.getPre() != null) { //building path
					path = min.getPre().getName() + " " + path;
					min = min.getPre();
				}
				System.out.println("Path: " + path); //solution part 2
				return;
			}
			min = getMin(q); //next minimum
		}
		
	}
	
	/** updating distance for one node(destination of Edge e)
	 * 
	 * @param e edge, which should be investigated
	 * @param q nodes, which shortest path to source in unknown
	 */
	private static void relax(Edge e, LinkedList<Node> q) {
		for(Node n : q) {
			if(n.equals(q)) //shortest path to source already found
				return;
		}
		if(e.getEnd().getDistance() > e.getStart().getDistance() + e.getCost()) { // check if e brings an advantage
			e.getEnd().setDistance(e.getStart().getDistance() + e.getCost()); //update minimal cost from source to e`s destination
			e.getEnd().setPre(e.getStart()); //update previous node in path
		}
	}

	/** gets Node with minimal costs (distance)
	 * 
	 * @param q nodes, which shortest path to source in unknown
	 * @return node, which cost from source is minimal
	 */
	private static Node getMin(LinkedList<Node> q) {
		Node min = q.get(0); 
		for(Node n : q) {
			if(n.getDistance() < min.getDistance())
				min = n; //at this moment n minimal value
		}
		return min;
	}

	/** Initializes Dijkstras algorithm
	 * 
	 * @param start start node
	 * @param nodes all nodes of the graph
	 * @return nodes without start as LinkedList
	 */
	private static LinkedList<Node> dijkstraInit(Node start, ArrayList<Node> nodes){
		LinkedList<Node> q = new LinkedList<Node>();
		for(Node n : nodes) {
			n.setDistance(Double.MAX_VALUE); //infinite costs
			if(n.equals(start)) { //if start node
				start.setDistance(0); //distance start -> start is 0
				start.setPre(null); //no previous node!
			}
			else {
				q.add(n); //add to LinkedList
			}
		}
		
		
		for(Edge e : start.getStartingEdges()) {
			e.getEnd().setDistance(e.getCost()); //initial costs (cost of edge)
			e.getEnd().setPre(start); //start is previous node for all neighbors
		}
		return q;
	}
	
}




























