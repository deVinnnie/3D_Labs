package test;

import static org.junit.Assert.*;

import org.junit.Test;

import transfo.RotateTransfo;
import util.Matrix;

public class RotateTransfoTest {

	@Test
	public void test_Genereert_Juiste_Matrix() {
		RotateTransfo rotate = new RotateTransfo(90,1,0,0);
		Matrix matrix = new Matrix(); 
		matrix.m[0][0] = 1; 
		matrix.m[0][1] = 0; 
		matrix.m[0][2] = 0; 
		matrix.m[0][3] = 0;  
		
		matrix.m[1][0] = 0; 
		matrix.m[1][1] = 0;
		matrix.m[1][2] = -1;
		matrix.m[1][3] = 0;
		
		matrix.m[2][0] = 0;
		matrix.m[2][1] = 1;
		matrix.m[2][2] = 0;			
		matrix.m[2][3] = 0;
		
		matrix.m[3][0] = 0;
		matrix.m[3][1] = 0;
		matrix.m[3][2] = 0;			
		matrix.m[3][3] = 1;
		
		assertEquals(matrix, rotate.mat); 	
	}
	
	@Test
	public void test_Genereert_Juiste_Inverse_Matrix() {
		RotateTransfo rotate = new RotateTransfo(90,1,0,0);
		Matrix matrix = new Matrix(); 
		matrix.m[0][0] = 1; 
		matrix.m[0][1] = 0; 
		matrix.m[0][2] = 0; 
		matrix.m[0][3] = 0;  
		
		matrix.m[1][0] = 0; 
		matrix.m[1][1] = 0;
		matrix.m[1][2] = 1;
		matrix.m[1][3] = 0;
		
		matrix.m[2][0] = 0;
		matrix.m[2][1] = -1;
		matrix.m[2][2] = 0;			
		matrix.m[2][3] = 0;
		
		matrix.m[3][0] = 0;
		matrix.m[3][1] = 0;
		matrix.m[3][2] = 0;			
		matrix.m[3][3] = 1;
		
		assertEquals(matrix, rotate.invMat); 	
	}
}
