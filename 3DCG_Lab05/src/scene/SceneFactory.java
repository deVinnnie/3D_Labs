package scene;

import geomobj.GeomObj;
import geomobj.Shape;
import geomobj.Sphere;
import geomobj.Square;
import geomobj.mesh.Mesh;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

import util.Colour;

import material.Material;


public class SceneFactory {
	
	public Scene createScene(Properties prop){
	
		Scene scene = new Scene();
		ArrayList<GeomObj> objects = new ArrayList<GeomObj>();
		Material currMtrl = new Material(); 
		
		String fileName = prop.getProperty("scene.file");		
		Scanner scanner;
		try {
			scanner = new Scanner(new File(fileName));
			scanner.useLocale(Locale.US);
			
			while(scanner.hasNext()){
				
				String token = scanner.next().toUpperCase();
				
				if(token.equals(Token.BACKGROUND.toString())){
					scene.setBackground(scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat());
				} 
				else if (!processMaterial(token,scanner,currMtrl)){
					objects.add(createShape(token, scanner, currMtrl));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		scene.setObjects(objects);		
		return scene;
	}
		
	private Shape createShape(String token, Scanner scanner, Material currMtrl){
		Shape shape = null;
		
		if(token.equals(Token.SQUARE.toString())){
			shape = new Square();
		} 
		else if(token.equals(Token.SPHERE.toString())){
			shape = new Sphere(); 
		}
		else if(token.equals(Token.MESH.toString())){
			shape = new Mesh(scanner.next()); 
		}
		else {
			throw new IllegalStateException("The token " + token + " is not supported by the scene description language!");
		}
		shape.mtrl = new Material(currMtrl); 
		return shape;
	}
	
	private boolean processMaterial(String token, Scanner scanner, Material currMtrl){
		boolean isMaterial = false; 
		if(token.equals(Token.COLOUR.toString())){
			isMaterial = true; 
			
			float r = scanner.nextFloat();
			float g = scanner.nextFloat();
			float b = scanner.nextFloat();
			
			currMtrl.setColour(new Colour(r,g,b)); 
		}
		return isMaterial; 
	}	
}
