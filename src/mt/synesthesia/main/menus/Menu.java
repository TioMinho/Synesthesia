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
	// *****MÉTODOS*****
	// *****************

	// Contrutor Padrão
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
	
	// Método para abrir o menu
	public void openMenu(){
		bgMusic.play();		// Executa a música de fundo
		isOpen = true;
		optionSelected = 0;
	}
	
	public void closeMenu(){
		bgMusic.stop();		// Para a música de fundo
		isOpen = false;
	}
	
	// Método para manipular as seleções do menu
	public abstract void menuLoop(Mouse gameMouse, long currentTime);
	
	// Método que checa a posição pressionada pelo mouse e retorna um valor
	// que represente a opção pressionada
	protected abstract int checkSelection(Mouse gameMouse);

	// Método para renderizar o menu
	public abstract void draw(Screen gameScreen);
}
