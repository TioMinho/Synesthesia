package mt.synesthesia.main.audio;

import java.util.Random;

public class Playlist {

	// *****************
	// ****ATRIBUTOS****
	// *****************
	
	private Sound music;					// Atributo que representa a faixa de áudio
	private Random randomTrack;				// Variável aleatória para reprodução aleatória
	private String playlistFolder;			// Pasta onde se localizam os arquivos de áudio
	
	// *****************
	// *****MÉTODOS*****
	// *****************
	
	// Construtor Padrão
	public Playlist(String playlistFolder) {

		// Inicializa a pasta da playlist
		this.playlistFolder = playlistFolder;
		
		// Inicializa a variável random
		randomTrack = new Random();
		
		// Inicia o objeto Music com uma música padrão (The Pot do Tool)
		music = new Sound("/Music/inGame/0.mp3");
		music.setLoop(true);
	}
	
	// Método principal responsável pela reprodução
	public void play() {
		
		// Permite carregar a próxima música quando a anterior acabar
		if(!music.isPlayed()){		
			// Contrução da String da path
			String track = Integer.toString(randomTrack.nextInt(10));
			String musicPath = "/Music/" + playlistFolder + "/" + track + ".mp3";
			
			// Carregando a música na string acima;
			music = new Sound(musicPath);
			music.setLoop(true);
		}
	
		// Executando a música
		music.play();
		
	}
	
	// Método principal por abortar a execução
	public void stop(){
		music.stop();
	}

}
