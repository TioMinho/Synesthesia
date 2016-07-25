package mt.synesthesia.main.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

// GUI = Graphic Users Interface
public class GUI {

	// *****************
	// ****ATRIBUTOS****
	// *****************
	
	private Image backGround;
	private Font font;
	
	// *****************
	// *****MÉTODOS*****
	// *****************
	
	// Construtor Padrão
	public GUI() {
		
		// Inicializa a imagem da GUI
		backGround = new Image("/GUI/gameGUI.png", 900, 506);
		
		// Inicializa a fonte
		font = new Font("tahoma", Font.BOLD, 14);
		
	}
	
	// Método de atualizar a GUI
	public void update (Graphics g, String playerName, int playerTurn, int gameMode, int timeCounter){
		
		// Seleciona a fonte
		font = new Font("tahoma", Font.BOLD, 14);
		g.setFont(font);
		g.setColor(Color.WHITE);
				
		// String para o modo de jogo
		String modeString = new String();
	
		switch(gameMode){
		case 1:
			modeString = "Classic";
			break;
		case 2:
			modeString = "Mirror Mode";
			break;
		case 3:
			modeString = "Ghost Mode";
			break;
		case 4:
			modeString = "Total Madness";
			break;
		}
		
		// Máximo = 12 letras, PosX = 85, PosY = 87
		g.drawString(playerName, 85, 87);
		
		// PosX = 77, PosY = 112
		g.drawString(Integer.toString(playerTurn), 77, 112);
		
		// PosX = 85, PosY = 140
		g.drawString(modeString, 85, 140);
		
		
		// Seleciona a fonte para o cronômetro
		font = new Font("tahoma", Font.BOLD, 60);
		g.setFont(font);
		g.setColor(Color.WHITE);
		
		// PosX = 767, PosY = 130
		int timer = 5 - timeCounter;
		
		// Evita que contabilize numeros negativos
		if(timeCounter > 5)
			timer = 0;
		
		g.drawString(Integer.toString(timer), 767, 130);
		
	}
	
	// Método para renderizar a GUI
	public void draw(Screen gameScreen){
		gameScreen.renderImage(0, 0, backGround);
	}

}
