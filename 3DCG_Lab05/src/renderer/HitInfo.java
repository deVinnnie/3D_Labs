package renderer;

import material.Material;
import geomobj.Shape;
import util.Point;
import util.Vector;

public class HitInfo {
	
	public float t;
	public Material hitMaterial; 
	public Point hitPoint;
	public Vector hitNormal;
	public boolean isEntering;
	
	public HitInfo(float t, Material hitMaterial, Point hitPoint, Vector hitNormal,
				   boolean isEntering){
		this.t = t;
		this.hitMaterial = hitMaterial;
		this.hitPoint = hitPoint;
		this.hitNormal = hitNormal;
		this.isEntering = isEntering;
	}
}
