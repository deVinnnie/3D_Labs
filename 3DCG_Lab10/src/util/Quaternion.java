package util;

public class Quaternion {
	public float a,b,c,d;
	
	public Quaternion(float a, float b, float c, float d){
		this.a = a; 
		this.b = b; 
		this.c = c; 
		this.d = d; 
	}
	
	public Quaternion(Point point){
		this.a = 0; 
		this.b = point.x; 
		this.c = point.y; 
		this.d = point.z; 
	}
	
	public Quaternion(float degrees, Vector r){
		double radians = Math.toRadians(degrees); 
		this.a = (float) Math.cos(radians/2.0); 
		float sinus = (float) Math.sin(radians/2.0); 
		this.b = sinus*r.x; 
		this.c = sinus*r.y;
		this.d = sinus*r.z; 
	}
	
	public Quaternion mult(Quaternion q){
		float a = (this.a*q.a - this.b*q.b - this.c*q.c - this.d*q.d); 
		float b = (this.a*q.b + this.b*q.a + this.c*q.d - this.d*q.c); 
		float c = (this.a*q.c - this.b*q.d + this.c*q.a + this.d*q.b); 
		float d = (this.a*q.d + this.b*q.c - this.c*q.b + this.d*q.a); 
		Quaternion result = new Quaternion(a,b,c,d); 
		
		return result; 
	}
	
	public Quaternion conjugate(){
		return new Quaternion(this.a, -this.b, -this.c, -this.d); 
	}
}
