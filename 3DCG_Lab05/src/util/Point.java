package util;

public class Point {
	
	public float x;
	public float y;
	public float z;
		
	public Point(float x, float y, float z) {
		set(x,y,z);	
	}
	
	public Point(Point p){
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}
	
	public void set(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void add(Vector v){
		x += v.x;
		y += v.y;
		z += v.z; 
	}
	
	public Point getAdd(Vector v){
		return new Point(x+v.x, y+v.y, z+v.z);
	}
	
}
