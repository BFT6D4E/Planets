
public class Edge {

	private Node start; //edge starts at start
	private Node end; //edge ends at end
	
	private double cost; //cost of the edge
	
	/**
	 * Constructor
	 * 
	 * @param start edge starts at start
	 * @param end edge ends at end
	 * @param cost cost of the edge
	 */
	public Edge(Node start, Node end, double cost) {
		this.start = start;
		this.end = end;
		this.cost = cost;
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		this.start = start;
	}

	public Node getEnd() {
		return end;
	}

	public void setEnd(Node end) {
		this.end = end;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
	
	
}
