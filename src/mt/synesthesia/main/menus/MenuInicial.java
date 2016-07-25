package mt.synesthesia.main.menus;

import mt.synesthesia.main.audio.Sound;
import mt.synesthesia.main.graphics.Image;
import mt.synesthesia.main.graphics.Screen;
import mt.synesthesia.main.system.Mouse;

public class MenuInicial extends Menu{

	// *****************
	// ****ATRIBUTOS****
	// *****************
	
	private Image selectionBox;
	private int boxActive;
	private boolean isReset;
	
	/* PONTEIROS */
	private MenuSkin menuSkinPointer;
	
	// *****************
	// *****M�TODOS*****		
	// *****************
	
	// Construtor padr�o
	public MenuInicial() {
		
		// Inicializa��o do background
		background = new Image("/Menus/menuPrincipal.png", 900, 506);
		
		// Inicializa��o das caixas seletoras
		selectionBox = new Image("/Menus/selectionBox1.png", 470, 210);
		
		// Inicializa a BGM (background music)
		bgMusic = new Sound("/Music/Intermission.mp3");
		
		// Inicializa o ponteiro do menu de skins
		this.menuSkinPointer = null;
		
		// Inicializa o boolean de resetar o game
		isReset = false;
		
		// Inicia a primeira caixa de sele��o como a caixa ativa
		boxActive = 1;
		
		// Inicializa o contador do delay de clicagem do mouse
		timeClicked = System.currentTimeMillis();
		
		// Abre o menu
		openMenu();
	}
	
	// Getter do boolean para resetar o game
	public boolean getReset(){
		return isReset;
	}
	
	// Setter para o boolean para resetar o game
	public void setReset(boolean isReset){
		this.isReset = isReset;
	}
	
	// M�todo de re-abrir o menu (uma vez fechado)
	public void reOpen() {
		
		// Inicializa��o das caixas seletoras
		selectionBox = new Image("/Menus/selectionBox1.png", 470, 210);
		
		// Inicia a primeira caixa de sele��o como a caixa ativa
		boxActive = 1;

		// Inicializa o boolean de resetar o game
		isReset = false;
		
		// Inicializa o contador do delay de clicagem do mouse
		timeClicked = System.currentTimeMillis();
		
		// Abre o menu
		openMenu();
		
	}
	
	// Setter para o ponteiro do Menu Skin
	public void setMenuSkinPointer(MenuSkin menuSkinPointer) {
		this.menuSkinPointer = menuSkinPointer;
	}

	@Override
	//Sobrescrita do m�todo de checar a sele��o pressionada
	protected int checkSelection(Mouse gameMouse) {
		
		if(gameMouse.getButton() == 1)	// Quando pressionado o primeiro bot�o do mouse
		{
			timeClicked = System.currentTimeMillis();
			
			if(boxActive == 1){ // Primeira caixa seletora
				if(gameMouse.getX() >= 200 && gameMouse.getX() < 670){ // Delimita a caixa seletora
					if(gameMouse.getY() >= 225 && gameMouse.getY() < 295)	// Primeira op��o
						return 1;
					else if(gameMouse.getY() >= 295 && gameMouse.getY() < 365)	// Segunda op��o
						return 2;
					else if(gameMouse.getY() >= 365 && gameMouse.getY() < 435)	// Terceira Op��o
						return 3;
					else	// Nenhuma op��o
						return 0;
					}
				else // Fora da caixa de sele��o
					return 0;
			}
			else	// Segunda caixa de sele��o
				if(gameMouse.getX() >= 200 && gameMouse.getX() < 670){ // Delimita a caixa seletora
					if(gameMouse.getY() >= 225 && gameMouse.getY() < 275)	// Primeira op��o
						return 4;
					else if(gameMouse.getY() >= 275 && gameMouse.getY() < 325)	// Segunda op��o
						return 5;
					else if(gameMouse.getY() >= 325 && gameMouse.getY() < 375)	// Terceira Op��o
						return 6;
					else if(gameMouse.getY() >= 375 && gameMouse.getY() < 425)	// Quarta Op��o
						return 7;
					else	// Nenhuma op��o
						return 0;
					}
				else // Fora da caixa de sele��o
					return 0;
				
		} // Mouse n�o pressionado
		else return 0;
		
	}

	@Override
	// Menu para manipular as op��es do menu
	public void menuLoop(Mouse gameMouse, long currentTime) {
		
		// Menu Loop
		if(isMenuOpen() && (currentTime - timeClicked) >= 500)
		{
			switch(checkSelection(gameMouse)){
			case 1: // New Game
				selectionBox = new Image("/Menus/selectionBox2.png", 470, 210);
				boxActive = 2;
				break;
			case 2:	// Menu de Skins
				closeMenu();
				menuSkinPointer.openMenu();
				break;
			case 3: // Exit
				optionSelected = -1;
				closeMenu();
				break;
			case 4:	// INICIA O MODO CLASSICO
				optionSelected = 1;
				closeMenu();
				break;
			case 5:	// INICIA O MODO ESPELHADO
				optionSelected = 2;
				closeMenu();
				break;
			case 6:	// INICIA O MODO FANTASMA
				optionSelected = 3;
				closeMenu();
				break;
			case 7:	// INICIA O TOTAL MADNESS
				optionSelected = 4;
				closeMenu();
				break;
			}
		}
		
	}
	
	@Override
	// M�todo para renderizar o menu
	public void draw(Screen gameScreen){
		gameScreen.renderImage(0, 0, background);
		gameScreen.renderImage(200, 225, selectionBox);
	}
}
