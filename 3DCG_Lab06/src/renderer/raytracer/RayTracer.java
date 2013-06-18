package renderer.raytracer;

import light.Light;
import geomobj.GeomObj;
import renderer.Intersection;
import renderer.Ray;
import scene.Scene;
import util.*; 

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
	
	private Colour shadeHit(Ray ray, Intersection intersection){
		Vector m = intersection.getBestHitNormal(); 
		float Ir=0,Ig=0,Ib=0; 
		for(Light light : this.scene.getLights()){
			Vector s = new Vector(intersection.getBestHitPoint(), light.pos);
			s.normalize(); 
			float sDotM = s.dot(m); 
			Colour mtrlAmbient = intersection.getBestHitMaterial().getAmbient();
			if(sDotM>0){
				Colour mtrlDiffuse = intersection.getBestHitMaterial().getDiffuse(); 
				Ir += (mtrlDiffuse.r * light.colour.r * sDotM) + (mtrlAmbient.r*light.colour.r);
				Ig += (mtrlDiffuse.g * light.colour.g * sDotM) + (mtrlAmbient.g*light.colour.g);
				Ib += (mtrlDiffuse.b * light.colour.b * sDotM) + (mtrlAmbient.b*light.colour.b);
			}
			else{
				Ir += mtrlAmbient.r*light.colour.r; 
				Ig += mtrlAmbient.g*light.colour.g; 
				Ib += mtrlAmbient.b*light.colour.b; 
			}
		}
		
		Colour result = new Colour(Ir, Ig, Ib); 
		return result; 
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













