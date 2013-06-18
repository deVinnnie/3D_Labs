package renderer.raytracer;

import geomobj.GeomObj;
import renderer.Intersection;
import renderer.Ray;
import scene.Scene;
import util.Colour;

public class RayTracer {
	
	private Scene scene;
	
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
	
	private Colour shadeHit(Ray ray, Intersection intersection) {
		return intersection.getBestHitMaterial().getColour(); 
	}

	private Intersection getBestIntersection(Ray ray){
		Intersection bestIntersection =new Intersection(); 
		
		for(GeomObj geomobj : scene.getObjects()){
			Intersection intersection = geomobj.intersection(ray); 
			if(bestIntersection.getNumHits()==0){
				bestIntersection = intersection; 
			}
			else if(intersection.getNumHits()>0 &&
					bestIntersection.getBestHitTime() > intersection.getBestHitTime()){
				bestIntersection = intersection; 
			}
		}	
		return bestIntersection;
	}
}













