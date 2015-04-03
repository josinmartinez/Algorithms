package Week2.Q1;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * initially, each point in a separate cluster
 * repeat until only k clusters:
 * 	let p,q = closest pair of separated points
 * 	merge the clusters containing p&q into a single cluster
 * 
 * 
 * just like kruskal's MST algorithm, but stopped early.
 * 
 * #nodes
 * node1 node2 cost
 * */

public class Kluster {
	
	private List<Vertex> vertices;
	private List<Edge> edges = new ArrayList<Edge>();
	private HashSet<Integer> leadersNames ;

	private final Integer finalK; //k Clustering
	private Integer currentK; //at the beginning start with # vertices
		
	private  void kCluster() {
		Collections.sort(edges); //Sorted edges by distance
		int edgeIndex = 0;
		while (currentK > finalK){ 
			while(edgeIndex < edges.size()){ 
				Edge e = edges.get(edgeIndex);
				Vertex n1 = e.getNode1(); 
				Vertex n2 = e.getNode2();
				edgeIndex++;
				int i1 = vertices.indexOf(n1);
				int i2 = vertices.indexOf(n2);

				//if the two vertices of the chosen edge belong different cluster
				if (vertices.get(i1).getLeaderName() != vertices.get(i2).getLeaderName()){
					Vertex oldleader = vertices.get(i2).leader;
					Vertex newLeader = vertices.get(i1).leader;
					for (int i=0;i<vertices.size();i++){ //it could be better the leader update
						if (oldleader.equals(vertices.get(i).leader)){
							vertices.get(i).leader = newLeader;
						}
					}
					leadersNames.remove(oldleader.name);
					currentK = leadersNames.size();
					break; //break inner while to check if this union gets the finalK
				}
			}
		}
		//Calculate the closes distance between different clusters, 
		//next sorted edge with vertices in different clusters, prior edges have two vertices in same cluster
		do{
			Edge e = edges.get(edgeIndex);
			Vertex n1 = e.getNode1(); 
			Vertex n2 = e.getNode2();
			int i1 = vertices.indexOf(n1);
			int i2 = vertices.indexOf(n2);
			if (vertices.get(i1).getLeaderName() != vertices.get(i2).getLeaderName()) break;
			else edgeIndex++;
		}while(true);
		System.out.println(edges.get(edgeIndex).distante);
	}
	
	private void leer(String filename) throws FileNotFoundException{
		Scanner scan = new Scanner(new File(Paths.get(filename).toString()));
		try{
			Integer nnodes = scan.nextInt();
			this.leadersNames = new HashSet<Integer>(nnodes);
			
			Set<Vertex> svertices = new HashSet<Vertex>(nnodes);//temp
			while (scan.hasNextInt()){
				Integer node1 = scan.nextInt();
				Integer node2 = scan.nextInt();
				Integer distance = scan.nextInt();
				Vertex n1= new Vertex(node1);
				Vertex n2= new Vertex(node2);
				this.edges.add(new Edge(n1,n2,distance));
				svertices.add(n1);
				svertices.add(n2);
				this.leadersNames.add(n1.name);
				this.leadersNames.add(n2.name);
			}
			this.vertices = new ArrayList<Vertex>(svertices);
			this.currentK = nnodes; 
		}finally{
			scan.close();
		}
	}
	
	public Kluster(Integer k, String filepath) throws FileNotFoundException {
		this.finalK = k;
		leer(filepath);
	}

	public static void main(String[] args) throws FileNotFoundException {
		Kluster k = new Kluster(4,"D:\\Cursos Coursera\\Coursera - Algoritmos II\\Ejercicios\\clustering1.txt");
		k.kCluster();
	}
}
