package renderer.raytracer;

import java.util.Properties;

import geomobj.GeomObj;
import light.Light;
import renderer.Intersection;
import renderer.Ray;
import scene.Scene;
import util.*;

public class RayTracerWithReflection extends RayTracer {

	private int maxRecursionDepth; 
	
	public RayTracerWithReflection(Scene scene, Properties prop) {
		super(scene);
		this.maxRecursionDepth = Integer.parseInt(prop.getProperty("raytrace.reflection.maxRecursionDepth")); 
	}

	@Override
	protected Colour shadeHit(Ray ray, Intersection intersection) {
		Vector m = intersection.getBestHitNormal(); 
		float Ir=0,Ig=0,Ib=0; 
		for(Light light : this.scene.getLights()){
			//Add Ambient Component
			Colour mtrlAmbient = intersection.getBestHitMaterial().getAmbient();
			Ir += mtrlAmbient.r*light.colour.r; 
			Ig += mtrlAmbient.g*light.colour.g; 
			Ib += mtrlAmbient.b*light.colour.b; 
			
			//Create Shadowfeeler
			Vector dir = new Vector(intersection.getBestHitPoint(), light.pos);
			Ray shadowFeeler = new Ray(intersection.getBestHitPoint(), dir); 
			
			if(!isInShadow(shadowFeeler)){
				//Calculate s . m => Cos 
				Vector s = new Vector(intersection.getBestHitPoint(), light.pos);
				s.normalize(); 
				float sDotM = s.dot(m);
				
				if(sDotM>0){
					//is in the light
					Colour mtrlDiffuse = intersection.getBestHitMaterial().getDiffuse(); 
					Ir += (mtrlDiffuse.r * light.colour.r * sDotM);
					Ig += (mtrlDiffuse.g * light.colour.g * sDotM);
					Ib += (mtrlDiffuse.b * light.colour.b * sDotM);
				}
			}
			
		}
		
		if(ray.recursionDepth <= this.maxRecursionDepth && 
				intersection.getBestHitMaterial().getReflectivity()>=0.1){
				Ray reflRay = computeReflectedRay(ray , intersection);
				float reflectivity = intersection.getBestHitMaterial().getReflectivity();
				Colour reflCol = shade(reflRay); 
				Ir += reflCol.r*reflectivity; 
				Ig += reflCol.g*reflectivity; 
				Ib += reflCol.b*reflectivity; 		
	}

		
		Colour result = new Colour(Ir, Ig, Ib); 
		return result; 
	}
	
	private Ray computeReflectedRay(Ray ray, Intersection intersection) {
		Point start = intersection.getBestHitPoint(); 
		Vector m = intersection.getBestHitNormal(); 
		Vector dir = ray.dir;
		
		// r= dir - 2 (dir.m)/|m|² * m  
		Vector t1 = m.getMultiple(-2*dir.dot(m)); 
		Vector r = dir.getSom(t1); 
		
		Ray reflRay = new Ray(start, r); 
		reflRay.recursionDepth=ray.recursionDepth+1; 
		return reflRay; 
	}

	private boolean isInShadow(Ray ray){
		boolean hit = false; 
		for(GeomObj obj : scene.getObjects()){
			if(obj.hit(ray)){
				hit = true; 
				break;
			}
		}
		return hit; 
	}
}
