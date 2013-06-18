package test;

import static org.junit.Assert.*;
import geomobj.BoxExtent;

import org.junit.Before;
import org.junit.Test;

import renderer.Ray; 
import util.*; 

public class BoxExtentTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test_hit_geeft_correct_resultaat() {	
		BoxExtent box = new BoxExtent(-1,1,1,-1,1,-1); //left, top, right, bottom, front, back;
		
		Ray ray = new Ray(new Point(0,0,5), new Vector(0,0,-1)); 
		assertTrue(box.hit(ray)); 
		
		ray = new Ray(new Point(0,0,-5), new Vector(0,0,1)); 
		assertTrue(box.hit(ray));
		
		ray = new Ray(new Point(0,5,0), new Vector(0,-1,0)); 
		assertTrue(box.hit(ray));
		
		ray = new Ray(new Point(0,-5,0), new Vector(0,1,0)); 
		assertTrue(box.hit(ray));
		
		ray = new Ray(new Point(5,0,0), new Vector(-1,0,0)); 
		assertTrue(box.hit(ray));
		
		ray = new Ray(new Point(-5,0,0), new Vector(1,0,0)); 
		assertTrue(box.hit(ray));
	}

}
