package Week2.Q2;


import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import Week2.Leader;


public class KlusterBig {

	private Leader[] leaders;
	private Map<Integer,VertexBig> hamilton; //hamiltonvalues,Vertice
	
	private int mask[]; 
	private void generateCombinatorialWith1and2bits(int nbits){
		int index = 0;
		for (int i=0;i<nbits;i++){
			Integer b = 000000000000000000000000 | 1 << i;
			mask[index++] = b;
			//System.out.println(Integer.toBinaryString(b));
			for (int j=i+1;j<nbits;j++){
				Integer c = b | 1 << j;
				mask[index++] = c;
				//System.out.println(Integer.toBinaryString(c));
			}
		}
	}

	private  void kClusterBig() {
		List<Integer> list  = new ArrayList<Integer>(hamilton.keySet());
		Queue<Integer> cola = new LinkedList<Integer>();
		int nKlusters=0;
		for (Integer valor: list){
			if (hamilton.get(valor).isExplored()) continue;
			cola.add(valor);
			nKlusters++;
			while (!cola.isEmpty()){
				VertexBig v = hamilton.get(cola.poll());
				v.setExplored(true);
				Integer leader = leaders[v.getName()].getName();
				
				//For each node checks all existing nodes with hamilton distance <= 2 and add them to same cluster
				for (int i = 0 ;i < mask.length; i++){
					Integer proximo = v.getValue() ^ mask[i];
					VertexBig next = hamilton.get(proximo);
					if (next != null){ //if exist it is assigned to same cluster
						leaders[next.getName()].setName(leader);
						if (!next.isExplored()){
							next.setExplored(true);
							cola.add(proximo); 
						}
					}
				}
			}
		}
		
		//How many different clusters?
		//Set<Leader> sol = new HashSet<Leader>(Arrays.asList(leaders));
		//sol.remove(null);
		//System.out.println(sol.size());
		System.out.println(nKlusters);
	}


	private void leerBig(String filename) throws FileNotFoundException{
		Scanner scan = new Scanner(new File(Paths.get(filename).toString()));
		try{
			Integer nnodes = scan.nextInt();
			Integer nbits  = scan.nextInt();
			this.leaders  = new Leader[nnodes];
			this.hamilton = new HashMap<Integer,VertexBig>(nnodes); 

			this.mask = new int[300];//Comb(24,2) + Comb(24,1) = 300
			generateCombinatorialWith1and2bits(nbits);

			int vertexName = 0;
			while (scan.hasNextInt()){
				int value = 0; 
				for (int j = 0;j<nbits;j++){
					try{
						Integer bit = scan.nextInt();
						value = value | bit << (nbits-j-1);
					}catch(Exception ex){System.out.print(vertexName+" ");ex.getStackTrace();}
				}
				if (hamilton.containsKey(value)){
					;
				}else{
					leaders[vertexName] = new Leader(vertexName);
					VertexBig v = new VertexBig(vertexName,value,leaders[vertexName]);
					hamilton.put(value, v);
					vertexName++;
				}
				scan.nextLine();
			}
		}finally{
			scan.close();
		}
	}

	public KlusterBig(String filepath) throws FileNotFoundException {
		leerBig(filepath);
	}

	public static void main(String[] args) throws FileNotFoundException {
		KlusterBig k = new KlusterBig("D:\\Cursos Coursera\\Coursera - Algoritmos II\\Ejercicios\\clustering_big.txt");
		k.kClusterBig();
	}
}
