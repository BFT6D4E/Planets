import java.util.ArrayList;

public class Node {
	private String name; //name of the node
	private int id; //id of the node
	
	private double distance; //distance from earth
	
	
	private Node pre; //previous node on path from earth
	
	private ArrayList<Edge> startingEdges = new ArrayList<Edge>(); //edges of the node
	
	/**
	 * Constructor 
	 * @param name name of the node
	 * @param id id of the node
	 * @throws Exception fatal error
	 */
	public Node(String name, int id) throws Exception {
		this.name = name;
		this.id = id;
		
		if(!name.contains("" + id) && !name.contains("Erde") && !name.contains("r7")) {
			throw new Exception();
		}
			
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Node getPre() {
		return pre;
	}

	public void setPre(Node pre) {
		this.pre = pre;
	}

	public ArrayList<Edge> getStartingEdges() {
		return startingEdges;
	}

	public void setStartingEdges(ArrayList<Edge> startingEdges) {
		this.startingEdges = startingEdges;
	}
	
	
}
