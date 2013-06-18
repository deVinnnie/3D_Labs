package test;

import static org.junit.Assert.*;
import geomobj.Sphere;
import geomobj.bool.*;

import transfo.*;
import util.*; 

import org.junit.Before;
import org.junit.Test;

import renderer.Intersection;
import renderer.Ray;

public class DifferenceBooleanTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test_intersection_1() {
		Ray ray = new Ray(new Point(0,0,5), new Vector(0,0,-1)); 
		Sphere sphere2 = new Sphere();
		sphere2.setTransfo(new TranslateTransfo(0,0,1)); 
		DifferenceBoolean diffBoolean = new DifferenceBoolean(new Sphere(), sphere2); 
		Intersection intersection = diffBoolean.intersection(ray); 
		assertEquals(new Point(0,0,0),intersection.getBestHitPoint()); 
	}
	
	@Test
	public void test_intersection_2() {
		Ray ray = new Ray(new Point(0,0,5), new Vector(0,0,-1)); 
		Sphere sphere2 = new Sphere();
		sphere2.setTransfo(new ScaleTransfo(0.5f,0.5f,0.5f)); 
		DifferenceBoolean diffBoolean = new DifferenceBoolean(new Sphere(), sphere2); 
		Intersection intersection = diffBoolean.intersection(ray); 
		assertEquals(new Point(0,0,1),intersection.getBestHitPoint()); 
	}
	
	@Test
	public void test_getCombInside_geeft_juiste_waarde(){
		DifferenceBoolean diffBoolean = new DifferenceBoolean(new Sphere(), new Sphere());
		assertFalse(diffBoolean.getCombInter(false, false));
		assertTrue(diffBoolean.getCombInter(true, false)); 
		assertFalse(diffBoolean.getCombInter(true, true)); 
		assertFalse(diffBoolean.getCombInter(false, true));
		assertFalse(diffBoolean.getCombInter(false, false)); 
	}
}
