package renderer;

import util.Matrix;
import util.Point;
import util.Vector;

public class Ray {
	
	public Point start;
	public Vector dir;
	public int recursionDepth=1; 
	
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
	
	public Ray getInvTransfoRay(Matrix invMat){
		Point startInv = invMat.mult(start); 
		Vector dirInv = invMat.mult(dir);
		Ray rayInv = new Ray(startInv, dirInv); 
		return rayInv; 
	}

}
