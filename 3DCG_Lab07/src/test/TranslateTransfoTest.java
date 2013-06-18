package test;

import static org.junit.Assert.*;

import org.junit.Test;

import transfo.TranslateTransfo;

public class TranslateTransfoTest {

	@Test
	public void test_TranslateTransfo_juist_geinitialiseerd(){
		TranslateTransfo transfo = new TranslateTransfo(1,1,1);
		for(int i=0; i<=3;i++){
			assertEquals(1,transfo.mat.m[i][3],0.1); 
		}
		for(int i=0;i<=3;i++){
			assertEquals(1,transfo.mat.m[i][i],0);
		}
	}

}
