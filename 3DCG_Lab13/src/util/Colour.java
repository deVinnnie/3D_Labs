package util;

public class Colour {
	
	public float r;
	public float g;
	public float b;
	
	public Colour(){
	}
	
	public Colour(float r, float g, float b) {
		set(r,g,b);
	}
	
	public void set(float r, float g, float b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public void set(Colour colour){
		this.r = colour.r;
		this.g = colour.g;
		this.b = colour.b;
	}
	
	public void add(Colour colour){
		r += colour.r;
		g += colour.g;
		b += colour.b;
	}
	
	public Colour mult(Colour colour){
		return new Colour(r*colour.r, g*colour.g, b*colour.b);
	}
	
	public Colour mult(float a){
		return new Colour(r*a, g*a, b*a);
	}
	
	public float[] get4Tuple(){
		return new float[]{r,g,b,1f};
	}
	
	@Override
	public boolean equals(Object o){
		boolean isEqual = false; 
		if(o instanceof Colour){
			Colour c2 = (Colour) o; 
			if(this.r == c2.r && this.g == c2.g && this.b == c2.b){
				isEqual = true;
			}
		}
		return isEqual; 
	}
}
