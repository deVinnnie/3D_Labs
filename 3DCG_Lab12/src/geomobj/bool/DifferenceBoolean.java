package geomobj.bool;

import material.Material;
import geomobj.GeomObj;
import renderer.HitInfo;
import renderer.Intersection;
import renderer.Ray;

public class DifferenceBoolean extends Boolean{

	public DifferenceBoolean(GeomObj left, GeomObj right) {
		super(left, right);
	}

	@Override
	public Intersection intersection(Ray ray) {
		Intersection combInter = new Intersection();  
		Intersection leftInter = this.left.intersection(ray); 
		Intersection rightInter = this.right.intersection(ray); 
		
		//ChangeSet C 
		if(leftInter.getNumHits()==0){
			return combInter; 
		}
		
		if(rightInter.getNumHits()==0){
			return leftInter; 
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
					if(combInside != rightInside){
						newHitInfo.hitNormal.reverse(); 
					}
					combInter.add(newHitInfo); 
				}
				rightInter.hits.remove(0); 
			}
		}
		
		//Changeset B: process remaining HitInfo Objects of the left shape
		while(leftInter.getNumHits()>0){
			HitInfo oldHitInfo = leftInter.getHit(0); 
			HitInfo newHitInfo = new HitInfo(oldHitInfo.t, material, oldHitInfo.hitPoint, oldHitInfo.hitNormal, combInside); 
			combInter.add(newHitInfo); 
			leftInter.hits.remove(0);
		}
		
		return combInter;
	}
	
	public boolean getCombInter(boolean leftInside, boolean rightInside){
		return leftInside && ! rightInside;
	}

	@Override
	public boolean hit(Ray ray) {
		Intersection combInter = new Intersection();  
		Intersection leftInter = this.left.intersection(ray); 
		Intersection rightInter = this.right.intersection(ray); 
		
		//ChangeSet C 
		if(leftInter.getNumHits()==0){
			return false; 
		}
		
		if(rightInter.getNumHits()==0){
			return true; 
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
					return true; 
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
					if(combInside != rightInside){
						newHitInfo.hitNormal.reverse(); 
					}
					combInter.add(newHitInfo); 
					return true; 
				}
				rightInter.hits.remove(0); 
			}
		}
		
		//Changeset B: process remaining HitInfo Objects of the left shape
		while(leftInter.getNumHits()>0){
			HitInfo oldHitInfo = leftInter.getHit(0); 
			HitInfo newHitInfo = new HitInfo(oldHitInfo.t, material, oldHitInfo.hitPoint, oldHitInfo.hitNormal, combInside); 
			combInter.add(newHitInfo); 
			leftInter.hits.remove(0);
			return true; 
		}
		
		return false;
	}

}
