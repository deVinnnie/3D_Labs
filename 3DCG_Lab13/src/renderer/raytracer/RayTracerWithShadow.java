package renderer.raytracer;

import geomobj.GeomObj;
import light.Light;
import renderer.Intersection;
import renderer.Ray;
import scene.Scene;
import util.Colour;
import util.Point;
import util.Vector;

public class RayTracerWithShadow extends RayTracer {

	public RayTracerWithShadow(Scene scene) {
		super(scene);
	}

	@Override
	protected Colour shadeHit(Ray ray, Intersection intersection) {
		Vector m = intersection.getBestHitNormal(); 
		
		Colour colorAmbient = intersection.getBestHitMaterial().getAmbient();
		Colour mtrlDiffuse = intersection.getBestHitMaterial().getDiffuse(); 
		Colour res = new Colour(0f,0f,0f); 
		
		/*Startpunt van shadowfeeler verleggen, zodat het niet meer intersect met het object zelf. 
		Anders krijg je vreemde zwarte lijnen op de objecten tijdens renderen. */
		Point startPunt = intersection.getBestHitPoint(); 
		Vector temp = new Vector(ray.dir); //Een beetje terug: vector omkeren. 
		temp.mult(-0.01f); 
		
		for(Light light : this.scene.getLights()){
			//Add Ambient Component
			res.add(colorAmbient.mult(light.colour)); 
			
			/*Create Shadowfeeler*/
			Vector dir = new Vector(startPunt, light.pos);
			Ray shadowFeeler = new Ray(startPunt, dir); 
			
			if(!isInShadow(shadowFeeler)){
				//Calculate s . m => Cos 
				Vector s = new Vector(intersection.getBestHitPoint(), light.pos);
				s.normalize(); 
				float sDotM = s.dot(m);
				
				if(sDotM>0){
					//is in the light
					res.add(mtrlDiffuse.mult(light.colour.mult(sDotM))); 
				}
			}
			
		}
		return res;
	}
	
	private boolean isInShadow(Ray ray){
		boolean hit = false; 
		
		Vector vector = new Vector(ray.dir);
		vector.mult(0.0001f); 
		
		Point p = new Point(ray.start); 
		p.add(vector); 
		Ray editedRay = new Ray(p, ray.dir); 
		
		for(GeomObj obj : scene.getObjects()){
			if(obj.hit(editedRay)){
				hit = true; 
				break;
			}
		}
		return hit; 
	}
}
