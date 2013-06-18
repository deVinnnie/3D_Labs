package renderer;

import java.util.Properties;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import util.Point;
import util.Quaternion;
import util.Vector;

public class Camera {
	
	public Point eye;
	public Point look;
	public Vector up;
	
	public Vector u,v,n;
	
	public float distance, width, height; 
	
	public Camera(Properties prop){
		this(new Point(Float.parseFloat(prop.getProperty("camera.eye.x")),
					     Float.parseFloat(prop.getProperty("camera.eye.y")),
					     Float.parseFloat(prop.getProperty("camera.eye.z"))),
		new Point(Float.parseFloat(prop.getProperty("camera.look.x")),
						  Float.parseFloat(prop.getProperty("camera.look.y")),
						  Float.parseFloat(prop.getProperty("camera.look.z"))),
		new Vector(Float.parseFloat(prop.getProperty("camera.up.x")),
						 Float.parseFloat(prop.getProperty("camera.up.y")),
						 Float.parseFloat(prop.getProperty("camera.up.z"))),		
		Float.parseFloat(prop.getProperty("camera.worldwindow.distance")),
		Float.parseFloat(prop.getProperty("camera.worldwindow.width")),
		Float.parseFloat(prop.getProperty("camera.worldwindow.height"))
		);
		
	}
	
	public Camera(Point eye, Point look, Vector up, float distance, float width, float height){
		this.eye = eye; 
		this.look = look; 
		this.up = up; 
		
		//Initialiseer u,v en n
		n = new Vector(look, eye);
		n.normalize(); 
				
		u = up.getCross(n); 
		u.normalize(); 
				
		v = n.getCross(u); 
		
		//World window
		this.distance = distance; 
		this.width = width; 
		this.height = height; 
	}
	
	public void setPositionAndOrientationOpenGL(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	    gl.glLoadIdentity();    
	    GLU glu = new GLU();
    	glu.gluLookAt(eye.x, eye.y, eye.z, look.x, look.y, look.z, v.x, v.y, v.z);
	}
	
	public void forward(){
		this.eye.add(n.getReverse());  
	}
	
	public void backward(){
		this.eye.add(n); 
	}
	
	public void up(){
		Quaternion p = new Quaternion(0,eye.x, eye.y, eye.z); 
		Quaternion q = new Quaternion(-5, this.u); 
		Quaternion p2 = q.mult(p).mult(q.conjugate()); 
		this.eye = new Point(p2.b, p2.c, p2.d);
		
		this.n = new Vector(this.look, this.eye); 
		this.n.normalize(); 
		this.v = n.getCross(u); 
	}
	
	public void down(){
		Quaternion p = new Quaternion(0,eye.x, eye.y, eye.z); 
		Quaternion q = new Quaternion(5, this.u); 
		Quaternion p2 = q.mult(p).mult(q.conjugate()); 
		this.eye = new Point(p2.b, p2.c, p2.d);
		
		this.n = new Vector(this.look, this.eye); 
		this.n.normalize(); 
		this.v = n.getCross(u);	
	}
	
	public void left(){
		Quaternion p = new Quaternion(0,eye.x, eye.y, eye.z); 
		Quaternion q = new Quaternion(-10, this.v); 
		Quaternion p2 = q.mult(p).mult(q.conjugate()); 
		this.eye = new Point(p2.b, p2.c, p2.d);
		
		this.n = new Vector(this.look, this.eye); 
		this.n.normalize(); 
		
		u = v.getCross(n); 
		u.normalize(); 
	}
	
	public void right(){
		Quaternion p = new Quaternion(0,eye.x, eye.y, eye.z); 
		Quaternion q = new Quaternion(10, this.v); 
		Quaternion p2 = q.mult(p).mult(q.conjugate()); 
		this.eye = new Point(p2.b, p2.c, p2.d);
		
		this.n = new Vector(this.look, this.eye); 
		this.n.normalize(); 
		
		u = v.getCross(n); 
		u.normalize(); 
	}
}

