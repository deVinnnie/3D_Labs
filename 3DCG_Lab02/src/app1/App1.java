package app1;

import java.io.FileNotFoundException;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import mesh.Mesh;
import util.Renderer;

public class App1 extends Renderer {
	
	public App1(String title){
		super(title);
	}
	
	public void init(GLAutoDrawable drawable) {		
		GL2 gl = drawable.getGL().getGL2();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0f);  
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glCullFace(GL.GL_BACK);
		
	    float[] lightPos = {-50, 0 , 0, 1};
	    float[] lightColorDiffuse = {0.8f, 0.8f, 0.8f, 1f};
        float[] lightColorAmbient = {0.1f, 0.1f, 0.1f, 1f};
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};
	      
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, lightColorDiffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, lightColorSpecular, 0);

	    gl.glEnable(GL2.GL_LIGHTING);  
	    gl.glEnable(GL2.GL_LIGHT0);
	}

	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
       
		float[] diffuseColor = {0.3f, 0.5f, 1f};
		float[] ambientColor = {0.3f, 0.5f, 1f};
		float[] specularColor = {0.5f, 0.5f, 0.5f};

        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, diffuseColor, 0);   
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, ambientColor, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, specularColor, 0); 
        gl.glMaterialf(GL.GL_FRONT, GL2.GL_SHININESS, 50);
		
        Mesh mesh;
		try {
			mesh = new Mesh("resources/tetrahedron.txt");
			mesh.draw(drawable);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		gl.glFlush();
	}

	public static void main(String[] args) {
		App1 app = new App1("3D modelling");
	}
}


