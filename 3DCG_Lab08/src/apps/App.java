package apps;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import com.sun.opengl.util.FPSAnimator;

import renderer.RayTraceRenderer;
import renderer.Renderer;
import scene.Scene;
import scene.SceneFactory;
import ui.UserEventMediator;
import renderer.Camera;


public class App extends Frame implements GLEventListener {
	
	private Camera camera;
	private Scene scene;
	private Renderer renderer;
	private Viewport viewport;


	public App(String appConfigurationFile) {
		
		Properties prop = AppPropertiesLoader.load(appConfigurationFile);
		
		setTitle(prop.getProperty("app.title"));	
		int canvasWidth = Integer.parseInt(prop.getProperty("canvas.width"));
		int canvasHeight = Integer.parseInt(prop.getProperty("canvas.height"));	
		setSize(new Dimension(canvasWidth, canvasHeight));
		
		camera = new Camera(prop); 
		SceneFactory sceneFactory = new SceneFactory();
		scene = sceneFactory.createScene(prop);  
	    renderer = new RayTraceRenderer(scene, camera, prop); 

		GLProfile glp = GLProfile.getDefault();
		GLCapabilities glCapabilities = new GLCapabilities(glp);
		GLCanvas canvas = new GLCanvas(glCapabilities);
		canvas.addGLEventListener(this); 
		
		UserEventMediator listener = new UserEventMediator(camera);
		canvas.addKeyListener(listener);
	        
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
		viewport = new Viewport(prop);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		viewport.reshape(drawable, x, y, width, height);
	}
	
	public void init(GLAutoDrawable drawable) {		            
        renderer.init(drawable);
	}

	public void display(GLAutoDrawable drawable) {	
		renderer.render(drawable);
	}

	public void dispose(GLAutoDrawable drawable) {
	}
	
	public void displayChanged(GLAutoDrawable drawable, boolean arg1, boolean arg2) {	
	}
	
	
	private class Viewport {
		
		private float aspectRatioWorldWindow;
		
		public Viewport(Properties prop){
			float width = Float.parseFloat(prop.getProperty("camera.worldwindow.width"));
			float height = Float.parseFloat(prop.getProperty("camera.worldwindow.height"));
			aspectRatioWorldWindow = width/height;
		}
		
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
