package test;

import static org.junit.Assert.*;
import geomobj.mesh.Face;
import geomobj.mesh.Mesh;

import org.junit.Before;
import org.junit.Test;

import renderer.Intersection;
import renderer.Ray;
import util.*; 

public class MeshTest {

	private Mesh mesh;
	
	@Before
	public void setUp() throws Exception {
		this.mesh = new Mesh("resources/cube.txt"); 
	}

	@Test
	public void test_Intersection_geeft_juiste_intersection_point1() {
		Ray ray = new Ray(new Point(4.0f,0.5f,0.5f), new Vector(-1,0,0)); 
		
		Intersection intersection = mesh.intersection(ray); 
		Point result  = intersection.getBestHitPoint();
		Point expectedHitPoint = new Point(1.0f,0.5f,0.5f); 
		 
		assertEquals(expectedHitPoint, result);
	}
	
	@Test
	public void test_Intersection_geeft_juiste_hitNormal() {
		Ray ray = new Ray(new Point(4.0f,0.5f,0.5f), new Vector(-1,0,0)); 
		
		Intersection intersection = mesh.intersection(ray); 
		Vector result  = intersection.getBestHitNormal(); 
		Vector expectedHitNormal = new Vector(1f,0f,0f); 
		assertEquals(expectedHitNormal, result);
	}
	
	@Test
	public void test_Intersection_geeft_juiste_intersection_point2() {
		Ray ray = new Ray(new Point(0.5f,5.0f,0.5f), new Vector(0,-1,0)); 
		
		Intersection intersection = mesh.intersection(ray); 
		Point result  = intersection.getBestHitPoint();
		Point expectedHitPoint = new Point(0.5f,1.0f,0.5f); 
		assertEquals(expectedHitPoint, result);
	}
	
	@Test
	public void test_PointInFace_geeft_true_waneer_hitPoint_in_face(){
		Point point = new Point(0.5f,1.0f,0.5f);
		Vector m = new Vector(0,1,0); 
		Face face = mesh.getFaces().get(2); //Top
		boolean inFace = mesh.pointInFace(point, face, m); 
		assertTrue(inFace); 
	}
}
