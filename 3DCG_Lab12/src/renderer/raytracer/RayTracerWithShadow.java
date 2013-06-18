package renderer.raytracer;

import geomobj.GeomObj;
import light.Light;
import renderer.Intersection;
import renderer.Ray;
import scene.Scene;
import util.Colour;
import util.Vector;

public class RayTracerWithShadow extends RayTracer {

	public RayTracerWithShadow(Scene scene) {
		super(scene);
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
		
		Colour result = new Colour(Ir, Ig, Ib); 
		return result; 
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
