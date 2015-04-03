package Week1;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Prim { //naive and forward implementation

	class Edge implements Comparable<Edge>{
		Integer v1;
		Integer v2;
		public Long cost;
		public Edge(Integer v1,Integer v2, Long cost){
			this.v1=v1;
			this.v2=v2;
			this.cost=cost;
		}
		@Override
		public String toString() {
			return "Edge [v1=" + v1 + ", v2=" + v2 + ", cost=" + cost + "]";
		}
		@Override
		public int compareTo(Edge o) {
			Edge eo = (Edge)o;
			return (this.cost > eo.cost)?1:-1;
		}
		public boolean cutedge(Set<Integer> X, Set<Integer> DV){
			if ((X.contains(v1) && DV.contains(v2)) || (X.contains(v2) && DV.contains(v1)))
				return true; 
			else return false;
		}
	}

	Set<Integer> V;// Set of Vertices G(V,E)
	List<Edge> E;  // List of edges G(V,E)
	
	Set<Integer> X;// Set of Vertices X
	List<Edge> T = new ArrayList<Edge>(); //T is the MST (minimum spanning tree)

	public Prim(String filepath) throws FileNotFoundException {
		leer(filepath);
	}

	private void leer(String path) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(Paths.get(path).toString()));
		try {
			Integer nnodos = sc.nextInt();
			Integer nedges = sc.nextInt();
			V = new HashSet<Integer>(nnodos);
			X = new HashSet<Integer>(nnodos);
			E = new ArrayList<Edge>(nedges);

			while (sc.hasNextInt()) {
				int v1 = sc.nextInt();
				int v2 = sc.nextInt();
				long cost = sc.nextLong();

				V.add(v1);
				V.add(v2);
				Edge e= new Edge(v1,v2,cost);
				E.add(e);

				System.out.println(e);
			}

			Collections.sort(E);
			System.out.println(E.size());
		} 
		finally{
			sc.close();
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Prim p = new Prim("D:\\Cursos Coursera\\Coursera - Algoritmos II\\Ejercicios\\edges.txt"); 
		
		p.X.add(p.E.get(0).v1); //Initialize X with first Vertice with the minimun cost on one of its edges.(E is ordering by cost)
		Set<Integer> df = new HashSet<Integer>(p.V); //Vertices not used
		
		while (p.X.size() != p.V.size()){ //While don't pass for all Vertices (V)
			Long min = Long.MAX_VALUE;
			Edge edge = null;
			df.removeAll(p.X);
			//choose the cheapest edge e (X,V)
			for (Edge e: p.E){ //Brute-force. It would be better using heaps
				if (e.cutedge(p.X,df)){
					if (e.cost < min){
						min = e.cost;
						edge = e;
					}
				}
			}
			p.T.add(edge); //add edge to T (MST)
			Integer v; 
			if (p.X.contains(edge.v1)) v = edge.v2;
			else v = edge.v1;
			p.X.add(v); //add v to X
		}
		
		
		Long cost = 0l; 
		for (Edge ed: p.T){
			cost += ed.cost;
		}
		
		System.out.println(cost);
	}

	//0(nm) It could be better implemented O(mlogn)
}
