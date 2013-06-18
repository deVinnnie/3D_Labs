package renderer.raytracer;

import geomobj.GeomObj;
import renderer.Intersection;
import renderer.Ray;
import scene.Scene;
import util.*; 

public abstract class RayTracer {
	protected Scene scene;
	public int supersampling; 
	
	public RayTracer(Scene scene){
		this.scene = scene;
	}
	
	public Colour shade(Ray ray){
		Intersection intersection = this.getBestIntersection(ray);
		
		if(intersection.getNumHits() == 0){
			return scene.getBackGround(); 
		}
		else{
			return shadeHit(ray, intersection);
		}
	}
	
	protected abstract Colour shadeHit(Ray ray, Intersection intersection); 

	private Intersection getBestIntersection(Ray ray){
		Intersection bestIntersection = null; 
		
		for(GeomObj geomobj : scene.getObjects()){
			Intersection intersection = geomobj.intersection(ray); 
			if(intersection.getNumHits()>0){
				if(bestIntersection==null 
						|| bestIntersection.getBestHitTime() > intersection.getBestHitTime()){
					bestIntersection = intersection; 
				}
			}
		}	
		
		if(bestIntersection==null){
			bestIntersection = new Intersection(); 
		}
		
		return bestIntersection;
	}
}













