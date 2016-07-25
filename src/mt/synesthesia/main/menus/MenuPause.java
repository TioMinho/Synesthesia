package mt.synesthesia.main.menus;

import mt.synesthesia.main.graphics.Image;
import mt.synesthesia.main.graphics.Screen;
import mt.synesthesia.main.system.Mouse;

public class MenuPause extends Menu {

	// *****************
	// ****ATRIBUTOS****
	// *****************

	/* PONTEIROS */
	MenuInicial menuInicialPointer;

	// *****************
	// *****MÉTODOS*****
	// *****************

	// Construtor Padrão
	public MenuPause() {

		// Inicialização do Background
		background = new Image("/Menus/pauseMenu.png", 600, 300);

		// Inicializa a música de fundo como nula
		bgMusic = null;

		// Inicializa o delayer
		timeClicked = System.currentTimeMillis();
	}

	// Configura o ponteiro do menu incial
	public void setMenuInicialPointer(MenuInicial menuInicialPointer) {
		this.menuInicialPointer = menuInicialPointer;
	}

	@Override
	// Abre o menu
	public void openMenu() {
		isOpen = true;
	}

	@Override
	// Fecha o menu
	public void closeMenu() {
		isOpen = false;
	}

	@Override
	// Método responsável por administrar as seleções do menu
	public void menuLoop(Mouse gameMouse, long currentTime) {

		if (isMenuOpen() && (currentTime - timeClicked) >= 500) {
			switch (checkSelection(gameMouse)) {
			case 1:
				closeMenu();
				break;
			case 2:
				closeMenu();
				menuInicialPointer.reOpen();
				break;
			case 3:
				System.exit(0);
				break;
			}
		}

	}

	// Método responsável por administrar as seleções do menu e pausar o tempo no ingame
	public void menuLoop(Mouse gameMouse, long currentTime, long timeHanger) {

		// Pause o tempo in game
		timeHanger += System.currentTimeMillis();
		
		if (isMenuOpen() && (currentTime - timeClicked) >= 500) {
			switch (checkSelection(gameMouse)) {
			case 1:
				closeMenu();
				break;
			case 2:
				closeMenu();
				menuInicialPointer.reOpen();
				break;
			case 3:
				System.exit(0);
				break;
			}
		}

	}

	@Override
	// Método para checar a seleção do mouse
	protected int checkSelection(Mouse gameMouse) {

		if (gameMouse.getButton() == 1) // Quando pressionado o primeiro botão
										// do mouse...
		{
			timeClicked = System.currentTimeMillis(); // Atualiza o delayer

			// Se o mouse estiver no eixo X comum às três opções...
			if (gameMouse.getX() >= 320 && gameMouse.getX() <= 580) {
				if (gameMouse.getY() >= 245 && gameMouse.getY() < 285)
					return 1; // Retorna 1 para a seleção do bounding box de
								// "Resume"

				if (gameMouse.getY() >= 285 && gameMouse.getY() < 325)
					return 2; // Retorna 2 para a seleção do bounding box de
								// "Back to Main Menu"

				if (gameMouse.getY() >= 325 && gameMouse.getY() < 385)
					return 3; // Retorna 3 para a seleção do bounding box de
								// "Quit"
			}
		}
		return 0; // Quando nenhum botão estiver pressionado
	}

	@Override
	// Método responsável pela renderização do menu de pause
	public void draw(Screen gameScreen) {
		gameScreen.renderImage((gameScreen.getWidth() / 2) - 300, (gameScreen.getHeight() / 2) - 150, background);
	}

}
