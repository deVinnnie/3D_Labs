package material;

import util.Colour;

public class Material{
	private Colour colour;

	public Material(){
		this.setColour(new Colour(255,0,0)); 
	}
	
	public Material(Material material){
		this.setColour(material.getColour()); 
	}
	
	public Material(Colour colour){
		this.setColour(new Colour(colour.r, colour.g, colour.b)); 
	}
	
	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}	
}
