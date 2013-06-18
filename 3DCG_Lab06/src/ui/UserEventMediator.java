package ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import renderer.Camera;

public class UserEventMediator extends KeyAdapter{
	private Camera camera; 
	
	public UserEventMediator(Camera camera){
		this.camera = camera; 
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		if (e.getKeyChar() == 'f'){
			camera.forward();
		} else if (e.getKeyChar()== 'b'){
			camera.backward();
		} else if(e.getKeyCode() == KeyEvent.VK_UP){
			camera.up(); 
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			camera.down(); 
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			camera.left(); 
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			camera.right(); 
		}
	}
	
	
	
}
