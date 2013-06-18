package geomobj;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class Sphere {
	
	private int nrSlices;
	private int nrStacks;

	public Sphere(int nrSlices, int nrStacks) {
		this.nrSlices = nrSlices;
		this.nrStacks = nrStacks;
	}
	
    public void draw(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		float lonDelta = 2.0f * (float) Math.PI / nrSlices;
		float latDelta = (float) Math.PI / nrStacks;

		// create region around the south pole
		float theta1 = (float) (-Math.PI / 2. + latDelta);
		float cosTheta1 = (float) Math.cos(theta1);
		float sinTheta1 = (float) Math.sin(theta1);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glNormal3f(0, -1, 0);
		gl.glVertex3f(0, -1, 0);
		float phi = 0.0f;
		for (int j = 0; j <= nrSlices; j++) {
			float cosPhi = (float) Math.cos(phi);
			float sinPhi = (float) Math.sin(phi);
			gl.glNormal3f(cosTheta1 * cosPhi, sinTheta1, cosTheta1 * sinPhi);
			gl.glVertex3f(cosTheta1 * cosPhi, sinTheta1, cosTheta1 * sinPhi);
			phi += lonDelta;
		}
		gl.glEnd();

		// create inner region excluding the poles
		for (int i = 1; i <= nrStacks - 2; i++) {
			float theta2 = theta1 + latDelta;
			float cosTheta2 = (float) Math.cos(theta2);
			float sinTheta2 = (float) Math.sin(theta2);
			gl.glBegin(GL2.GL_TRIANGLE_STRIP);
			phi = 0.0f;
			for (int j = 0; j <= nrSlices; j++) {
				float cosPhi = (float) Math.cos(phi);
				float sinPhi = (float) Math.sin(phi);
				gl.glNormal3f(cosTheta1 * cosPhi, sinTheta1, cosTheta1 * sinPhi);
				gl.glVertex3f(cosTheta1 * cosPhi, sinTheta1, cosTheta1 * sinPhi);
				gl.glNormal3f(cosTheta2 * cosPhi, sinTheta2, cosTheta2 * sinPhi);
				gl.glVertex3f(cosTheta2 * cosPhi, sinTheta2, cosTheta2 * sinPhi);		
				phi += lonDelta;
			}
			gl.glEnd();
			theta1 = theta2;
			cosTheta1 = cosTheta2;
			sinTheta1 = sinTheta2;
		}

		// create region around the north pole
		theta1 = (float) (Math.PI/2. - latDelta);
		cosTheta1 = (float) Math.cos(theta1);
		sinTheta1 = (float) Math.sin(theta1);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glNormal3f(0, 1, 0);
		gl.glVertex3f(0, 1, 0);
		phi = 0.0f;
		for (int j = 0; j <= nrSlices; j++) {
			float cosPhi = (float) Math.cos(phi);
			float sinPhi = (float) Math.sin(phi);
			gl.glNormal3f(cosTheta1 * cosPhi, sinTheta1, cosTheta1 * sinPhi);
			gl.glVertex3f(cosTheta1 * cosPhi, sinTheta1, cosTheta1 * sinPhi);
			phi -= lonDelta;
		}
		gl.glEnd();
	}

}
