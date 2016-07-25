package mt.synesthesia.main.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import mt.synesthesia.main.graphics.Image;
import mt.synesthesia.main.graphics.Screen;
import mt.synesthesia.main.logic.Player;
import mt.synesthesia.main.system.Mouse;

public class MenuGameOver extends Menu{

	// *****************
	// ****ATRIBUTOS****
	// *****************
	
	/* PONTEIROS */
	MenuInicial menuInicialPointer;
	
	// *****************
	// *****MÉTODOS*****		
	// *****************
	
	public MenuGameOver() {
		
		// Inicialização do background
		background = new Image("/Menus/gameOver.png", 600, 300);
		
		// Inicialização do áudio como nulo (não há som de fundo)
		bgMusic = null;
		
		// Inicializa o delayer
		timeClicked = System.currentTimeMillis();
		
	}

	// Setter para o ponteiro do menu inicial
	public void setMenuInicialPointer(MenuInicial menuInicialPointer) {
		this.menuInicialPointer = menuInicialPointer;
	}
	
	@Override
	// Sobreescrita do método de abrir o menu
	public void openMenu() {
		isOpen = true;
	}
	
	@Override
	// Sobreescrita do método de fechar o menu
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
				menuInicialPointer.reOpen();
				break;
			case 2:
				System.exit(0);
				break;
			}
		}

	}

	@Override
	// Método para checar as seleções do mouse
	protected int checkSelection(Mouse gameMouse) {

		if (gameMouse.getButton() == 1) // Quando pressionado o primeiro botão
										// do mouse...
		{
			timeClicked = System.currentTimeMillis(); // Atualiza o delayer

			// Se o mouse estiver no eixo X comum às três opções...
			if (gameMouse.getX() >= 373 && gameMouse.getX() <= 523) {
				if (gameMouse.getY() >= 284 && gameMouse.getY() < 324)
					return 1; // Retorna 1 para a seleção do bounding box de
								// "Restart"

				if (gameMouse.getY() >= 324 && gameMouse.getY() < 364)
					return 2; // Retorna 2 para a seleção do bounding box de
								// "Quit"
			}
		}
		return 0; // Quando nenhum botão estiver pressionado
	}

	// Método responsável pela atualização do nome a ser exibido como vencedor
	public void update(Graphics g, Player[] players) {
		
		Font font = new Font("tahoma", Font.BOLD, 22);
		
		g.setFont(font);
		g.setColor(Color.WHITE);
		
		if(players[0].getScore() > players[1].getScore())
			g.drawString(players[0].getName(), 402, 252);
		else if(players[0].getScore() < players[1].getScore())
			g.drawString(players[1].getName(), 402, 252);
		else
			g.drawString("Draw!", 402, 252);
		
	}
	
	@Override
	// Método responsável por renderizar o menu
	public void draw(Screen gameScreen) {
		gameScreen.renderImage((gameScreen.getWidth() / 2) - 300, (gameScreen.getHeight() / 2) - 150, background);
	}

}
