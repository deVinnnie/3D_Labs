package geomobj;

import renderer.Intersection;
import renderer.Ray;

public interface GeomObj {
	
	/*
	 * This method should return an object of the Intersection class
	 * containing information about all the hits (with positive hittimes)
	 * between this object and the given ray.
	 * 
	 * The returned Intersection object should NOT contain hits with negative hittimes.
	 * 
	 * In case this object does not intersect with the given ray, an Intersection object
	 * with an empty arraylist should be returned.
	 */
	public Intersection intersection(Ray ray);	
	
	public boolean hit(Ray ray); 
}
