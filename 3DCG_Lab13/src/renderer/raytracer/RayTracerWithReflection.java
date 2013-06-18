package renderer.raytracer;

import java.util.Properties;

import geomobj.GeomObj;
import light.Light;
import renderer.Intersection;
import renderer.Ray;
import scene.Scene;
import util.*;

/*public class RayTracerWithReflection extends RayTracer {

	private int maxRecursionDepth; 
	
	public RayTracerWithReflection(Scene scene, Properties prop) {
		super(scene);
		String recursionDepth = prop.getProperty("raytrace.reflection.maxRecursionDepth"); 
		this.maxRecursionDepth = Integer.parseInt(recursionDepth); 
	}
	
	@Override
	protected Colour shadeHit(Ray ray, Intersection intersection) {
		Vector m = intersection.getBestHitNormal(); 
		Colour colorAmbient = intersection.getBestHitMaterial().getAmbient();
		Colour colorDiffuse = intersection.getBestHitMaterial().getDiffuse(); 
		
		Colour result = new Colour(0,0,0); 
		
		//Startpunt van shadowfeeler verleggen, zodat het niet meer intersect met het object zelf. 
		//Anders krijg je vreemde zwarte lijnen op de objecten tijdens renderen. 
		Point startPunt = intersection.getBestHitPoint(); 
		Vector temp = new Vector(ray.dir); //Een beetje terug: vector omkeren. 
		temp.mult(-0.01f); 
		startPunt.add(temp); 
		
		for(Light light : this.scene.getLights()){
			//Add Ambient Component
			result.add(colorAmbient.mult(light.colour)); 
			
			//Create Shadowfeeler
			Vector dir = new Vector(startPunt, light.pos);
			Ray shadowFeeler = new Ray(startPunt, dir); 
			
			if(!isInShadow(shadowFeeler)){
				//Calculate s . m => Cos 
				Vector s = new Vector(intersection.getBestHitPoint(), light.pos);
				s.normalize(); 
				float sDotM = s.dot(m);
				
				if(sDotM>0){
					//is in the light
					result.add(colorDiffuse.mult(light.colour).mult(sDotM)); 
				}
			}
		}
		
		if(ray.recursionDepth <= this.maxRecursionDepth && intersection.getBestHitMaterial().reflectivity >= 0.1 ){
			Ray reflRay = this.computeReflectedRay(ray, intersection);//Maybe change to start later on
			Colour reflCol = this.shade(reflRay);
			float reflectivity = intersection.getBestHitMaterial().getReflectivity();
			result.add(reflCol.mult(reflectivity)); 	
		}

		return result; 
	}

	private Ray computeReflectedRay(Ray ray, Intersection intersection) {
		Vector m = intersection.getBestHitNormal(); 
		Vector dir = ray.dir;
		
		// r= dir - 2 (dir.m)/|m|² * m  
		Vector t1 = m.getMultiple(-2*dir.dot(m)); 
		Vector r = dir.getSom(t1); 
		
		Ray reflRay = new Ray(intersection.getBestHitPoint(), r); 
		reflRay.recursionDepth=(ray.recursionDepth+1); 
		return reflRay; 
	}

	private boolean isInShadow(Ray ray){
		boolean hit = false; 
		for(GeomObj obj : scene.getObjects()){
			if(obj.intersection(ray).getNumHits()>0){
				hit = true; 
				break;
			}
		}
		return hit; 
	}
}*/

public class RayTracerWithReflection extends RayTracer {

	private int maxRecursionDepth;
	
	public RayTracerWithReflection(Scene scene, Properties prop) {
		super(scene);
		maxRecursionDepth = Integer.parseInt(prop.getProperty("raytrace.reflection.maxRecursionDepth"));
	}

	@Override
	protected Colour shadeHit(Ray ray, Intersection inter) {
		Colour colourD = inter.getBestHitMaterial().diffuse;
		Colour colourA = inter.getBestHitMaterial().ambient;	
		Colour col = new Colour(0,0,0);
		Point start = inter.getBestHitPoint();
		//Vector temp = new Vector( inter.getBestHitNormal() );
		Vector temp = new Vector( ray.dir );
		temp.mult(-0.01f);
		start.add( temp );
		Ray shadowfeeler = new Ray(start);
		
		for(Light light : scene.getLights()){
			col.add(colourA.mult(light.colour));
			
			Vector dir = new Vector(start,light.pos);
			shadowfeeler.dir = dir;

			if(!isInShadow(shadowfeeler) ){
				Vector norms = new Vector(inter.getBestHitPoint(),light.pos);
				norms.normalize();
				Vector normm = inter.getBestHitNormal();
				float t = norms.dot(normm);
				if( t>0 ){
					col.add( colourD.mult( light.colour.mult(t) ) );
				}
			}
		}
		if(ray.recursionDepth <= this.maxRecursionDepth && inter.getBestHitMaterial().reflectivity >= 0.1 ){
			ray.recursionDepth++; 
			Ray reflRay = this.computeReflectedRay(ray, inter);//Maybe change to start later on
			Colour colr = this.shade(reflRay);
			col.add( colr.mult(inter.getBestHitMaterial().reflectivity) );
		}
		
		return col;
	}
	
	private Ray computeReflectedRay(Ray ray, Intersection best){
		Vector m = best.getBestHitNormal();
		Vector d = ray.dir;
		Vector r = d.getSom(m.getMultiple(d.dot(m)*-2)); 
		return new Ray(best.getBestHitPoint(),r);
	}
	
	private boolean isInShadow(Ray ray ){
		for( GeomObj object : scene.getObjects()){
			if( object.hit(ray) ) return true;
		}
		return false;
	}

}
