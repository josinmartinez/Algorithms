package Week2.Q1;


public class Edge implements Comparable<Edge>{
	Vertex node1;
	Vertex node2;
	Integer distante;
	
	public Edge(Vertex n1, Vertex n2, Integer d){
		node1=n1;
		node2=n2;
		distante=d;
	}
	
	public Vertex getNode1() {
		return node1;
	}

	public Vertex getNode2() {
		return node2;
	}

	public Integer getDistance(){ return distante;}
	
	@Override
	public int compareTo(Edge o) {
		return this.distante.compareTo(((Edge)o).getDistance());
	}

	@Override
	public String toString() {
		return "Edge [" + (node1 != null ? "node1=" + node1 + ", " : "")
				+ (node2 != null ? "node2=" + node2 + ", " : "")
				+ (distante != null ? "distante=" + distante : "") + "]";
	}
	
}
