package util;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

abstract public class Renderer extends Frame implements GLEventListener {
	
	private static final int CANVAS_WIDTH = 800;
	private static final int CANVAS_HEIGHT = 600;
	private static final int CANVAS_FPS = 60;
	
	private GLCanvas fGLCanvas = null;
	private com.sun.opengl.util.FPSAnimator fAnimator = null;

	public Renderer(String title) {
		super(title);
		super.setSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

		GLProfile glp = GLProfile.getDefault();
		GLCapabilities glCapabilities = new GLCapabilities(glp);
		fGLCanvas = new GLCanvas(glCapabilities);
		fGLCanvas.addGLEventListener(this);
		add(fGLCanvas, java.awt.BorderLayout.CENTER);
		fAnimator = new com.sun.opengl.util.FPSAnimator(fGLCanvas, CANVAS_FPS);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setVisible(true);
		fAnimator.start();
		fGLCanvas.requestFocus();
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU();
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		float aspectRatio = (float) width / (float) height;
		glu.gluPerspective(45.0f, aspectRatio, 1, 100.0);
		
		setCamera(drawable);
	}

	public void dispose(GLAutoDrawable drawable) {
	}
	
	public void displayChanged(GLAutoDrawable drawable, boolean arg1, boolean arg2) {	
	}
	
	protected void setCamera(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(0, 0, 50, 0, 0, 0, 0, 1, 0);		
	}

}
