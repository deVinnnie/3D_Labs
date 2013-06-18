package geomobj.mesh;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import util.Point;
import util.Vector;

public class Mesh {
	
	private ArrayList<Point> verts;	    // vertex list
	private ArrayList<Vector> norms;	// normal list
	private ArrayList<Face> faces;	    // face list
	
	public Mesh(String fileName){
		try{
			readFile(fileName);
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
	}
	
	private void readFile(String fileName) throws FileNotFoundException{
		// to do
		File file = new File(fileName); 
		Scanner scanner = new Scanner(file); 
		scanner.useLocale(Locale.US); 
		
		int nVerts = scanner.nextInt(); 
		int nNorms = scanner.nextInt(); 
		int nFaces = scanner.nextInt(); 
		
		verts = new ArrayList<Point>(nVerts); 
		for(int i = 0; i<nVerts; i++){
			float x = scanner.nextFloat();
			float y = scanner.nextFloat(); 
			float z = scanner.nextFloat(); 
			Point p = new Point(x,y,z);
			verts.add(p);
		}
		
		norms = new ArrayList<Vector>(); 
		for(int i = 0; i<nNorms; i++){
			float x = scanner.nextFloat();
			float y = scanner.nextFloat(); 
			float z = scanner.nextFloat(); 
			Vector v = new Vector(x,y,z); 
			norms.add(v);
		}
		
		faces = new ArrayList<Face>(nFaces);
		for(int i = 0; i<nFaces; i++){
			int nVertsInFace = scanner.nextInt(); 
			//Face f = new Face(nVertsInFace);
			
			ArrayList<Integer> vertIndices = new ArrayList<Integer>(nVertsInFace); 
			ArrayList<Integer> normIndices = new ArrayList<Integer>(nVertsInFace);  
			
			for(int j = 0; j<nVertsInFace; j++){
				Integer n = new Integer(scanner.nextInt());
				vertIndices.add(n); 
			}
			
			for(int j = 0; j<nVertsInFace; j++){
				Integer n = new Integer(scanner.nextInt());
				normIndices.add(n); 
			}
			
			Face face = new Face(vertIndices, normIndices); 	
			faces.add(face); 
		}
	}
	
	public void draw(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();
		for(Face face: faces){
			gl.glBegin(GL2.GL_POLYGON);  
			for(int i=0; i<face.vertIndices.size();i++){
				Vector normal = this.norms.get(face.normIndices.get(i));   
				Point vertex = this.verts.get(face.vertIndices.get(i)); 
				gl.glNormal3f(normal.x, normal.y,normal.z);
				gl.glVertex3f(vertex.x, vertex.y,vertex.z);
			}
            gl.glEnd();
		}
	}
	
}
