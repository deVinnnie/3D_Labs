package renderer;

import geomobj.Shape;
import util.Point;
import util.Vector;

public class HitInfo {
	
	public float t;
	public Shape hitShape;
	public Point hitPoint;
	public Vector hitNormal;
	public boolean isEntering;
	
	public HitInfo(float t, Shape hitShape, Point hitPoint, Vector hitNormal,
				   boolean isEntering) {
		this.t = t;
		this.hitShape = hitShape;
		this.hitPoint = hitPoint;
		this.hitNormal = hitNormal;
		this.isEntering = isEntering;
	}
}
