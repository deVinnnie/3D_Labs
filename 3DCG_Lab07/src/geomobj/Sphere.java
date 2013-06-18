package geomobj;

import renderer.HitInfo;
import renderer.Intersection;
import renderer.Ray;
import util.Point;
import util.Vector;

public class Sphere extends Shape{

	@Override
	public Intersection intersection(Ray ray) {
		//Reken verder met inverse getransformeerde ray
		Ray rayInv = ray.getInvTransfoRay(this.transfo.invMat);
		
		Intersection intersection= new Intersection(); 
		float a = rayInv.dir.dot(rayInv.dir); 
		Vector startVector = new Vector(rayInv.start.x, rayInv.start.y, rayInv.start.z); 
		float b = 2 * startVector.dot(rayInv.dir); 
		float c = startVector.dot(startVector) -1;
		
		float D = (float) (Math.pow(b, 2) - (4.0*a*c));  
		
	
		if(D==0){
			float t = (-b) / (2*a);  
			if(t>=0){
				Point point = ray.start.getAdd(ray.dir.getMultiple(t)); 
				Vector normalVector = new Vector(point);
				Vector realNormalVector = this.transfo.invMat.getTranspose().mult(normalVector); 
				realNormalVector.normalize(); 
				
				boolean isEntering = false; 
				intersection.add(new HitInfo(t, this.mtrl, point, realNormalVector, isEntering));
			}
		}
		else if(D>0){
			float t1 = (float) ((-b +Math.sqrt(D))/(2*a));
			float t2 = (float) ((-b -Math.sqrt(D))/(2*a));
			
			if(t2>=0){
				Point point = ray.start.getAdd(ray.dir.getMultiple(t2)); 
				boolean isEntering = false; 
				if(t2<t1){isEntering = true;} 
				
				Vector normalVector = new Vector(point);
				Vector realNormalVector = this.transfo.invMat.getTranspose().mult(normalVector); 
				realNormalVector.normalize(); 
				intersection.add(new HitInfo(t2, this.mtrl, point, realNormalVector, isEntering));
			}
			
			if(t1>=0){
				Point point = ray.start.getAdd(ray.dir.getMultiple(t1)); 
				boolean isEntering = false; 
				if(t1<t2){isEntering = true;} 
				Vector normalVector = new Vector(point);
				Vector realNormalVector = this.transfo.invMat.getTranspose().mult(normalVector); 
				realNormalVector.normalize(); 
				intersection.add(new HitInfo(t1, this.mtrl, point, normalVector, isEntering));
			}
		}
		return intersection;
	}
}
