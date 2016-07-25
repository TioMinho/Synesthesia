package mt.synesthesia.main.system;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

	// *****************
	// ****ATRIBUTOS****
	// *****************
	
	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;
	
	// *****************
	// *****M�TODOS*****
	// *****************
	
	// Construtor Padr�o
	public Mouse(){}
	
	/*GETTERS*/
	
	public int getX(){
		return mouseX;
	}
	
	public int getY(){
		return mouseY;
	}
	
	public int getButton(){
		return mouseB;
	}
	
	
	// M�todo para verificar se o mouse foi arrastado
	public void mouseDragged(MouseEvent e) {}

	// M�todo para verificar se o mouse foi movimentado 
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	// M�todo para verificar se o mouse foi clickado
	public void mouseClicked(MouseEvent e) {
//		mouseB = e.getButton();
	}

	// M�todo para verificar se o mouse entrou dentro da janela do game
	public void mouseEntered(MouseEvent e) {}

	// M�todo para verificar se o mouse saiu da janela do game
	public void mouseExited(MouseEvent e) {}
	
	// M�todo para verificar se um bot�o do mouse foi pressionado
	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();
	}

	// M�todo para verificar se um bot�o do mouse foi solto
	public void mouseReleased(MouseEvent e) {
		mouseB = -1;
	}

}
