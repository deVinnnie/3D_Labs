package geomobj.bool;

import material.Material;
import geomobj.GeomObj;
import renderer.HitInfo;
import renderer.Intersection;
import renderer.Ray;

public class IntersectionBoolean extends Boolean {

	public IntersectionBoolean(GeomObj left, GeomObj right) {
		super(left, right);
	}

	@Override
	public Intersection intersection(Ray ray) {
		Intersection combInter = new Intersection();  
		Intersection leftInter = this.left.intersection(ray); 
		Intersection rightInter = this.right.intersection(ray); 
		
		//ChangeSet C 
		if(leftInter.getNumHits()==0 || rightInter.getNumHits()==0){
			return combInter; 
		}
		
		boolean leftInside = false; 
		boolean rightInside = false; 
		boolean combInside = false;  
		Material material = leftInter.getBestHitMaterial(); 
		
		while(leftInter.getNumHits()>0 && rightInter.getNumHits()>0){
			boolean combInsideNew = false; 
			if(leftInter.getBestHitTime()<rightInter.getBestHitTime()){
				leftInside = !leftInside; 
				combInsideNew = this.getCombInter(leftInside, rightInside); 
				
				if(combInsideNew != combInside){
					combInside = combInsideNew; 
					HitInfo oldHitInfo = leftInter.getHit(0);
					HitInfo newHitInfo = new HitInfo(oldHitInfo.t, material, oldHitInfo.hitPoint, oldHitInfo.hitNormal, combInside); 
					combInter.add(newHitInfo); 
				}
				leftInter.hits.remove(0); 
			}
			else{
				rightInside = !rightInside;
				combInsideNew = this.getCombInter(leftInside, rightInside); 
				if(combInsideNew != combInside){
					combInside = combInsideNew; 
					HitInfo oldHitInfo = rightInter.getHit(0);
					HitInfo newHitInfo = new HitInfo(oldHitInfo.t, material, oldHitInfo.hitPoint, oldHitInfo.hitNormal, combInside);
					combInter.add(newHitInfo); 
				}
				rightInter.hits.remove(0); 
			}
		}
		
		//Changeset B: Geen processing meer want niet in intersection
		return combInter;
	}
	
	public boolean getCombInter(boolean leftInside, boolean rightInside){
		return leftInside && rightInside; 
	}

	@Override
	public boolean hit(Ray ray) {
		/*Intersection leftInter = this.left.intersection(ray); 
		Intersection rightInter = this.right.intersection(ray); 
		
		//ChangeSet C 
		if(leftInter.getNumHits()==0 || rightInter.getNumHits()==0){
			return false; 
		}
		
		boolean leftInside = false; 
		boolean rightInside = false; 
		boolean combInside = false;  
		
		while(leftInter.getNumHits()>0 && rightInter.getNumHits()>0){
			boolean combInsideNew = this.getCombInter(leftInside, rightInside);
			if(leftInter.getBestHitTime()<rightInter.getBestHitTime()){
				leftInside = !leftInside; 
				if(combInsideNew != combInside){
					return true; 
				}
				leftInter.hits.remove(0); 
			}
			else{
				rightInside = !rightInside;
				if(combInsideNew != combInside){
					return true; 
				}
				rightInter.hits.remove(0); 
			}
		}
		
		//Changeset B: process remaining HitInfo Objects of the left shape
		if(leftInter.getNumHits()>0){
			return true; 
		}
		
		return false; */
        Intersection leftInter=this.left.intersection(ray);
        Intersection rightInter=this.right.intersection(ray);
        if(leftInter.hits.isEmpty() || rightInter.hits.isEmpty()){
                return false;          
        }
        boolean leftInside=false,rightInside=false;
        int currentLeft=0,currentRight=0;
        int maxLeft=leftInter.getNumHits(),maxRight=rightInter.getNumHits();
        
        while(currentLeft<maxLeft && currentRight<maxRight){
                HitInfo hitLeft = leftInter.getHit(currentLeft);
                HitInfo hitRight = rightInter.getHit(currentRight);
                
                if(hitLeft.t <= hitRight.t){
                    leftInside = !leftInside;
                    if(leftInside){
                            return true;
                    }
                    currentLeft++;
                }
                else{
                    rightInside = !rightInside;
                    if(rightInside){
                            return true;
                    }
                    currentRight++;
                }
        }
        return false;
	}
}