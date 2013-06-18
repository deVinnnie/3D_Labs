package test;

import static org.junit.Assert.*;

import org.junit.Test;

import renderer.Ray;
import util.Matrix;
import util.Point;
import util.Vector;

public class RayTest {

	@Test
	public void test_Ray_getInvTransfoRay_geeft_juiste_resultaat(){
		Ray ray = new Ray(new Point(1,0,0), new Vector(1,0,0));
		Matrix matrix = new Matrix(); 
		matrix.m[0][3]=2; 
		
		Ray rayInv = ray.getInvTransfoRay(matrix);
		assertEquals(new Point(3,0,0), rayInv.start); 
		assertEquals(new Vector(1,0,0), rayInv.dir);	
	}

}
