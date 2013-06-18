package scene;

import geomobj.*;
import geomobj.bool.*; 
import geomobj.mesh.Mesh;
import java.io.*; 
import java.util.*;

import transfo.*;
import util.*;
import light.Light;
import material.Material;


public class SceneFactory {
	public Scene createScene(Properties prop){
		Scene scene = new Scene();
		ArrayList<GeomObj> objects = new ArrayList<GeomObj>();
		ArrayList<Light> lights = new ArrayList<Light>(); 
		TransfoStack stack = new TransfoStack();
		
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
				else if(token.equals(Token.LIGHT.toString())){
					Point p = new Point(scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat()); 
					Colour c = new Colour(scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat()); 
					lights.add(new Light(p,c));
				}
				else if (!processMaterial(token,scanner,currMtrl)){
					if (!processTransfo(token,scanner, stack)){
						objects.add(createGeomObject(token, scanner, currMtrl, stack));
					}	
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		scene.setObjects(objects);	
		scene.setLights(lights); 
		return scene;
	}
		
	private GeomObj createGeomObject(String token, Scanner scanner,
			Material currMtrl, TransfoStack stack) {
		
		if (token.equals(Token.DIFFERENCE.toString())){
			GeomObj left = getObject(scanner, currMtrl, stack);
			GeomObj right = getObject(scanner, currMtrl, stack);
			//return the appropriate boolean object
			return new DifferenceBoolean(left, right); 
		}
		else if(token.equals(Token.INTERSECTION.toString())){
			GeomObj left = getObject(scanner,currMtrl, stack); 
			GeomObj right = getObject(scanner, currMtrl, stack); 
			//return the appropertiate boolean object
			return new IntersectionBoolean(left, right); 
		}
		else {
			// token is a shape token
			//call a method to create a shape and return the result
			return createShape(token, scanner, currMtrl, stack); 
		}
	}
	
	private GeomObj getObject(Scanner scanner, Material currMtrl, TransfoStack stack){
		while(scanner.hasNext()){
			//the sdl file still contains unprocessed tokens
			String token = scanner.next().toUpperCase();
			if (!processMaterial(token,scanner,currMtrl)){
				if (!processTransfo(token,scanner, stack)){//token is not processed as a transformation token
					return this.createGeomObject(token, scanner, currMtrl, stack); 
					//call an appropriate ( existing ) method and return its result .
				}
			}
		}
		throw new IllegalStateException(); 

	}

	private Shape createShape(String token, Scanner scanner, Material currMtrl, TransfoStack stack){
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
		shape.setTransfo(new Transfo(stack.peek()));
		return shape;
	}
	
	private boolean processMaterial(String token, Scanner scanner, Material currMtrl){
		boolean isMaterial = false; 
		if(token.equals(Token.DIFFUSE.toString()) || token.equals(Token.AMBIENT.toString())){
			isMaterial = true; 
			
			float r = scanner.nextFloat();
			float g = scanner.nextFloat();
			float b = scanner.nextFloat();
			
			if(token.equals(Token.DIFFUSE.toString())){
				currMtrl.setDiffuse(new Colour(r,g,b));
			}
			else if(token.equals(Token.AMBIENT.toString())){
				currMtrl.setAmbient(new Colour(r,g,b)); 
			}
		}
		else if(token.equals(Token.REFLECTIVITY.toString())){
			isMaterial = true; 
			float reflectivity = scanner.nextFloat(); 
			currMtrl.setReflectivity(reflectivity); 
		}
		
		return isMaterial; 
	}	
	
	public boolean processTransfo(String token, Scanner scanner, TransfoStack stack){
		boolean isTransfo = true; 
		Transfo transfo = null; 
		if(token.equals(Token.SCALE.toString())){
			float sx = scanner.nextFloat(); 
			float sy = scanner.nextFloat(); 
			float sz = scanner.nextFloat(); 
			transfo = new ScaleTransfo(sx,sy,sz);
		}
		else if(token.equals(Token.TRANSLATE.toString())){
			float tx = scanner.nextFloat(); 
			float ty = scanner.nextFloat(); 
			float tz = scanner.nextFloat(); 
			
			transfo = new TranslateTransfo(tx,ty,tz); 
		}
		else if(token.equals(Token.ROTATE.toString())){
			float angle = scanner.nextFloat(); 
			float x = scanner.nextFloat(); 
			float y = scanner.nextFloat(); 
			float z = scanner.nextFloat(); 
			
			transfo = new RotateTransfo(angle,x,y,z); 
		}
		else if(token.equals(Token.PUSH.toString())){
			stack.push();
		}
		else if(token.equals(Token.POP.toString())){
			stack.pop();
		}
		else{
			isTransfo = false; 
		}
		
		if(isTransfo == true && transfo != null){
			stack.transform(transfo);
		}
		return isTransfo;
	}
}
