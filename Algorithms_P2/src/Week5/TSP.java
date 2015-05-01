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

	private Map<Set<Byte>,Integer> indexInArray = new HashMap<Set<Byte>,Integer>();
	private List<List<Set<Byte>>> listMCombinaciones;
	
	public static void main(String[] args) throws FileNotFoundException {
		TSP tsp = new TSP();
		tsp.leer("D:\\data\\tsp.txt");
		tsp.calcule();
		//tsp.prueba(5);
	}
	
	private void calcule() {
		initializeA1andCombinaciones();
		for (byte m = 2; m <= ncities; m++){
			for (Set<Byte> s: setsOfSize(m)){
				for (Byte j: s){
					if (j == initCity) continue;
					A[calculeIndex(s)][j] = minValue(s,j);
				}
			}
		}
		System.out.println("SOL:"+solucion());
	}

	private void initializeA1andCombinaciones() {
		initializeDistances();
		initializeA();
		initializeSubsets();
	}

	private void initializeSubsets() {
		for (byte i=0;i<ncities;i++){
			Set<Byte> s = new HashSet<Byte>(1); s.add(i);
			indexInArray.put(s, (int)i);
		}
		listMCombinaciones = new ArrayList<List<Set<Byte>>>(2); //only need the last one to calculate a new combination
		Set<Byte> s = new HashSet<Byte>(1); s.add(initCity);
		List<Set<Byte>> list = new ArrayList<Set<Byte>>(1); list.add(s);
		listMCombinaciones.add(list);
		listMCombinaciones.add(null);
	}

	private void initializeA() {
		int subsets = (int)Math.pow(2, ncities-1)+(ncities-1);
		A = new float[subsets][ncities]; //A = new ArrayList<List<Double>>();
		for (int i=0; i<subsets; i++){
			if (i==initCity) A[i][initCity] = 0;
			else A[i][initCity] = Float.MAX_VALUE;
		}	
	}

	private void initializeDistances() {
		distances = new float[ncities][ncities];
		for (byte i=0;i<ncities;i++)
			for(byte j=i;j<ncities;j++){
				distances[i][j] = cities.get(i).distanceTo(cities.get(j));
				distances[j][i] = distances[i][j];
			}
	}

	private List<Set<Byte>> setsOfSize(byte m) {
		int temp =0; long capacity = factorial(ncities-1).divide(factorial(m-1).multiply(factorial(ncities-1-m+1))).longValue();
		if (capacity > Integer.MAX_VALUE) capacity=Integer.MAX_VALUE;
		int newindex = 0, oldindex =0;
		if (m%2 == 0) {newindex=1;oldindex=0;} else {newindex=0;oldindex=1;}
		listMCombinaciones.set(newindex,new ArrayList<Set<Byte>>((int)capacity));
		for (Set<Byte> set: listMCombinaciones.get(oldindex)){
			byte start = max(set);
			//if (start==ncities-1) break;
			for (byte i=start;i<ncities;i++){
				temp++;
				Set<Byte> s1 = new HashSet<Byte>(set);
				s1.add(i);
				if (s1.size() == m)	{
					listMCombinaciones.get(newindex).add(s1);
					indexInArray.put(s1, indexInArray.size());
				}
			}
		}
		System.out.println(m+" = "+listMCombinaciones.get(newindex).size()+"!"+temp);
		return listMCombinaciones.get(newindex);
	}

	private BigInteger factorial(int n) {
		BigInteger value = new BigInteger("1");
		for (int i=n;i>0;i--)
			value = value.multiply(new BigInteger(String.valueOf(i)));
		return value;
	}

	private byte max(Set<Byte> set) {
		byte max = Byte.MIN_VALUE;
		for (Byte b: set){
			if (b > max) max = b;
		}
		return ++max;
	}

	private float minValue(Set<Byte> s, Byte j) {
		float minValue = Float.MAX_VALUE;
		Set<Byte> s1 = new HashSet<Byte>(s);
		s1.remove(j);
		for (byte k: s){
			if ((k==j)) continue;//||(k==initial)
			else {
				float value = A[calculeIndex(s1)][k] + distances[k][j];
				if (minValue > value) minValue = value;
			}
		}
		return minValue;
	}

	private double solucion() {
		Set<Byte> allCities =  new HashSet<Byte>();
		for (byte i=0;i<ncities;i++){
			allCities.add(i);
		}
		double minValue = Double.MAX_VALUE;
		for (byte j=1;j<ncities;j++){
			double value = A[calculeIndex(allCities)][j] + distances[j][0];
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
		
	private int calculeIndex(Set<Byte> S){
		return indexInArray.get(S);
	}
}
