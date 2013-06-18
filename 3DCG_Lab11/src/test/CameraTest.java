package test;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.*;

import renderer.Camera;
import util.*; 
import apps.AppPropertiesLoader;

public class CameraTest {
	private Properties prop; 
	private Camera camera; 
	
	@Before
	public void setUp(){
		prop = AppPropertiesLoader.load("src/apps/app1/app1.cfg");
		camera = new Camera(prop); 
	}
	
	@Test
	public void test_Eye_wordt_correct_ingelezen() {
		Point eye = new Point(0,0,6); 
		assertEquals(eye, camera.eye);
	}
	
	@Test
	public void test_Look_wordt_correct_ingelezen(){
		Point look = new Point(0,0,0); 
		assertEquals(look, camera.look); 
	}
	
	@Test
	public void test_Up_wordt_correct_ingelezen(){
		Vector up = new Vector(0,1,0); 
		assertEquals(up, camera.up); 
	}
	
	@Test
	public void test_left_doet_wat_moet(){
		camera.left();
		
	}

}
