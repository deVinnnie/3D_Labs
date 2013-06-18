package renderer;

import javax.media.opengl.GLAutoDrawable;

import scene.Scene;

public abstract class Renderer {

	protected Scene scene;
	protected Camera camera;
	
	public Renderer(Scene scene, Camera camera){
		this.scene = scene;
		this.camera = camera;
	}
	
	public abstract void init(GLAutoDrawable drawable);
	
	public abstract void render(GLAutoDrawable drawable);
	
}
