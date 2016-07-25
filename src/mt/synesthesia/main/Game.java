package mt.synesthesia.main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import mt.synesthesia.main.graphics.*;
import mt.synesthesia.main.logic.*;
import mt.synesthesia.main.menus.*;
import mt.synesthesia.main.system.*;
import mt.synesthesia.main.audio.*;

public class Game extends Canvas implements Runnable {

	// *****************
	// ****ATRIBUTOS****
	// *****************
	private static final long serialVersionUID = 1L;	// ??? - Convenção usar isso aq - ???

	private Thread gameThread;						// Thread principal onde o jogo será executado
	
	private boolean running = false;				// Boolean para administrar o gameloop
	private boolean flashed = false;				// Boolean para verificar se uma cor foi piscada
	private boolean playerTurn = false;				// Boolean para indicar quando é ou não a vez do jogador
	
	private long playerTimer;
	private long timeClicked;						// Atributo utilizado para medir o tempo após uma selação
	private int turnCursor;							// Atributo responsável por apontar para certa posição da lista de sequências
	
	private GameWindow window;						// Janela de execução do jogo
	private MenuInicial menuInicial;				// Menu inicial que inicia o game
	private MenuSkin menuSkin;						// Menu de skins para configurar os players
	private MenuPause menuPause;					// Menu de pause ingame
	private MenuGameOver menuOver;					// Menu de fim de jogo
	
	private Keyboard key;							// Classe para manipulação de entrada de teclado
	private Mouse mouse;							// Classe para manipulação de entrada de mouse
	
	private Screen screen;							// Tela atrelada à janela
	
	private Player[] player;						// Inicia os jogadores do jogo
	private int playerNumber;						// Para dizer qual o player que tá jogando
	
	private Playlist playlist;						// Atributo responsável pela playlist inGame
	
	// Manipulação de gráficos
	private BufferedImage image;
	private int[] pixels;
	
	// *****************
	// *****MÉTODOS*****
	// *****************
	
	// Construtor Padrão
	public Game(){
		
		// Inicialização da Janela e atribuição ao código do jogo
		window = new GameWindow("Synesthesia", 900, 900*9/16);
		window.add(this);
		
		// Inicialização da tela da janela
		screen = new Screen(window.getWidth(), window.getHeight());
		
		// Inicialização dos dados gráficos
		image = new BufferedImage(window.getWidth(), window.getHeight(), BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		// Inicializa os players
		player = new Player[2];
		player[0] = new Player();
		player[1] = new Player();
		player[0].setBoardSkin("/Skins/Classic.png");
		player[1].setBoardSkin("/Skins/Classic.png");
		
		playerNumber = 0;
		
		// Inicialização dos menus
		menuInicial = new MenuInicial();
		menuSkin = new MenuSkin(player, menuInicial);
		menuPause = new MenuPause();
		menuOver = new MenuGameOver();
		
		menuInicial.setMenuSkinPointer(menuSkin);
		menuPause.setMenuInicialPointer(menuInicial);
		menuOver.setMenuInicialPointer(menuInicial);
		
		// Inicia os turnos como 0
		turnCursor = 0;
		
		// Inicia-se o contador
		playerTimer = 0;
		
		// Inicialização de manipuladores de entrada de teclado
		key = new Keyboard();
		addKeyListener(key);
		
		// Inicialização de manipuladores de entrada de mouse
		mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		// Inicialização da playlist
		playlist = new Playlist("inGame");
		
	}
	
	// Método responsável por iniciar a thread
	public synchronized void start() {
			
		running = true;		// Inicia o gameLoop
		
		// Declaração e inicialização do thread do jogo
		gameThread = new Thread(this, "HowCanMirrorsBeReal");
		gameThread.start();
		
	}
	
	// Método responsável por abortar a thread
	public synchronized void stop() {
		
		running = false;	// Para o gameLoop
		
		// Aborção do thread com tratamento de exceção
		try{
			gameThread.join();
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		
	}
	
	// Método responsável pela atualização das operações do programa
	public void update(){
				
		// Inicia o menu inicial
		if(menuInicial.isMenuOpen()){
			playlist.stop();
			if(!menuInicial.getReset()){
				resetGame();
				menuInicial.setReset(true);
			}
			menuInicial.menuLoop(mouse, System.currentTimeMillis());
		}
		
		// Inicia o menu de skins
		if(menuSkin.isMenuOpen())
			menuSkin.menuLoop(mouse, System.currentTimeMillis(), key);
		
		// Inicia o menu de pause
		if(menuPause.isMenuOpen())
			menuPause.menuLoop(mouse, System.currentTimeMillis());
		
		// Inicia o menu de Game Over
		if(menuOver.isMenuOpen())
			menuOver.menuLoop(mouse, System.currentTimeMillis());
		
		// Inicia o jogo de acordo com a opção selecionada
		if(!menuInicial.isMenuOpen() && !menuSkin.isMenuOpen() && !menuPause.isMenuOpen() && !menuOver.isMenuOpen()){
			playlist.play();
			gameLoop();
		}
		
	}
	
	// Método responsável pela atualização gráfica da janela do programa
	public void render(){
		
		// Inicialização dos Buffers
		BufferStrategy bs = getBufferStrategy();
		
		// Verifica se o objeto responsável pelo buffer ja foi criado
		// evitando que a mesma variável seja declarada infinitas vezes no GameLoop
		if (bs == null){
			createBufferStrategy(3);	// 3 Buffers = Principal, Intermediário e Secundário
			return;
		}
		
		// Inicializa um objeto para manipulação gráfica
		Graphics g = bs.getDrawGraphics();
		
		// ---------- Região de Declaração ----------
		
		// Limpa a tela com todos os pixels igual a 0
		screen.clear();
		
		// Renderiza o menu inicial caso o mesmo esteja aberto
		if(menuInicial.isMenuOpen())
			menuInicial.draw(screen);
		
		// Renderiza o menu de skins caso o mesmo esteja aberto
		if(menuSkin.isMenuOpen())
			menuSkin.draw(screen);
		
		// Renderiza o tabuleiro caso o menu esteja fechado
		if(!menuInicial.isMenuOpen() && !menuSkin.isMenuOpen()){
			player[playerNumber].getGUI().draw(screen);
			screen.renderImage(253, 40, player[playerNumber].getBoard().getBoardSkin());
		}
		
		// Renderiza o menu pause (por cima dos gráficos in game)
		if(menuPause.isMenuOpen())
			menuPause.draw(screen);
		
		// Renderiza o menu de fim de jogo (por cima dos gráficos in game)
		if(menuOver.isMenuOpen())
			menuOver.draw(screen);
		
		// Permite um delay de 0,5 segundo e atualiza o tabuleiro
		if(flashed && (System.currentTimeMillis() - timeClicked)  > 500){
			player[playerNumber].getBoard().update();
			flashed = false;
		}
		
		// ------------------------------------------

		// Atribui à janela os pixels formados na Screen
		for (int i=0 ; i < pixels.length ; i++)
			pixels[i] = screen.getPixels()[i];
		
		// Renderiza a imagem do buffer
		g.drawImage(image, 0, 0, window.getWidth(), window.getHeight(), null);
		
		// Atualização do menuSkin
		if(menuSkin.isMenuOpen())
			menuSkin.update(g);
		
		// Atualização do menu de fim de jogo
		if(menuOver.isMenuOpen())
			menuOver.update(g, player);
		
		// Atualização das GUI
		if(!menuInicial.isMenuOpen() && !menuSkin.isMenuOpen())
				player[playerNumber].updateGUI(g, menuInicial.getSelection(), playerTimer);
				
		// Limpa a imagem para receber a próxima renderização
		g.dispose();
		
		// Exibe o buffer gráfico
		bs.show();
		
	}
	
	// Método responsável pela execução da thread e consequente início do aplicativo
	public void run(){
		// Timers
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		
		// Contadores de FPS
		int frames = 0;
		int updates = 0;
		
		// Controle de framerate para o Game Loop
		while (running){
			// Controladores
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			// Atualização operacional
			while (delta >= 1){
				
				// Realiza a atualização operacional //
					update();	
				// ********************************* //
				
				updates++;		// Contabiliza a atualização operacional
				delta--;		// Decrementa o delta para sair dessa etapa
			}
			
			// Realiza a atualização gráfica //
				render();
			// ******************************** //
			
			frames++;			// Contabiliza a atualização gráfica
			
			// Para cada segundo...
			if(System.currentTimeMillis() - timer > 1000){
				// Acrescenta um segundo ao timer
				timer+=1000;
				
				// Configura o títula para exibir os frames e updates
				window.setTitle("Synesthesia" + " | " + updates + " ups, " + frames + " fps");
				
				// Reseta o fps
				updates = 0;
				frames = 0;
			}
		}
	}
	
	// Método responsável por resetar todas as informações do game para os valores padrões, permitindo mais de 1 partida
	private void resetGame() {
		// Reseta os scores e situação de ambos os players
		player[0] = new Player();
		player[1] = new Player();
		player[0].setBoardSkin("/Skins/Classic.png");
		player[1].setBoardSkin("/Skins/Classic.png");
		
		playerNumber = 0;
	
		// Reseta os turnos como 0
		turnCursor = 0;
		
		// Inicia-se o contador
		playerTimer = 0;
		
		// Reset dos booleanos
		flashed = false;
		playerTurn = false;
	}
	
	// Método para checar as interações do jogador com o tabuleiro
	private int playerCheck(){
		
		// Verificações do Teclado
		if(key.getKey(KeyEvent.VK_ESCAPE))		// Se o ESC for pressionado, é aberto o menu de Pause
			menuPause.openMenu();
		
		// Verificações do Mouse
		if(mouse.getButton() == 1) // Se pressionado o primeiro botão do mouse
		{
			// Verifica se a posição do mouse remete a um pixel da cor vermelha
			if(screen.getPixels()[mouse.getX() + screen.getWidth() * mouse.getY()] == -384239 && !flashed){
				player[playerNumber].getBoard().flashColor(1);					// Pisca os pixels vermelhos
				flashed = true;								// Sinaliza a piscada
				timeClicked = System.currentTimeMillis();	// Inicia o contador a partir desse momento
				return 1;
			}
			// Verifica se a posição do mouse remete a um pixel da cor azul
			if(screen.getPixels()[mouse.getX() + screen.getWidth() * mouse.getY()] == -16743969 && !flashed){
				player[playerNumber].getBoard().flashColor(2);					// Pisca os pixels azuis
				flashed = true;								// Sinaliza a piscada
				timeClicked = System.currentTimeMillis();	// Inicia o contador a partir desse momento
				return 2;
			}
			// Verifica se a posição do mouse remete a um pixel da cor amarela
			if(screen.getPixels()[mouse.getX() + screen.getWidth() * mouse.getY()] == -3804 && !flashed){
				player[playerNumber].getBoard().flashColor(3);					// Pisca os pixels amarelos
				flashed = true;								// Sinaliza a piscada
				timeClicked = System.currentTimeMillis();	// Inicia o contador a partir desse momento
				return 3;
			}
			// Verifica se a posição do mouse remete a um pixel da cor amarela
			if(screen.getPixels()[mouse.getX() + screen.getWidth() * mouse.getY()] == -13251771 && !flashed){
				player[playerNumber].getBoard().flashColor(4);					// Pisca os pixels verdes
				flashed = true;								// Sinaliza a piscada
				timeClicked = System.currentTimeMillis();	// Inicia o contador a partir desse momento
				return 4;
			}
		}
		return -1;			// Retorno padrão quando nenhuma tecla for pressionada
	}
	
	// THE EXTREME VERY IMPORTANTE AND MOTHERF**KIN GAMELOOP
	private void gameLoop(){
		
		// Se for selecionado a opção de sair em quaisquer dos menus
		if(menuInicial.getSelection() == -1){
			System.exit(0);			// Termina-se a execução da JVM
			stop();					// Para a Thread (por precaução)
		}
		
		// --------------------------- Verificações de fim de jogo --------------------------------------
		else if (player[0].isMissed())	// Se o player 1 tiver errado, muda-se a vez para o player 2
			playerNumber = 1;
		else if (player[1].isMissed())	// Se o player 2 tiver errado, muda-se a vez para o player 1
			playerNumber = 0;
		// Se ambos os players tiverem errado, declara-se o fim do jogo
		if (player[0].isMissed() && player[1].isMissed()){
			long delay = System.currentTimeMillis();	// Variável para o delay
			
			while(System.currentTimeMillis() - delay <= 250)
				System.out.println("Espera...");
				
			menuOver.openMenu();
		}
		// ----------------------------------------------------------------------------------------------
		
		// Inicia o gameLoop de acordo com a seleção selecionado no menu Inicial
		switch(menuInicial.getSelection()){

		// ------------------------------------------------------------------------------------------------------ //
		// ----------------------------------------- Modo Clássico ---------------------------------------------- //
		case 1:
			if(!playerTurn && player[playerNumber].getScore() < 31 && !flashed){	// Condições para incremento da sequência

				// O incremento acontecerá para N vezes que equivalem à número do turno
				if(player[playerNumber].getScore() > turnCursor){	
				
					// Parando o contador
					playerTimer = 0;
					
					// Para o primeiro cursor do turno irá haver um incremento singular
					if(turnCursor == 0){
						player[playerNumber].getCombinations().addFinal();		// Adiciona-se um elemento no final da lista
						
						long delay = System.currentTimeMillis();	// Variável para o delay
						
						while(System.currentTimeMillis() - delay <= 500)
							System.out.println("Espera...");
					}
						
					turnCursor++;												// Incrementa-se o cursor, para a varredura
					
					// Cria-se um auxiliar que apontará para o header da sequência
					Node temp = player[playerNumber].getCombinations().getHeader();		
					
					for(int i = 0; i < turnCursor - 1; i++)		
						temp = temp.getNext();					// Varre todos os elementos da sequência
					
					player[playerNumber].getBoard().flashColor(temp.getColor());	// Pisca a cor da sequência
					flashed = true;													// Sinaliza a piscada
					timeClicked = System.currentTimeMillis();						// Inicia o contador a partir desse momento
				}
				else{
					playerTurn = true;							// Quando acabar o turno, iniciar o turno do jogador
					playerTimer = System.currentTimeMillis();	// Inicia o contador
				}	
			}
			else if(playerTurn){			// Se o turno for do jogador
				if(turnCursor < 1){				// Para um cursor igual a zero
					player[playerNumber].turnPassed();		// Aumenta-se o número do turno
					playerNumber++;							// Muda-se para o próximo player
					
					// Caso o incremente supere 2 (incremento do player 2), volta-se para o player 1
					if(playerNumber >= 2)					
						playerNumber = 0;
					
					playerTurn = false;			// Finaliza o turno do jogador
				}
				else{
					// Variável para armazenar a cor selecionada
					int pressed = playerCheck();
					
					// Verifica se o jogador pressionou a cor correta				
					if(pressed == player[playerNumber].getCombinations().getHeader().getColor()){
						turnCursor--;		// Decrementa-se o cursor
						
						playerTimer = System.currentTimeMillis();	// Reinicia o contador
						
						// Re-empilha o elemento que foi removido para a checagem
						player[playerNumber].getCombinations().addFinal(player[playerNumber].getCombinations().removerInicio().getColor());
					}
					// Se o jogador pressinae a cor incorreta, ele terá errado OH BABY A TRIPLE
					else if(pressed != -1 || player[playerNumber].timeElapsed(playerTimer) >= 5){
						// Diz-se que o tal player errou
						player[playerNumber].setMissed(true);
						
						turnCursor = 0;				// Reseta-se o cursor
						playerNumber++;				// Vai para o próximo player
						
						// Caso o incremente supere 2 (incremento do player 2), volta-se para o player 1
						if(playerNumber >= 2)		
							playerNumber = 0;
						
						playerTurn = false;			// Finaliza o turno do jogador
					}
				}
			}
			break;
		// ------------------------------------------------------------------------------------------------------ //
		// ----------------------------------------- Modo Espelhado --------------------------------------------- //
		case 2:	
			if(!playerTurn && player[playerNumber].getScore() < 31 && !flashed){	// Condições para incremento da sequência

				// O incremento acontecerá para N vezes que equivalem à número do turno
				if(player[playerNumber].getScore() > turnCursor){	
				
					// Parando o contador
					playerTimer = 0;
					
					// Para o primeiro cursor do turno irá haver um incremento singular
					if(turnCursor == 0){
						player[playerNumber].getCombinations().addFinal();	// Adiciona-se um elemento no final da lista
						long delay = System.currentTimeMillis();	// Variável para o delay
						
						while(System.currentTimeMillis() - delay <= 500)
							System.out.println("Espera...");
					}
						
					turnCursor++;											// Incrementa-se o cursor, para a varredura
					
					// Cria-se um auxiliar que apontará para o header da sequência
					Node temp = player[playerNumber].getCombinations().getHeader();
					
					for(int i = 0; i < turnCursor - 1; i++)		
						temp = temp.getNext();					// Varre todos os elementos da sequência
					
					player[playerNumber].getBoard().flashColor(temp.getColor());	// Pisca a cor da sequência
					flashed = true;													// Sinaliza a piscada
					timeClicked = System.currentTimeMillis();						// Inicia o contador a partir desse momento
				}
				else{
					playerTurn = true;							// Quando acabar o turno, iniciar o turno do jogador
					playerTimer = System.currentTimeMillis();	// Inicia o contador
				}	
			}
			else if(playerTurn){			// Se o turno for do jogador
				if(turnCursor < 1){				// Para um cursor igual a zero
					player[playerNumber].turnPassed();		// Aumenta-se o número do turno
					playerNumber++;							// Muda-se para o próximo player
					
					// Caso o incremente supere 2 (incremento do player 2), volta-se para o player 1
					if(playerNumber >= 2)					
						playerNumber = 0;
					
					playerTurn = false;			// Finaliza o turno do jogador
				}
				else{
					// Variável para armazenar a cor selecionada
					int pressed = playerCheck();
					
					// Verifica se o jogador pressionou a cor correta				
					if(pressed == player[playerNumber].getCombinations().getTrailer().getColor()){
						turnCursor--;		// Decrementa-se o cursor
						
						playerTimer = System.currentTimeMillis();	// Reinicia o contador
						
						// Re-empilha o elemento que foi removido para a checagem
						player[playerNumber].getCombinations().addInicio(player[playerNumber].getCombinations().removerFinal().getColor());
					}
					// Se o jogador pressinae a corr incorreta, ele terá errado
					else if(pressed != -1 || player[playerNumber].timeElapsed(playerTimer) >= 5){
						// Diz-se que o tal player errou
						player[playerNumber].setMissed(true);
						
						turnCursor = 0;				// Reseta-se o cursor
						playerNumber++;				// Vai para o próximo player
						
						// Caso o incremente supere 2 (incremento do player 2), volta-se para o player 1
						if(playerNumber >= 2)		
							playerNumber = 0;
						
						playerTurn = false;			// Finaliza o turno do jogador
					}
				}
			}
			break;
		// ------------------------------------------------------------------------------------------------------ //
		// ----------------------------------------- Modo Fantasma ---------------------------------------------- // 
		case 3:	// Modo fantasma
			if(!playerTurn && player[playerNumber].getScore() < 31 && !flashed){	// Condições para incremento da sequência

				// O incremento acontecerá para N vezes que equivalem à número do turno
				if(player[playerNumber].getScore() > turnCursor){	
				
					// Parando o contador
					playerTimer = 0;
					
					// Para o primeiro cursor do turno irá haver um incremento singular
					if(turnCursor == 0){
						player[playerNumber].getCombinations().addFinal();	// Adiciona-se um elemento no final da lista
						long delay = System.currentTimeMillis();	// Variável para o delay
						
						while(System.currentTimeMillis() - delay <= 500)
							System.out.println("Espera...");
					}
					
					turnCursor++;											// Incrementa-se o cursor, para a varredura
					
					// Cria-se um auxiliar que apontará para o header da sequência
					Node temp = player[playerNumber].getCombinations().getHeader();		
					
					for(int i = 0; i < turnCursor - 1; i++)		
						temp = temp.getNext();					// Varre todos os elementos da sequência
					
					// Só irá exibir as duas últimas sequências e as ímpares
					if(turnCursor % 2 != 0 || turnCursor > player[playerNumber].getScore() - 1)
						player[playerNumber].getBoard().flashColor(temp.getColor());	// Pisca a cor da sequência
					else
						player[playerNumber].getBoard().ring(temp.getColor());			// Apenas toca o som
					
					flashed = true;								// Sinaliza a piscada
					timeClicked = System.currentTimeMillis();	// Inicia o contador a partir desse momento
				}
				else{
					playerTurn = true;							// Quando acabar o turno, iniciar o turno do jogador
					playerTimer = System.currentTimeMillis();	// Inicia o contador
				}	
			}
			else if(playerTurn){			// Se o turno for do jogador
				if(turnCursor < 1){				// Para um cursor igual a zero
					player[playerNumber].turnPassed();		// Aumenta-se o número do turno
					playerNumber++;							// Muda-se para o próximo player
					
					// Caso o incremente supere 2 (incremento do player 2), volta-se para o player 1
					if(playerNumber >= 2)					
						playerNumber = 0;
					
					playerTurn = false;			// Finaliza o turno do jogador
				}
				else{
					// Variável para armazenar a cor selecionada
					int pressed = playerCheck();
					
					// Verifica se o jogador pressionou a cor correta				
					if(pressed == player[playerNumber].getCombinations().getHeader().getColor()){
						turnCursor--;		// Decrementa-se o cursor
						
						playerTimer = System.currentTimeMillis();	// Reinicia o contador
						
						// Re-empilha o elemento que foi removido para a checagem
						player[playerNumber].getCombinations().addFinal(player[playerNumber].getCombinations().removerInicio().getColor());
					}
					// Se o jogador pressinae a corr incorreta, ele terá errado
					else if(pressed != -1 || player[playerNumber].timeElapsed(playerTimer) >= 5){
						// Diz-se que o tal player errou
						player[playerNumber].setMissed(true);
						
						turnCursor = 0;				// Reseta-se o cursor
						playerNumber++;				// Vai para o próximo player
						
						// Caso o incremente supere 2 (incremento do player 2), volta-se para o player 1
						if(playerNumber >= 2)		
							playerNumber = 0;
						
						playerTurn = false;			// Finaliza o turno do jogador
					}
				}
			}
			break;
		// ------------------------------------------------------------------------------------------------------ //
		// ----------------------------------------- Modo Loucura Total ----------------------------------------- // 
		case 4:
			if(!playerTurn && player[playerNumber].getScore() < 31 && !flashed){	// Condições para incremento da sequência

				// O incremento acontecerá para N vezes que equivalem à número do turno
				if(player[playerNumber].getScore() > turnCursor){	

					// Parando o contador
					playerTimer = 0;
					
					// Apaga a lista para receber uma lista nova
					if(turnCursor == 0){
						for(int i = 0; i < player[playerNumber].getScore() - 1; i++)
							player[playerNumber].getCombinations().removerInicio();
						
						long delay = System.currentTimeMillis();	// Variável para o delay
						
						while(System.currentTimeMillis() - delay <= 500)
							System.out.println("Espera...");
					}
						
					// Essa adição, agora, acontecerá N vezes para N = turno
					player[playerNumber].getCombinations().addFinal();			// Adiciona-se um elemento no final da lista
					
					turnCursor++;												// Incrementa-se o cursor, para a varredura
					
					// Cria-se um auxiliar que apontará para o header da sequência
					Node temp = player[playerNumber].getCombinations().getHeader();		
					
					for(int i = 0; i < turnCursor - 1; i++)		
						temp = temp.getNext();					// Varre todos os elementos da sequência
					
					player[playerNumber].getBoard().flashColor(temp.getColor());		// Pisca a cor da sequência
					
					flashed = true;								// Sinaliza a piscada
					timeClicked = System.currentTimeMillis();	// Inicia o contador a partir desse momento
				}
				else{
					playerTurn = true;							// Quando acabar o turno, iniciar o turno do jogador
					playerTimer = System.currentTimeMillis();	// Inicia o contador
				}	
			}
			else if(playerTurn){			// Se o turno for do jogador
				if(turnCursor < 1){				// Para um cursor igual a zero
					player[playerNumber].turnPassed();		// Aumenta-se o número do turno
					playerNumber++;							// Muda-se para o próximo player
					
					// Caso o incremente supere 2 (incremento do player 2), volta-se para o player 1
					if(playerNumber >= 2)					
						playerNumber = 0;
					
					playerTurn = false;			// Finaliza o turno do jogador
				}
				else{
					// Variável para armazenar a cor selecionada
					int pressed = playerCheck();
					
					// Verifica se o jogador pressionou a cor correta				
					if(pressed == player[playerNumber].getCombinations().getHeader().getColor()){
						turnCursor--;		// Decrementa-se o cursor
						
						playerTimer = System.currentTimeMillis();	// Reinicia o contador
						
						// Re-empilha o elemento que foi removido para a checagem
						player[playerNumber].getCombinations().addFinal(player[playerNumber].getCombinations().removerInicio().getColor());
					}
					// Se o jogador pressinae a corr incorreta, ele terá errado
					else if(pressed != -1 || player[playerNumber].timeElapsed(playerTimer) >= 5){
						// Diz-se que o tal player errou
						player[playerNumber].setMissed(true);
						
						turnCursor = 0;				// Reseta-se o cursor
						playerNumber++;				// Vai para o próximo player
						
						// Caso o incremente supere 2 (incremento do player 2), volta-se para o player 1
						if(playerNumber >= 2)		
							playerNumber = 0;
						
						playerTurn = false;			// Finaliza o turno do jogador
					}
				}
			}
			break;
		// ------------------------------------------------------------------------------------------------------ //
		}		
	}
}
