package Week5;

public class Vertex {
	private double p1;
	private double p2;
	public Vertex(double p1, double p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
	}
	public double getP1() {
		return p1;
	}
	public void setP1(double p1) {
		this.p1 = p1;
	}
	public double getP2() {
		return p2;
	}
	public void setP2(double p2) {
		this.p2 = p2;
	}
	public float distanceTo(Vertex v){
		double vp1 = v.getP1();
		double vp2 = v.getP2();
		return (float)Math.sqrt(Math.pow(p1-vp1, 2) + Math.pow(p2-vp2, 2));
	}
}
