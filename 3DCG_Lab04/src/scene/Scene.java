package scene;

import geomobj.GeomObj;
import java.util.ArrayList;
import util.Colour;

public class Scene {
	
	private ArrayList<GeomObj> objects;	
	private Colour background;
	
	public Scene(){
		objects = new ArrayList<GeomObj>();
		background = new Colour(0,  0,  0);
	}

	public void setObjects(ArrayList<GeomObj> objects) {
		this.objects = objects;
	}
	
	public ArrayList<GeomObj> getObjects(){
		return objects;
	}

	public void setBackground(float r, float g, float b) {
		background.set(r, g, b);
	}
	
	public Colour getBackGround(){
		return background;
	}
}
