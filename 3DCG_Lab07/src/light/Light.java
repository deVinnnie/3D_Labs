package light;

import util.Colour;
import util.Point;

public class Light {
	public Point pos; 
	public Colour colour; 
	
	public Light(Point pos, Colour colour){
		this.pos = pos; 
		this.colour = colour; 
	}
}
