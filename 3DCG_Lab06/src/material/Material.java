package material;

import util.Colour;

public class Material{
	public Colour diffuse;
	public Colour ambient = new Colour((int) 0.2,0,0); 

	public Material(){
		this.setDiffuse(new Colour(1,0,0)); 
	}
	
	public Material(Colour colour){
		this.setDiffuse(new Colour(colour.r, colour.g, colour.b));
	}
	
	public Material(Material material){
		this.setDiffuse(material.getDiffuse()); 
		this.setAmbient(material.getAmbient()); 
	}
	
	public Colour getDiffuse(){
		return diffuse;
	}

	public void setDiffuse(Colour colour) {
		this.diffuse = colour;
	}

	public Colour getAmbient(){
		return ambient;
	}

	public void setAmbient(Colour ambient) {
		this.ambient = ambient;
	}	
}
