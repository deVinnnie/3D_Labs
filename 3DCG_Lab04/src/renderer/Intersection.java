package renderer;

import geomobj.Shape;

import java.util.ArrayList;

import util.Point;
import util.Vector;

public class Intersection {
	public ArrayList<HitInfo> hits;
		
	public Intersection() {
		hits = new ArrayList<HitInfo>();
	}

	public Intersection(Intersection inter){
		set(inter);
	}
	
	public void set(Intersection inter){
		hits = inter.hits;
	}
	
	public void add(HitInfo hitInfo){
		hits.add(hitInfo);
	}
	
	public int getNumHits(){
		return hits.size();
	}
	
	public float getBestHitTime(){
		return hits.get(0).t;
	}
	
	public Shape getBestHitShape(){
		return hits.get(0).hitShape;
	}
	
	public Point getBestHitPoint(){
		return hits.get(0).hitPoint;
	}
	
	public Vector getBestHitNormal(){
		return hits.get(0).hitNormal;
	}
	
	public HitInfo getHit(int i){
		return hits.get(i);
	}
}
