package geomobj;

import renderer.HitInfo;
import renderer.Intersection;
import renderer.Ray;
import util.Point;
import util.Vector;

public class Square extends Shape {
	
	@Override
	public Intersection intersection(Ray ray) {
		Ray rayInv = ray.getInvTransfoRay(this.transfo.invMat);  
		Intersection intersection = new Intersection();
		
		if(Math.abs(rayInv.dir.z) < 0.00001){
			return intersection;
		}
		float t = -rayInv.start.z/rayInv.dir.z;
		if(t<=0.001) {
			return intersection;
		}
		Point hitPoint = rayInv.getPoint(t); 
		if (hitPoint.x > 1.0 || hitPoint.x < -1.0 || hitPoint.y > 1.0 || hitPoint.y < -1.0){
			return intersection;
		}
		Vector realHitNormalVector = this.transfo.invMat.getTranspose().mult(new Vector(0,0,1)); 
		realHitNormalVector.normalize(); 
		intersection.add(new HitInfo(t, this.mtrl, ray.getPoint(t), realHitNormalVector, true));
		return intersection;	
	}

	@Override
	public boolean hit(Ray ray) {
		Ray rayInv = ray.getInvTransfoRay(this.transfo.invMat);  
		if(Math.abs(rayInv.dir.z) < 0.00001){
			return false;
		}
		float t = -rayInv.start.z/rayInv.dir.z;
		if(t<=0.001) {//0.0=>0.001
			return false;
		}
		Point hitPoint = rayInv.getPoint(t); 
		if (hitPoint.x > 1.0 || hitPoint.x < -1.0 || hitPoint.y > 1.0 || hitPoint.y < -1.0){
			return false;
		}
		return true;	
	}	
}
