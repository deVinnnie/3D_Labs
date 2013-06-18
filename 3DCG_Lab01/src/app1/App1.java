package app1;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import util.Renderer;

public class App1 extends Renderer {
	
	public App1(String title){
		super(title);
	}
	
	public void init(GLAutoDrawable drawable) {		
		GL2 gl = drawable.getGL().getGL2();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f); 
	}

	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glBegin(GL2.GL_TRIANGLES);
        	gl.glColor3f(1, 0, 0);
	        gl.glVertex3i(-10, -8, 10);
	        gl.glColor3f(0, 1, 0);
	        gl.glVertex3i( 10, -8, 10);	
	        gl.glColor3f(0, 0, 1);
	        gl.glVertex3i( 0, 10, 10);
        gl.glEnd();
		gl.glFlush();
	}

	public static void main(String[] args) {
		App1 app = new App1("Hello world!");
	}
	
}


