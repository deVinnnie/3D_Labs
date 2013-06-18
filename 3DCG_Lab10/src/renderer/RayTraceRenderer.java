package renderer;

import java.util.Properties;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import renderer.raytracer.RayTracer;
import renderer.raytracer.RayTracerFactory;
import scene.Scene;
import util.Colour;
import util.Vector;

public class RayTraceRenderer extends Renderer {
	
	private RayTracer rayTracer;	
	private int nRows, nCols;	

	public RayTraceRenderer(Scene scene, Camera camera, Properties prop) {
		super(scene, camera);	
		nRows = Integer.parseInt(prop.getProperty("canvas.height"));
	    nCols = Integer.parseInt(prop.getProperty("canvas.width"));	  
	    rayTracer = RayTracerFactory.createRayTracer(scene,prop);    
	}

	@Override
	public void init(GLAutoDrawable drawable) {
	}
	
	
	
	@Override
	public void render(GLAutoDrawable drawable) {
				
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);		
		GLU glu = new GLU();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluOrtho2D(0, nCols, 0, nRows);		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	    gl.glLoadIdentity();   	   
		gl.glDisable(GL2.GL_LIGHTING);
		
		// to do: implement
		for(int r = 0; r<this.nRows; r++){
			for(int c = 0; c<this.nCols; c++){
				//v1
				Vector v11 = camera.u.getMultiple(-1 * (camera.width / 2)); 
				Vector v12 = camera.v.getMultiple(camera.height/2); 
				Vector v1 = v11.getSom(v12); 
				
				//v2
				Vector v21 = camera.u.getMultiple(c*camera.width/nCols); 
				Vector v22 = camera.v.getMultiple(r*camera.height/nRows); 
				Vector v2 = v21.getSom(v22.getReverse()); 
				
				//v3
				Vector v3 = camera.n.getMultiple(-camera.distance);
				v3 = v3.getSom(v1); 
				Vector dir = v3.getSom(v2); 
				
				Ray ray = new Ray(camera.eye, dir); 
				
				Colour col = this.rayTracer.shade(ray); 
				gl.glColor3f(col.r, col.g, col.b);
				gl.glRecti(c, nRows-r, c+1, nRows-r-1);
			}
		}
		gl.glFlush();
	}
}