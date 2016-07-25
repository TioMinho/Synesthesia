package mt.synesthesia.main.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import mt.synesthesia.main.audio.Sound;
import mt.synesthesia.main.graphics.*;
import mt.synesthesia.main.system.*;
import mt.synesthesia.main.logic.*;

public class MenuSkin extends Menu{

	// *****************
	// ****ATRIBUTOS****
	// *****************
	
	private int[] skinIndex; 			// Atributo referente ao seletor de skin
	private String[] playerName;		// Vetor de 2 Strings para armazenar os nomes dos players
	private String[] skinName;			// Vetor de 2 Strings para armazenar os nomes das skins dos players
	private Image skinPreview;			// Imagem para dar preview da skin a ser escolhida
	private int nameSelected;			// Vari�vel para indicar qual nome deve ser modificado pelo seletor do menu
	private long timeTyped;				// Vari�vel para indicar o tempo no qual uma tecla for pressionada
	
	/*PONTEIROS*/
	private Player[] playersPointers;
	private MenuInicial menuInicialPointer;
	
	// *****************
	// *****M�TODOS*****		
	// *****************
	
	// Construtor Padr�o
	public MenuSkin(Player[] playerPointers, MenuInicial menuInicialPointer) {
		
		// Inicializa��o do background
		background = new Image("/Menus/menuSkins.png", 900, 506);
		
		// Inicializa a BGM (background music)
		bgMusic = new Sound("/Music/Intermission.mp3");
		
		// Inicializa��o do �ndice das skins
		skinIndex = new int[2];
		skinIndex[0] = 0;
		skinIndex[1] = 0;
		
		// Inicializa��o dos nomes dos jogadores
		playerName = new String[2];
		playerName[0] = "PLAYER 1";
		playerName[1] = "PLAYER 2";
		
		// Inicializa��o dos nomes das Skins
		skinName = new String[11];
		skinName[0] = "Classic";
		skinName[1] = "GoT";
		skinName[2] = "Herleson";
		skinName[3] = "theStar";
		skinName[4] = "Twister";
		skinName[5] = "Mixxx";
		skinName[6] = "Blurred";
		skinName[7] = "theBall";
		skinName[8] = "Minimum";
		skinName[9] = "Mirror";
		skinName[10] = "Synesthesia";
		
		// Inicializa��o da Imagem do visualizador de Skins
		skinPreview = new Image("/Skins/Classic.png", 400, 400);
		
		// Inicializa o contador do delay de clicagem do mouse
		timeClicked = System.currentTimeMillis();
		
		// Inicializa o contador do delay de teclagem do teclado
		timeTyped = System.currentTimeMillis();
		
		// Inicializa os ponteiros dos players
		this.playersPointers = playerPointers;
		
		// Inicializa o ponteiro do menu inicial
		this.menuInicialPointer = menuInicialPointer;
		
	}

	@Override
	// M�todo de fechamento do menu
	public void closeMenu() {
		
		isOpen = false;		// Fecha o menu
		bgMusic.stop();		// Para a m�sica
		
		/* Atribui��o aos players dos atributos modificados no menu Skin */
		// Player 1
		playersPointers[0].setBoardSkin("/Skins/" + skinName[skinIndex[0]] + ".png");
		playersPointers[0].setName(playerName[0]);
		
		// Player 1
		playersPointers[1].setBoardSkin("/Skins/" + skinName[skinIndex[1]] + ".png");
		playersPointers[1].setName(playerName[1]);
	}
	
	@Override
	// M�todo respons�vel por administrar o funcionamento do menu
	public void menuLoop(Mouse gameMouse, long currentTime) {
		
		// Caso o menu esteja aberto e o delay for atendido...
		if(isMenuOpen() && (currentTime - timeClicked) >= 500)
		{
			// Verifica-se a sele�ao do mouse do usu�rio
			switch(checkSelection(gameMouse)){
			case -1:	// Caso seja -1, ir� voltar ao menu principal
				closeMenu();
				menuInicialPointer.openMenu();
				break;
			case 1:		// Caso seja selecionada a caixa do nome do player 1
				nameSelected = 0;
				break;
			case 2:		// Caso seja 1, o seletor de skins ir� voltar um �ndice para o player 1
				skinIndex[0]--;					// Volta-se um �ndice
				
				// Caso o �ndice seja menor que zero, reseta-se para o maior �ndice
				if(skinIndex[0] < 0)
					skinIndex[0] = 10;
				
				// Muda a imagem do visualizador de Skin para a skin que est� sendo escolhida
				skinPreview = new Image("/Skins/" + skinName[skinIndex[0]] + ".png", 400, 400);
				break;
			case 3:		// Caso seja 2, o seletor de skins ir� avan�ar um �ndice para o player 1
				skinIndex[0]++;					// Aumenta-se um �ndice
				
				// Caso o �ndice seja maior que o limite de skins, retorna-se para o primeiro
				if(skinIndex[0] > 10)
					skinIndex[0] = 0;
				
				// Muda a imagem do visualizador de Skin para a skin que est� sendo escolhida
				skinPreview = new Image("/Skins/" + skinName[skinIndex[0]] + ".png", 400, 400);
				break;
			case 4:		// Caso seja selecionada a caixa do nome do player 2
				nameSelected = 1;
				break;
			case 5:		// Caso seja 3, o seletor de skins ir� voltar um �ndice para o player 2
				skinIndex[1]--;					// Volta-se um �ndice
				
				// Caso o �ndice seja menor que zero, reseta-se para o maior �ndice
				if(skinIndex[1] < 0)
					skinIndex[1] = 10;
				
				// Muda a imagem do visualizador de Skin para a skin que est� sendo escolhida
				skinPreview = new Image("/Skins/" + skinName[skinIndex[1]] + ".png", 400, 400);
				break;
			case 6:		// Caso seja 4, o seletor de skins ir� avan�ar um �ndice para o player 2
				skinIndex[1]++;					// Aumenta-se um �ndice
				
				// Caso o �ndice seja maior que o limite de skins, retorna-se para o primeiro
				if(skinIndex[1] > 10)
					skinIndex[1] = 0;
				
				// Muda a imagem do visualizador de Skin para a skin que est� sendo escolhida
				skinPreview = new Image("/Skins/" + skinName[skinIndex[1]] + ".png", 400, 400);
				break;
			}
		}
		
	}

	// M�todo respons�vel por administrar o funcionamento do menu
	public void menuLoop(Mouse gameMouse, long currentTime, Keyboard keyboard) {
		
		// Caso o menu esteja aberto e o delay for atendido...
		if(isMenuOpen() && (currentTime - timeClicked) >= 500)
		{
			readName(keyboard);
			
			// Verifica-se a sele�ao do mouse do usu�rio
			switch(checkSelection(gameMouse)){
			case -1:	// Caso seja -1, ir� voltar ao menu principal
				closeMenu();
				menuInicialPointer.openMenu();
				break;
			case 1:		// Caso seja selecionada a caixa do nome do player 1
				nameSelected = 0;
				break;
			case 2:		// Caso seja 1, o seletor de skins ir� voltar um �ndice para o player 1
				skinIndex[0]--;					// Volta-se um �ndice
				
				// Caso o �ndice seja menor que zero, reseta-se para o maior �ndice
				if(skinIndex[0] < 0)
					skinIndex[0] = 10;
				
				// Muda a imagem do visualizador de Skin para a skin que est� sendo escolhida
				skinPreview = new Image("/Skins/" + skinName[skinIndex[0]] + ".png", 400, 400);
				break;
			case 3:		// Caso seja 2, o seletor de skins ir� avan�ar um �ndice para o player 1
				skinIndex[0]++;					// Aumenta-se um �ndice
				
				// Caso o �ndice seja maior que o limite de skins, retorna-se para o primeiro
				if(skinIndex[0] > 10)
					skinIndex[0] = 0;
				
				// Muda a imagem do visualizador de Skin para a skin que est� sendo escolhida
				skinPreview = new Image("/Skins/" + skinName[skinIndex[0]] + ".png", 400, 400);
				break;
			case 4:		// Caso seja selecionada a caixa do nome do player 2
				nameSelected = 1;
				break;
			case 5:		// Caso seja 3, o seletor de skins ir� voltar um �ndice para o player 2
				skinIndex[1]--;					// Volta-se um �ndice
				
				// Caso o �ndice seja menor que zero, reseta-se para o maior �ndice
				if(skinIndex[1] < 0)
					skinIndex[1] = 10;
				
				// Muda a imagem do visualizador de Skin para a skin que est� sendo escolhida
				skinPreview = new Image("/Skins/" + skinName[skinIndex[1]] + ".png", 400, 400);
				break;
			case 6:		// Caso seja 4, o seletor de skins ir� avan�ar um �ndice para o player 2
				skinIndex[1]++;					// Aumenta-se um �ndice
				
				// Caso o �ndice seja maior que o limite de skins, retorna-se para o primeiro
				if(skinIndex[1] > 10)
					skinIndex[1] = 0;
				
				// Muda a imagem do visualizador de Skin para a skin que est� sendo escolhida
				skinPreview = new Image("/Skins/" + skinName[skinIndex[1]] + ".png", 400, 400);
				break;
			}
		}
		
	}

	
	@Override
	// M�todo para checar as posi��es do mouse
	protected int checkSelection(Mouse gameMouse) {
	
		if(gameMouse.getButton() == 1)	// Quando pressionado o primeiro bot�o do mouse
		{
			// Reseta-se o delayer
			timeClicked = System.currentTimeMillis();
			
			// Se o mouse se localizar no bounding box do cursor de retorno
			if(gameMouse.getY() >= 0 && gameMouse.getY() <= 75)	{
				if(gameMouse.getX() >= 810)
					return -1;		// Retorna o valor de -1
			}
			
			// Se o mouse se localizar no bounding box do nome do player 1
			if(gameMouse.getX() >= 630 && gameMouse.getY() <= 820){
				if(gameMouse.getY() >= 115 && gameMouse.getY() <= 145)
					return 1;
				
				if(gameMouse.getY() >= 350 && gameMouse.getY() <= 380)
					return 4;
			}
			
			// Se o mouse se localizar no bounding box dos cursores do seletor de skin do player 1
			if(gameMouse.getY() >= 160 && gameMouse.getY() <= 200) {
				if(gameMouse.getX() >= 605 && gameMouse.getX() <= 650) 
					return 2;		// Retorna 1 para o previous
				
				if(gameMouse.getX() >= 780 && gameMouse.getY() <= 820) 
					return 3;		// Retorna 2 para o next
			}
			
			// Se o mouse se localizar no bounding box do cursores do seletor de skin do player 2
			if(gameMouse.getY() >= 395 && gameMouse.getY() <= 435) {
				if(gameMouse.getX() >= 605 && gameMouse.getX() <= 650) 
					return 5;		// Retorna 3 para o previous
				
				if(gameMouse.getX() >= 780 && gameMouse.getY() <= 820) 
					return 6;		// Retorna 4 para o next
			}
		}
		
		return 0;		// Caso nada aconte�a, o retorno � 0
	}
	
	// M�todo respons�vel pela entrada do nome de um determinado player
	public void readName(Keyboard keyboard) {
		
		if(keyboard.isKeyPressed() && (System.currentTimeMillis() - timeTyped) >= 100 && playerName[nameSelected].length() < 12){
			timeTyped = System.currentTimeMillis();
			playerName[nameSelected] += (char) keyboard.getKey();
		}
		
		if(keyboard.getKey(KeyEvent.VK_BACK_SPACE) && playerName[nameSelected].length() > 0 && (System.currentTimeMillis() - timeTyped) >= 100){
			timeTyped = System.currentTimeMillis();
			playerName[nameSelected] = playerName[nameSelected].substring(0, playerName[nameSelected].length() - 1);
		}
		
	}

	// M�todo respons�vel por atualizar as strings dos seletores
	public void update(Graphics g) {
		
		// Cria��o da fonte
		Font font = new Font("tahoma", Font.BOLD, 18);
		
		// Modifica��o da fonte e da cor a ser exibida pelo texto
		g.setFont(font);
		g.setColor(Color.WHITE);
		
		// Renderiza��o dos textos da caixa de sele��o do primeiro player
		if(nameSelected == 0)
			g.setColor(Color.RED);
		g.drawString(playerName[0], 632, 137);					// Nome
		
		g.setColor(Color.WHITE);
		g.drawString(skinName[skinIndex[0]], 685, 185);			// Skin
		
		// Renderiza��o dos textos da caixa de sele��o do segundo player
		if(nameSelected == 1)
			g.setColor(Color.RED);
		g.drawString(playerName[1], 633, 374);					// Nome
		
		g.setColor(Color.WHITE);
		g.drawString(skinName[skinIndex[1]], 685, 422);			// Skin
		
	}
	
	@Override
	// M�todo respons�vel por renderizar as imagens do menu
	public void draw(Screen gameScreen) {
		gameScreen.renderImage(0, 0, background);			// Renderiza��o da imagem de fundo
		gameScreen.renderImage(75, 50, skinPreview);		// Renderiza��o do visualizador de skins
	}
	
}
