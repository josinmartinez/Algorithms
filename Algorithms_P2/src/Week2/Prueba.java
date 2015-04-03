package Week2;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class Prueba {

	class lead{
		private Integer name;

		public Integer getName() {
			return name;
		}

		public void setName(Integer name) {
			this.name = name;
		}

		public lead(Integer name) {
			super();
			this.name = name;
		}

		@Override
		public String toString() {
			return "lead [name=" + name + "]";
		}
		
	}

	class obj {
		lead l;

		public obj(lead l) {
			super();
			this.l = l;
		}

		public lead getL() {
			return l;
		}

		public void setL(lead l) {
			this.l = l;
		}

		@Override
		public String toString() {
			return "obj [l=" + l + "]";
		}
		
	}
	
	lead []leaders = new lead[5];
	obj [] objs    = new obj[5];
	
	public void metodo(){
		for (int i=0;i<5;i++){
			lead l = new lead(i);
			leaders[i]=l;
			obj o = new obj(leaders[i]);
			objs[i]=o;
		}
		Set sol = new HashSet(Arrays.asList(leaders));
		System.out.println("Problem 2: "+ sol.size());
		System.out.println(objs[0]+"|"+objs[1]+"|"+objs[2]+"|"+objs[3]+"|"+objs[4]);
		leaders[0].setName(3); //affects objs
		leaders[1]=leaders[0]; //no affects objs
		leaders[2].setName(leaders[1].getName()); //affects objs		
		System.out.println(objs[0]+"|"+objs[1]+"|"+objs[2]+"|"+objs[3]+"|"+objs[4]);
		leaders[0]=leaders[4];
		System.out.println(objs[0]+"|"+objs[1]+"|"+objs[2]+"|"+objs[3]+"|"+objs[4]);
	}
	
	public static void main(String[] args) {
		Prueba p = new Prueba();
		p.metodo();
	}
}