package test;

import static org.junit.Assert.*;

import org.junit.Test;

import renderer.Ray;

import util.*;

public class MatrixTest {

	@Test
	public void test_mult_met_Vector() {
		Matrix matrix = new Matrix(); 
		matrix.m[0][3] = 2; 
		Vector vector = matrix.mult(new Vector(1,0,0)); 
		assertEquals(new Vector(1,0,0), vector);
	}
}
