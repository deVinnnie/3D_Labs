package material;

import util.Colour;

public class Material{
	public Colour diffuse = new Colour(0f,0f,0f); 
	public Colour ambient = new Colour(0.2f,0.0f,0.0f); 
	public float reflectivity=0;
	
	public Material(){
		this.setDiffuse(new Colour(1,0,0)); 
	}
	
	public Material(Colour colour){
		this.setDiffuse(colour);
	}
	
	public Material(Material material){
		this.setDiffuse(material.getDiffuse()); 
		this.setAmbient(material.getAmbient()); 
		this.setReflectivity(material.getReflectivity()); 
	}
	
	public Colour getDiffuse(){
		return diffuse;
	}

	public void setDiffuse(Colour colour) {
		this.diffuse.set(colour);
	}

	public Colour getAmbient(){
		return ambient;
	}

	public void setAmbient(Colour ambient) {
		this.ambient.set(ambient);
	}	
	
	public void setReflectivity(float r){
		this.reflectivity = r; 
	}
	
	public float getReflectivity(){
		return this.reflectivity;
	}
}
