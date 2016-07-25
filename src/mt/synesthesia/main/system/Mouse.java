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
	// *****MÉTODOS*****
	// *****************
	
	// Construtor Padrão
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
	
	
	// Método para verificar se o mouse foi arrastado
	public void mouseDragged(MouseEvent e) {}

	// Método para verificar se o mouse foi movimentado 
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	// Método para verificar se o mouse foi clickado
	public void mouseClicked(MouseEvent e) {
//		mouseB = e.getButton();
	}

	// Método para verificar se o mouse entrou dentro da janela do game
	public void mouseEntered(MouseEvent e) {}

	// Método para verificar se o mouse saiu da janela do game
	public void mouseExited(MouseEvent e) {}
	
	// Método para verificar se um botão do mouse foi pressionado
	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();
	}

	// Método para verificar se um botão do mouse foi solto
	public void mouseReleased(MouseEvent e) {
		mouseB = -1;
	}

}
