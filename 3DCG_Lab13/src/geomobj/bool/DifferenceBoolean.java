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
		/*Intersection combInter = new Intersection();  
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
			
			if(leftInter.getBestHitTime()<=rightInter.getBestHitTime()){
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
		
		return combInter;*/
		Intersection combInter = new Intersection();
		Intersection leftInter=this.left.intersection(ray);
		if( leftInter.hits.isEmpty() )
			return combInter;
		Intersection rightInter=this.right.intersection(ray);
		if( rightInter.hits.isEmpty() )
			return leftInter;
		boolean leftInside=false,rightInside=false,combInside=false;
		int currentLeft=0,currentRight=0,maxLeft=leftInter.getNumHits(),maxRight=rightInter.getNumHits();
		while(currentLeft<maxLeft && currentRight<maxRight){
			boolean combInsideNew;
			HitInfo hitLeft = leftInter.getHit(currentLeft);
			HitInfo hitRight = rightInter.getHit(currentRight);
			if( hitLeft.t <= hitRight.t  ){
				leftInside = !leftInside;
				combInsideNew = leftInside && !rightInside;
				if( combInsideNew != combInside ){
					combInside = combInsideNew;
					hitLeft.isEntering = combInside;
					combInter.add(hitLeft);
				}
				currentLeft++;
			}else{
				rightInside = !rightInside;
				combInsideNew = leftInside && !rightInside;
				if( combInsideNew != combInside ){
					combInside = combInsideNew;
					hitRight.hitMaterial = hitLeft.hitMaterial;
					if( combInside!=rightInside )
						hitRight.hitNormal.reverse();
					hitRight.isEntering = combInside;
					combInter.add(hitRight);
				}
				currentRight++;
			}
		}
		while( currentLeft < maxLeft ){
			HitInfo hitLeft = leftInter.getHit(currentLeft);
			//hitLeft.isEntering = !combInside;
			//combInside = !combInside;
			combInter.add(hitLeft);
			currentLeft++;
		}
		return combInter;
	}
	
	public boolean getCombInter(boolean leftInside, boolean rightInside){
		return leftInside && ! rightInside;
	}

	@Override
	public boolean hit(Ray ray) {
		/*Intersection combInter = new Intersection();  
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
		
		return false;*/
		Intersection leftInter=this.left.intersection(ray);
		if( leftInter.hits.isEmpty() )
			return false;
		boolean leftInside=false,rightInside=false;
		Intersection rightInter=this.right.intersection(ray);
		int currentLeft=0,currentRight=0,maxLeft=leftInter.getNumHits(),maxRight=rightInter.getNumHits();
		if( rightInter.hits.isEmpty() ){
			if( leftInter.getBestHitTime() < 1 )
				return true;
		}
		while(currentLeft<maxLeft && currentRight<maxRight){
			HitInfo hitLeft = leftInter.getHit(currentLeft);
			HitInfo hitRight = rightInter.getHit(currentRight);
			if( hitLeft.t <= hitRight.t  ){
				leftInside = !leftInside;
				if( leftInside && !rightInside )
					return true;
				currentLeft++;
			}else{
				rightInside = !rightInside;
				if( leftInside && !rightInside )
					return true;
				currentRight++;
			}
		}
		if( currentLeft < maxLeft && leftInter.getHit(currentLeft).t < 1 ){
			return true;
		}
		return false;
	}

}
