package geomobj;

import renderer.HitInfo;
import renderer.Intersection;
import renderer.Ray;
import util.Point;
import util.Vector;

public class Sphere extends Shape{

	@Override
	public Intersection intersection(Ray ray) {
		Intersection intersection= new Intersection(); 
		float a = ray.dir.dot(ray.dir); 
		Vector startVector = new Vector(ray.start.x, ray.start.y, ray.start.z); 
		float b = 2 * startVector.dot(ray.dir); 
		float c = startVector.dot(startVector) -1;
		
		float D = (float) (Math.pow(b, 2) - (4.0*a*c));  
		
	
		if(D<0){
		}
		else if(D==0){
			float t = (-b) / (2*a);  
			if(t>=0){
				Point point = ray.start.getAdd(ray.dir.getMultiple(t)); 
				Vector normalVector = new Vector(point);
				boolean isEntering = false; 
				intersection.add(new HitInfo(t, this, point, normalVector, isEntering));
			}
		}
		else if(D>0){
			float t1 = (float) ((-b +Math.sqrt(D))/(2*a));
			float t2 = (float) ((-b -Math.sqrt(D))/(2*a));
			
			if(t1>=0){
				Point point = ray.start.getAdd(ray.dir.getMultiple(t1)); 
				Vector normalVector = new Vector(point);
				boolean isEntering = false; 
				if(t1<t2){isEntering = true;} 
				intersection.add(new HitInfo(t1, this, point, normalVector, isEntering));
			}
			
			if(t2>=0){
				Point point = ray.start.getAdd(ray.dir.getMultiple(t2)); 
				Vector normalVector = new Vector(point);
				boolean isEntering = false; 
				if(t2<t1){isEntering = true;} 
				intersection.add(new HitInfo(t2, this, point, normalVector, isEntering));
			}
		}
		return intersection;
	}
}
