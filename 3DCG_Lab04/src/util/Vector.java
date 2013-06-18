package util;

public class Vector {
	
	public float x;
	public float y;
	public float z;
	
	public Vector(){
	}
	
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector(Vector v){
		set(v);
	}
	
	public Vector(Point p){
		x = p.x;
		y = p.y;
		z = p.z;
	}
	
	public void set(Vector v){
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	// scalar multiplication
	public void mult(float a){
		x *= a;
		y *= a;
		z *= a;
	}
	
	public Vector getMultiple(float a){
		return new Vector(x*a,y*a,z*a); 
	}
	
	public void add(Vector v){
		x += v.x;
		y += v.y;
		z += v.z;
	}
	
	public Vector getProduct(Vector v){
		return new Vector(x+v.x, y+v.y,z+v.z); 
		
	}
	
	// a vector which starts at p1 and ends at p2
	public Vector(Point p1, Point p2){
		x = p2.x-p1.x;
		y = p2.y-p1.y;
		z = p2.z-p1.z;		
	}
	
	// dot product
	public float dot(Vector v){
		return x*v.x + y*v.y + z*v.z;
	}

	// cross product
	public Vector getCross(Vector v){
		return new Vector(y*v.z-z*v.y, z*v.x-x*v.z, x*v.y-y*v.x);
	}
	
	public void normalize(){
		float length = (float)Math.sqrt(x*x+y*y+z*z);
		x /= length;
		y /= length;
		z /= length;
	}
	
	public Vector getReverse(){
		return new Vector(-x, -y, -z);
	}
	
	public void reverse(){
		x=-x;
		y=-y;
		z=-z;
	}

}
