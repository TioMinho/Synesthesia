package mt.synesthesia.main.audio;

import java.util.Random;

public class Playlist {

	// *****************
	// ****ATRIBUTOS****
	// *****************
	
	private Sound music;					// Atributo que representa a faixa de �udio
	private Random randomTrack;				// Vari�vel aleat�ria para reprodu��o aleat�ria
	private String playlistFolder;			// Pasta onde se localizam os arquivos de �udio
	
	// *****************
	// *****M�TODOS*****
	// *****************
	
	// Construtor Padr�o
	public Playlist(String playlistFolder) {

		// Inicializa a pasta da playlist
		this.playlistFolder = playlistFolder;
		
		// Inicializa a vari�vel random
		randomTrack = new Random();
		
		// Inicia o objeto Music com uma m�sica padr�o (The Pot do Tool)
		music = new Sound("/Music/inGame/0.mp3");
		music.setLoop(true);
	}
	
	// M�todo principal respons�vel pela reprodu��o
	public void play() {
		
		// Permite carregar a pr�xima m�sica quando a anterior acabar
		if(!music.isPlayed()){		
			// Contru��o da String da path
			String track = Integer.toString(randomTrack.nextInt(10));
			String musicPath = "/Music/" + playlistFolder + "/" + track + ".mp3";
			
			// Carregando a m�sica na string acima;
			music = new Sound(musicPath);
			music.setLoop(true);
		}
	
		// Executando a m�sica
		music.play();
		
	}
	
	// M�todo principal por abortar a execu��o
	public void stop(){
		music.stop();
	}

}
