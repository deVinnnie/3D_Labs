package renderer;

import java.util.*;

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
		
		//V1 = vector from center of image to top-left corner. 
		Vector v11 = camera.u.getMultiple(-1 * (camera.width / 2)); 
		Vector v12 = camera.v.getMultiple(camera.height/2); 
		Vector v1 = v11.getSom(v12); 
				
		for(int r = 0; r<this.nRows; r++){
			for(int c = 0; c<this.nCols; c++){
				List<Colour> samples = new ArrayList<Colour>(); 
					
				//0 => 1 sample
				//1 = 2 samples along each sides
				//2 => 3 samples along each side
				int nSamples = this.rayTracer.supersampling+1; 
				float widthPixel = camera.width/nCols; 
				float delta = widthPixel / nSamples; 
				
				//Iterate over columns of pixel
				for(int i = 0; i<nSamples; i++)
				{
					//Iterate over rows of pixel
					for(int j = 0; j <nSamples; j++){
						//Calculate v2 = the vector from the top-left corner to the (top-left-corner of) pixel
						float x = c*camera.width/nCols+i*delta;
						float y = r*camera.height/nRows+j*delta;
						
						Vector v21 = camera.u.getMultiple(x); 
						Vector v22 = camera.v.getMultiple(y); 
						Vector v2 = v21.getSom(v22.getReverse()); 
						
						//Calculate v3 = the vector from the camera to the top-left corner of the image 
						Vector v3 = camera.n.getMultiple(-camera.distance);
						v3 = v3.getSom(v1); 
						
						//Calculate dir = the vector from the camera to the pixel
						Vector dir = v3.getSom(v2); 
						
						//Construct new Ray and determine colour of sample
						Ray ray = new Ray(camera.eye, dir); 
						Colour col = this.rayTracer.shade(ray); 
						
						//Add sample to the samples list
						samples.add(col);
					}
				}
				
				//Take average of all samples;
				Colour colAvg = new Colour(); 
				
				for(Colour sample : samples){
					colAvg.r += (sample.r / (nSamples*nSamples)); 
					colAvg.g += (sample.g / (nSamples*nSamples)); 
					colAvg.b += (sample.b / (nSamples*nSamples)); 
				}
				
				gl.glColor3f(colAvg.r, colAvg.g, colAvg.b);
				gl.glRecti(c, nRows-r, c+1, nRows-r-1);
			}
		}
		gl.glFlush();
	}
}