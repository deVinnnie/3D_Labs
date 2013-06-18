package geomobj;

import renderer.Ray;
import util.Point;

public class BoxExtent {
	private float left, top, right, bottom, front, back; 
	
	public BoxExtent(float left, float top, float right, float bottom,
			float front, float back) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.front = front;
		this.back = back;
	}
	
	public boolean hit(Ray ray){
		float denom; 
		//FRONT FACE & Back Face
		denom = ray.dir.z;
		if (denom!=0){ //if ray not parallel with front face
			float numer = front-ray.start.z; 
			float tHit = numer / denom; //t-value of hitPoint between ray and front face plane
			
			if (tHit>=0){ //if hitpoint in front of startpoint of ray
				Point hitPoint = ray.getPoint(tHit); //compute hitPoint
				if (hitPoint.x<=right 
						&& hitPoint.x>=left 
						&& hitPoint.y<=top 
						&& hitPoint.y>=bottom){
					//hitPoint inside face 
					return true;
				}
			}
			
			numer = back-ray.start.z; 
			tHit = numer / denom; 
			
			if (tHit>=0){ //if hitpoint in front of startpoint of ray
				Point hitPoint = ray.getPoint(tHit); //compute hitPoint
				if (hitPoint.x<=right 
						&& hitPoint.x>=left 
						&& hitPoint.y<=top 
						&& hitPoint.y>=bottom){
					//hitPoint inside face 
					return true;
				}
			}
		}
		
		//Left Face & Right Face
		denom = ray.dir.x;
		if (denom!=0){ //if ray not parallel with front face
			float numer = left-ray.start.x; 
			float tHit = numer / denom; //t-value of hitPoint between ray and front face plane
			if (tHit>=0){ //if hitpoint in front of startpoint of ray
				Point hitPoint = ray.getPoint(tHit); //compute hitPoint
				if (hitPoint.z<=front 
						&& hitPoint.z>=back 
						&& hitPoint.y<=top 
						&& hitPoint.y>=bottom){
					//hitPoint inside face 
					return true;
				}
			}
			
			numer = right-ray.start.x; 
			tHit = numer / denom; //t-value of hitPoint between ray and front face plane
			if (tHit>=0){ //if hitpoint in front of startpoint of ray
				Point hitPoint = ray.getPoint(tHit); //compute hitPoint
				if (hitPoint.z<=front
						&& hitPoint.z>=back 
						&& hitPoint.y<=top 
						&& hitPoint.y>=bottom){
					//hitPoint inside face 
					return true;
				}
			}
		}
		
		//Top Face & Bottom Face
		denom = ray.dir.y;
		if (denom!=0){ //if ray not parallel with front face
			float numer = top-ray.start.y; 
			float tHit = numer / denom; //t-value of hitPoint between ray and front face plane
			if (tHit>=0){ //if hitpoint in front of startpoint of ray
				Point hitPoint = ray.getPoint(tHit); //compute hitPoint
				if (hitPoint.z<=front 
						&& hitPoint.z>=back 
						&& hitPoint.x<=right 
						&& hitPoint.x>=left){
					//hitPoint inside face 
					return true;
				}
			}
			
			numer = bottom-ray.start.y; 
			tHit = numer / denom; //t-value of hitPoint between ray and front face plane
			if (tHit>=0){ //if hitpoint in front of startpoint of ray
				Point hitPoint = ray.getPoint(tHit); //compute hitPoint
				if (hitPoint.z<=front
						&& hitPoint.z>=back 
						&& hitPoint.x<=right 
						&& hitPoint.x>=left){
					//hitPoint inside face 
					return true;
				}
			}
		}
		return false; 
	}
}
