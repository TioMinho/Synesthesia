package mt.synesthesia.main.menus;

import mt.synesthesia.main.audio.Sound;
import mt.synesthesia.main.graphics.*;
import mt.synesthesia.main.system.Mouse;

public abstract class Menu {
	
	// *****************
	// ****ATRIBUTOS****
	// *****************
	
	protected Image background;
	protected Sound bgMusic;
	protected int optionSelected;
	protected boolean isOpen; 
	protected long timeClicked;
	
	// *****************
	// *****M�TODOS*****
	// *****************

	// Contrutor Padr�o
	Menu(){}
	
	/* GETTERS */
	
	public Image getBackground(){
		return background;
	}
	
	public int getSelection(){
		return optionSelected;
	}
	
	public boolean isMenuOpen(){
		return isOpen;
	}
	
	/* SETTERS */
	
	public void setBackground(String path, int bgWidth, int bgHeight){
		background = new Image(path, bgWidth, bgHeight);
	}
	
	// M�todo para abrir o menu
	public void openMenu(){
		bgMusic.play();		// Executa a m�sica de fundo
		isOpen = true;
		optionSelected = 0;
	}
	
	public void closeMenu(){
		bgMusic.stop();		// Para a m�sica de fundo
		isOpen = false;
	}
	
	// M�todo para manipular as sele��es do menu
	public abstract void menuLoop(Mouse gameMouse, long currentTime);
	
	// M�todo que checa a posi��o pressionada pelo mouse e retorna um valor
	// que represente a op��o pressionada
	protected abstract int checkSelection(Mouse gameMouse);

	// M�todo para renderizar o menu
	public abstract void draw(Screen gameScreen);
}
