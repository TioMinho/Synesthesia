package mt.synesthesia.main.system;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;	// Classe Java para manipular janelas

// Classe para manipula��o da Janela do Game

public class GameWindow extends JFrame {
	
	// *****************
	// ****ATRIBUTOS****
	// *****************
	
	private static final long serialVersionUID = 1L;	// ??? - Conven��o usar isso a� - ???
	
	// Resolu��o Padr�o - 16x9
	public static int width = 300;						// Largura
	public static int height = width / 16 * 9;			// Altura
	public static int scale = 3;						// Escala
	
	private boolean enableFullscreen = false;			// Boolean para permitir ou proibir o Fullscreen
	private int modeFullscreen = 0;						// Seletor inteiro para o modo Fullscrean a implementar
	
	// Objeto respons�vel por receber os valores da tela do computador do usu�rio 
	private GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	
	
	// *****************
	// *****M�TODOS*****
	// *****************
	
	// Construtor Padr�o
	public GameWindow(String title){
		
		// T�tulo da Janela
		setTitle(title);
		
		// Dimens�es da Janela
		setSize(GameWindow.width * GameWindow.scale, GameWindow.height * GameWindow.scale);
		
		// Inicia a Janela centralizada
		setLocationRelativeTo(null);
		
		// Condiciona o fechamento da janela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		// Impede que a tela seja redimensionada
		setResizable(false);
		
		// Exibe a janela
		setVisible(true); 
	
	}
	
	// Construtor Principal - Janela
	public GameWindow(String title, int width, int height) {
			
			// T�tulo da Janela 
			setTitle(title);
			
			// Dimens�es da Janela
			setSize(width, height);
			
			// Inicia a Janela centralizada
			setLocationRelativeTo(null);
			
			// Condiciona o fechamento da janela
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			// Impede que a tela seja redimensionada
			setResizable(false);
			
			// Exibe a janela
			setVisible(true);
		}

	// Construtor Principal - Fullscreen
	// Construtor Principal - Fullscreen
	public GameWindow(String title, int fullscreen_mode){
		// T�tulo da Janela 
		setTitle(title);
	
		// Inicializa um modo de tela cheia
		setFullscreen(fullscreen_mode);
			
		// Inicia a Janela centralizada
		setLocationRelativeTo(null);
			
		// Condiciona o fechamento da janela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					
		// Impede que a tela seja redimensionada
		setResizable(false);
			
		// Exibe a janela
		setVisible(true);
	}
	
	// Manipulador de dimens�es de tela cheia
	// Manipulador de dimens�es Fullscreen
	private void FullscreenMode(){
		switch(modeFullscreen)
		{
		case 0:	// Modo Janela - Sem fullscreen
			System.out.println("No Fullscreen");
			setUndecorated(false);
			break;
		case 1:	// Modo Fullscreen aparente - Janela maximizada ao m�ximo
			setUndecorated(true);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			break;
		case 2:	// Modo Fullscreen - tela cheia
			setUndecorated(true);
			device.setFullScreenWindow(this);
			break;
		}
	}
	
	// Seleciona o tipo de Tela Cheia
	// Setter para o modo Fullscreen
	public void setFullscreen(int fullscreen_mode){
		
		enableFullscreen = true;	// Indica que o modo Fullscreen est� ativado
		
		if (fullscreen_mode <= 2){
			this.modeFullscreen = fullscreen_mode;	// Atribui o tipo de fullscreen desejado
			FullscreenMode();						// Ativa o fullscreen
		}
		else
			System.out.println("O modo de numero "+ fullscreen_mode + " � invalido.");
	}

	// Adiciona um componente � janela
	
	public int getScale(){
		return this.scale;
	}
	
}
