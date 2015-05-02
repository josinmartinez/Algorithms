package Week5;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class TSP {
	private List<Vertex> cities; private byte ncities; private byte initCity;
	private float[][]A;
	private float[][]distances;
	private Map<Integer,Integer> mapa;
	
	public static void main(String[] args) throws FileNotFoundException {
		TSP tsp = new TSP();
		tsp.leer("D:\\data\\tsp.txt");
		tsp.calcule();
	}

	private int nextValue(int a) {
		  /* works for any word length */
		  int c = (a & -a);
		  int r = a+c;
		  return (((r ^ a) >> 2) / c) | r; 
	}
	
	private int[] generateSubset(int m){
		int nbits = (int)Math.pow(2, m)-1 ;
		int val = nbits;
		int capacity = (m==1)?ncities:(int)factorial(ncities-1).divide(factorial(m-1).multiply(factorial(ncities-1-m+1))).longValue();
		int []temp = new int[capacity];
		int index = 0;
		temp[index++] = val;
		mapa.put(val, mapa.size());
		val = nextValue(val);
		while (val < (int)Math.pow(2, ncities)){
			if (val % 2 !=0 || nbits == 1){
				temp[index++] = val;
				mapa.put(val, mapa.size());
			}
			val = nextValue(val);
		}
		return temp;
	}
	
	private void calcule() {
		System.out.print("Inicializar.....");
		initializeA1andCombinaciones();
		System.out.println("[Done]");
		for (byte m = 2; m <= ncities; m++){
			int contador = 0;
			System.out.print(m);
			for (int s: generateSubset(m)){
				contador++;
				byte[] cities = generateCities(s,m); 
				for (Byte j: cities){
					if (j == initCity) continue;
					A[mapa.get(s)][j] = minValue(s,j,cities);
				}
			}
			System.out.println(" "+contador);
		}
		System.out.println("SOL:"+solucion());
	}

	private byte[] generateCities(int s, int m) {
		byte [] temp = new byte[m];
		byte index=0;
		for (byte i=0;i<ncities;i++){
			if (((s>>i)&1) == 1)
				temp[index++] = i;
		}
		return temp;
	}

	private void initializeA1andCombinaciones() {
		initializeDistances();
		initializeA();
	}

	private void initializeA() {
		int subsets = (int)Math.pow(2, ncities-1)+(ncities-1);
		mapa = new HashMap<Integer,Integer>(subsets);
		A = new float[subsets][ncities]; 
		for (int i=0; i<subsets; i++){
			if (i==initCity) A[i][initCity] = 0;
			else A[i][initCity] = Float.MAX_VALUE;
		}
		generateSubset(1);
	}

	private void initializeDistances() {
		distances = new float[ncities][ncities];
		for (byte i=0;i<ncities;i++)
			for(byte j=i;j<ncities;j++){
				distances[i][j] = cities.get(i).distanceTo(cities.get(j));
				distances[j][i] = distances[i][j];
			}
	}

	private BigInteger factorial(int n) {
		BigInteger value = new BigInteger("1");
		for (int i=n;i>0;i--)
			value = value.multiply(new BigInteger(String.valueOf(i)));
		return value;
	}

	private float minValue(int s, Byte j, byte[] cities) {
		float minValue = Float.MAX_VALUE;
		int op = 1 << j;
		s = s ^ op;

		for (byte k: cities){
			if ((k==j)) continue;//||(k==initial)
			else {
				float value = A[mapa.get(s)][k] + distances[k][j];
				if (minValue > value) minValue = value;
			}
		}
		return minValue;
	}


	private double solucion() {
		int allCities = (int)Math.pow(2, ncities)-1;
		double minValue = Double.MAX_VALUE;
		for (byte j=1;j<ncities;j++){
			double value = A[mapa.get(allCities)][j] + distances[j][0];
			if (minValue > value) minValue = value;
		}
		return minValue;
	}

	private void leer(String filename) throws FileNotFoundException{
		System.out.print("Leer.....");
		Scanner scan = new Scanner(new File(filename));
		try{
			ncities = scan.nextByte();
			cities = new ArrayList<Vertex>(ncities);
			while (scan.hasNext("[0-9]+.[0-9]+")){
				double x = Double.valueOf(scan.next());
				double y = Double.valueOf(scan.next());
				cities.add(new Vertex(x,y));
			}
			System.out.println("[DONE]");
		}finally{
			scan.close();
		}
	}
}
