package Week1;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Jobs {

	public class Task implements Comparable<Task>{
		Integer lenght, weight;
		Double ratio;
		
		public Task(Integer w, Integer l){
			lenght = l;
			weight = w;
			ratio = ratio(w,l);
		}
		
		private Double ratio(Integer w, Integer l){
			return (1.0)*w/l;
			//return (w - l)*1.0;
		}

		@Override
		public int compareTo(Task arg0) {
			Task t = (Task)arg0;
			if (this.ratio > t.ratio) return -1;
			else if (this.ratio < t.ratio) return 1;
			else if (this.weight > t.weight) return -1;
			else return 1;
		}

		@Override
		public String toString() {
			return "Task [weight=" + weight + ", lenght=" + lenght + ", ratio="+ ratio + "]";
		}
		
	}

	public List<Task> tasks;
	
	public Jobs() {
		leer();
	}
	
	private void calcular() {
		long total = 0;
		long completion = 0;
		for (Task t: tasks){
			total += t.weight*(t.lenght+completion);
			completion += t.lenght;
		}
		System.out.println(total);
	}
	
	private void show() {
		for (Task t: tasks){
			System.out.println(t);
		}
	}

	private void leer(){
		 try {
		        Scanner sc = new Scanner(new File(Paths.get("D:\\Cursos Coursera\\Coursera - Algoritmos II\\Ejercicios\\jobs.txt").toString()));
		        tasks = new ArrayList<Task>(sc.nextInt());
		        
		        while (sc.hasNextInt()) {
		        	int w = sc.nextInt();
		            int l = sc.nextInt();
		            Task t = new Task(w,l);
		            tasks.add(t);
		        }
		        sc.close();
		    } 
		    catch (FileNotFoundException e) {
		        e.printStackTrace();
		    }
	}

	public static void main(String[] args){
		Jobs j = new Jobs();
		
		Collections.sort(j.tasks);
		j.show();
		j.calcular();
	}

}
