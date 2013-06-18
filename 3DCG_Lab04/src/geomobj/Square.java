package geomobj;

import renderer.HitInfo;
import renderer.Intersection;
import renderer.Ray;
import util.Point;
import util.Vector;

public class Square extends Shape {
	
	@Override
	public Intersection intersection(Ray ray) {
		Intersection intersection = new Intersection();
		
		if(Math.abs(ray.dir.z) < 0.00001){
			return intersection;
		}
		float t = -ray.start.z/ray.dir.z;
		if(t<=0.0) {
			return intersection;
		}
		Point hitPoint = ray.getPoint(t); 
		if (hitPoint.x > 1.0 || hitPoint.x < -1.0 || hitPoint.y > 1.0 || hitPoint.y < -1.0){
			return intersection;
		}
		intersection.add(new HitInfo(t, this, hitPoint, new Vector(0,0,1), true));
		return intersection;	
	}

	
}
