package apps;

import geomobj.Sphere;
import geomobj.mesh.Mesh;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;


import com.sun.opengl.util.FPSAnimator;

import renderer.Camera;
import ui.UserEventMediator;


public class App extends Frame implements GLEventListener {
	
	private Camera camera;	
	private Viewport viewport;
	private Properties prop;


	public App(String appConfigurationFile) {
		
		prop = AppPropertiesLoader.load(appConfigurationFile);
		
		setTitle(prop.getProperty("app.title"));	
		int canvasWidth = Integer.parseInt(prop.getProperty("canvas.width"));
		int canvasHeight = Integer.parseInt(prop.getProperty("canvas.height"));	
		setSize(new Dimension(canvasWidth, canvasHeight));
		
		camera = new Camera(prop);  

		GLProfile glp = GLProfile.getDefault();
		GLCapabilities glCapabilities = new GLCapabilities(glp);
		GLCanvas canvas = new GLCanvas(glCapabilities);
		canvas.addGLEventListener(this); 
		
		canvas.addKeyListener(new UserEventMediator(camera)); 
	        
		add(canvas, java.awt.BorderLayout.CENTER);
		
		FPSAnimator animator = new FPSAnimator(canvas, 60);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		setVisible(true);
		animator.start();
		canvas.requestFocus();
		viewport = new Viewport();
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		viewport.reshape(drawable, x, y, width, height);
	}
	
	public void init(GLAutoDrawable drawable) {		            
		GL2 gl = drawable.getGL().getGL2();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0f);  
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glCullFace(GL.GL_BACK);

	    gl.glEnable(GL2.GL_LIGHTING);  
	    gl.glEnable(GL2.GL_LIGHT0);
	       
		GLU glu = new GLU();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45, 1.3333333f, 1, 100);	
	}

	public void display(GLAutoDrawable drawable) {	
		GL2 gl = drawable.getGL().getGL2();
		camera.setPositionAndOrientationOpenGL(drawable);
		
		float[] lightPos = {-2, 0 , 0, 1};
	    float[] lightColorDiffuse = {0.8f, 0.8f, 0.8f, 1f};
        float[] lightColorAmbient = {0.1f, 0.1f, 0.1f, 1f};
	      
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, lightColorDiffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, lightColorAmbient, 0);
        
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
		
		float[] ambientColor = {1f, 1f, 0f};
		float[] diffuseColor = {1f, 1f, 0f};
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, diffuseColor, 0);  
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, ambientColor, 0);
        Sphere sphere = new Sphere(100,50);
        gl.glPushMatrix();
        gl.glTranslatef(-2,0,0);
        gl.glScalef(0.05f, 0.05f, 0.05f);
        sphere.draw(drawable);
        gl.glPopMatrix();
        
		float[] diffuseColor2 = {0.3f, 0.5f, 1f};
		float[] ambientColor2 = {0.3f, 0.5f, 1f};

        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, diffuseColor2, 0);   
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, ambientColor2, 0); 
		
        String fileName = prop.getProperty("scene.file");
        Mesh mesh = new Mesh(fileName);
		mesh.draw(drawable);
		
		gl.glFlush();
	}

	public void dispose(GLAutoDrawable drawable) {
	}
	
	public void displayChanged(GLAutoDrawable drawable, boolean arg1, boolean arg2) {	
	}
	
	
	private class Viewport {
		
		private float aspectRatioWorldWindow = 1.333333333f;
		
		public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height){
			GL2 gl = drawable.getGL().getGL2();	
			float aspectRatioScreenWindow = (float)width/height;
			if(aspectRatioScreenWindow>aspectRatioWorldWindow){
				gl.glViewport( (int)((width-height*aspectRatioWorldWindow)/2.), 0, (int)(height*aspectRatioWorldWindow), height);
			} else {
				gl.glViewport( 0, (int)((height-width/aspectRatioWorldWindow)/2.), width, (int)(width/aspectRatioWorldWindow));
			}
		}

	}
	
}
