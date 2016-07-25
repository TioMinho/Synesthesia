package mt.synesthesia.main.system;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	// *****************
	// ****ATRIBUTOS****
	// *****************
	
	private boolean[] keys = new boolean[500];
	public boolean up, down, left, right;

	// *****************
	// *****MÉTODOS*****
	// *****************
	
	public boolean getKey(int keyNumber){
		return keys[keyNumber];
	}
	
	public int getKey(){
		for(int i = KeyEvent.VK_A; i <= KeyEvent.VK_Z; i++)
			if(keys[i] == true)
				return i;
		
		return 0;
	}
	
	public boolean isKeyPressed(){
		for(int i = KeyEvent.VK_A; i <= KeyEvent.VK_Z; i++)
			if(keys[i] == true)
				return true;
		
		return false;
	}
	
	public void update(){
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
