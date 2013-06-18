package geomobj.bool;

import geomobj.GeomObj;

public abstract class Boolean implements GeomObj{
	public GeomObj left; 
	public GeomObj right;
	
	public Boolean(GeomObj left, GeomObj right) {
		this.left = left; 
		this.right = right;  
	}
}
