package test;

import static org.junit.Assert.*;
import geomobj.*;
import renderer.*;
import transfo.*;
import util.*;
import org.junit.Test;

public class IntersectionTest {

	@Test
	public void test_Intersection_berekent_correcte_waarde() {
		Sphere sphere = new Sphere();
		Ray ray = new Ray(new Point(-3,0,0), new Vector(1,0,0));
		
		Point expectedHitPoint1 = new Point(-1,0,0);
		Point expectedHitPoint2 = new Point(1,0,0);
		float expectedT = 2; 
		
		Intersection intersect = sphere.intersection(ray); 
		assertEquals(expectedHitPoint1,intersect.getBestHitPoint());
		assertEquals(expectedT, intersect.getBestHitTime(),0.1); 
	}
	
	@Test 
	public void test_intersection_berekent_correcte_waarde_bij_translate(){
		TranslateTransfo transfo = new TranslateTransfo(1,0,0);
		Sphere sphere = new Sphere();
		sphere.setTransfo(transfo);
		Ray ray = new Ray(new Point(-3,0,0), new Vector(1,0,0));
		
		Intersection intersect = sphere.intersection(ray);
		
		Point expectedHitPoint1 = new Point(0,0,0);
		Point expectedHitPoint2 = new Point(2,0,0);
		float expectedT = 3; 
	
		assertEquals(expectedHitPoint1,intersect.getBestHitPoint());
		assertEquals(expectedT, intersect.getBestHitTime(),0.1); 
	}

}
