package test;

import static org.junit.Assert.*;

import java.util.Properties;

import light.Light;

import org.junit.Before;
import org.junit.Test;

import scene.Scene;
import scene.SceneFactory;
import util.Colour;
import util.Point;
import apps.AppPropertiesLoader;

public class LightTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Properties prop = AppPropertiesLoader.load("src/apps/app4/app4.cfg");
		SceneFactory factory = new SceneFactory();
		Scene scene =  factory.createScene(prop); 
		
		assertEquals(1, scene.getLights().size());
		Light light = scene.getLights().get(0); 
		assertEquals(new Colour(1,1,1), light.colour); 
		assertEquals(new Point(-20,20,5), light.pos); 
	}
}
