package mt.synesthesia.main.graphics;

import mt.synesthesia.main.audio.Sound;

public class Board {

	// *****************
	// ****ATRIBUTOS****
	// *****************
	
	private Image boardSkin;		// Imagem que representa o tabuleiro
	private int width, height;		// Largura e altura do tabuleiro, 400x400 por padrão
	private Sound[] soundEffect;	// Efeito sonoro do tabuleiro
	
	// *****************
	// *****MÉTODOS*****
	// *****************
	
	// Construtor Padrão
	public Board(){
		
		// Inicializa a largura e altura padrão das boards
		width = 400;
		height = 400;
		
		// Inicialização de áudio
		soundEffect = new Sound[4];
		
		// Inicialização individual de cada efeito sonoro
		soundEffect[0] = new Sound("/Music/firstNote.mp3");
		soundEffect[1] = new Sound("/Music/secondNote.mp3");
		soundEffect[2] = new Sound("/Music/thirdNote.mp3");
		soundEffect[3] = new Sound("/Music/forthNote.mp3");
		
		// Imagem padrão para as boards
		boardSkin = new Image("/Skins/Classic.png", width, height);
		
	}

	// Construtor Personalizado
	public Board(String skinPath){
		
		// Inicializa a largura e altura padrão das boards
		width = 400;
		height = 400;
		
		// Inicialização de áudio
		soundEffect = new Sound[4];
		
		// Inicialização individual de cada efeito sonoro
		soundEffect[0] = new Sound("/Music/firstNote.mp3");
		soundEffect[1] = new Sound("/Music/secondNote.mp3");
		soundEffect[2] = new Sound("/Music/thirdNote.mp3");
		soundEffect[3] = new Sound("/Music/forthNote.mp3");
		
		// Inicializa a imagem da board de acordo com o que for informado no construtor
		boardSkin = new Image(skinPath, width, height);
		
	}
	
	/* GETTERS */
	public Image getBoardSkin(){
		return boardSkin;
	}
	
	int getWidth(){
		return width;
	}
	
	int getHeight(){
		return height;
	}
	
	/* SETTERS */
	public void setSkin(String skinPath){	// Setter para a imagem do tabuleiro
		boardSkin = new Image(skinPath, width, height);
	}
	
	// Método para piscar a cor
	// 1 - Vermelho, 2 - Azul, 3 - Amarelo, 4 - Verde
	public void flashColor(int colorNumber){
		
		switch(colorNumber){
		case 1: // Pisca a cor Vermelha
			for(int i = 0; i < width * height ; i++)	// Varre todos os piexels
				if(boardSkin.getPixels()[i] == -384239)	
					boardSkin.setPixels(0xFF8F87, i);	// Ilumina os pixels vermelhos
			soundEffect[0].play();						// Toca o efeito sonoro
			break;
		case 2: // Pisca a cor Azul
			for(int i = 0; i < width * height ; i++)	// Varre todos os pixels
				if(boardSkin.getPixels()[i] == -16743969)
					boardSkin.setPixels(0x75B2DD, i);	// Ilumina os pixels azuis
			soundEffect[1].play();						// Toca o efeito sonoro
			break;
		case 3: // Pisca a cor Amarela
			for(int i = 0; i < width * height ; i++)	// Varre todos os pixels
				if(boardSkin.getPixels()[i] == -3804)
					boardSkin.setPixels(0xFFF787, i);	// Ilumina os pixels amarelos
			soundEffect[2].play();						// Toca o efeito sonoro
			break;
		case 4: // Pisca a cor Verde
			for(int i = 0; i < width * height ; i++)	// Varre todos os pixels
				if(boardSkin.getPixels()[i] == -13251771)
					boardSkin.setPixels(0x7EC986, i);	// Ilumina os pixels verdes
			soundEffect[3].play();						// Toca o efeito sonoro
			break;
		}
	}

	// Método para apenas tocar o som daquela determinada cor (Ghost Mode)
	public void ring(int colorNumber) {
		switch(colorNumber){
		case 1: // Toca a cor Vermelha
			soundEffect[0].play();						// Toca o efeito sonoro
			break;
		case 2: // Toca a cor Azul
			soundEffect[1].play();						// Toca o efeito sonoro
			break;
		case 3: // Toca a cor Amarela
			soundEffect[2].play();						// Toca o efeito sonoro
			break;
		case 4: // Toca a cor Verde
			soundEffect[3].play();						// Toca o efeito sonoro
			break;
		}
	}
	
	// Método para retornar o tabuleiro para sua cor original
	public void update(){
		for(int i = 0; i < width * height ; i++){	// Varre todos os pixels
			if(boardSkin.getPixels()[i] == 16748423){			
				boardSkin.setPixels(-384239, i);	// Restaura a cor vermelha
			}
			if(boardSkin.getPixels()[i] == 7713501){				
				boardSkin.setPixels(-16743969, i);	// Restaura a cor azul
			}
			if(boardSkin.getPixels()[i] == 16775047){				
				boardSkin.setPixels(-3804, i);		// Restaura a cor amarela
			}
			if(boardSkin.getPixels()[i] == 8309126){				
				boardSkin.setPixels(-13251771, i);	// Restaura a cor verde
			}
		}
	}
}
