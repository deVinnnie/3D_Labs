package geomobj;

import transfo.Transfo;
import material.Material;

public abstract class Shape implements GeomObj {
	public Material mtrl; 
	public Transfo transfo; 
	
	public Shape(){
		this.mtrl = new Material();
		this.transfo = new Transfo(); 
	}

	public Transfo getTransfo() {
		return transfo;
	}

	public void setTransfo(Transfo transfo) {
		this.transfo = transfo;
	}
}
