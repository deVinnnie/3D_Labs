package renderer;

import util.Point;
import util.Vector;

public class Ray {
	
	public Point start;
	public Vector dir;
	
	public Ray(Point start){
		this.start = new Point(start);
		this.dir = new Vector();
	}
	
	public Ray(Point start, Vector dir){
		this.start = new Point(start);
		this.dir = new Vector(dir);
	}	
	
	public Point getPoint(float t){
		return start.getAdd(dir.getMultiple(t)); 
	}
}
