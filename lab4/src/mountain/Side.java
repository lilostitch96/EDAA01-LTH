package mountain;

public class Side {
	private Point a;
	private Point b;
	private Point m;
	
	public Side(Point a, Point b, Point m) {
		this.a = a;
		this.b = b;
		this.m = m;
	}
	
	public Side(Point a, Point b) {
		this.a = a;
		this.b = b;
	}
	
	public Point getM() {
		return m;
	}
	
	public boolean equals(Object obj) {
		Side s = (Side) obj;
		return (s.a == this.a) && (s.b == this.b) || (s.b == this.a) && (s.a == this.b);
	}
	
	public int hashCode() {
        return a.hashCode() + b.hashCode();
}
}
