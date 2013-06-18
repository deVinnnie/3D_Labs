package renderer.raytracer;

import light.Light;
import renderer.Intersection;
import renderer.Ray;
import scene.Scene;
import util.Colour;
import util.Vector;

public class BasicRayTracer extends RayTracer {

	public BasicRayTracer(Scene scene) {
		super(scene);
	}

	@Override
	protected Colour shadeHit(Ray ray, Intersection intersection){
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
}
