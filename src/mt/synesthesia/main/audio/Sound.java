package mt.synesthesia.main.audio;

import javax.sound.sampled.*;

public class Sound {

	// *****************
	// ****ATRIBUTOS****
	// *****************

	private Clip clip;
	private boolean played = false;
	private boolean loop = true;
	
	// *****************
	// *****M�TODOS*****
	// *****************

	// Construtor Personalizado (E mais importante)
	public Sound(String path) {

		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(getClass().getResourceAsStream(path));

			AudioFormat baseFormat = audioInputStream.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			AudioInputStream decodedAIS = AudioSystem.getAudioInputStream(
					decodeFormat, audioInputStream);

			clip = AudioSystem.getClip();
			clip.open(decodedAIS);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/* GETTERS */
	public Clip getClip(){
		return clip;
	}
	
	public boolean isPlayed(){
		return played;
	}
	
	// M�todo para executar um clipe de �udio
	public void play() {
		if(clip == null)
			return;
		
		if(!clip.isRunning() && loop)
			played = false;
		
		if(!played){
			stop();
			clip.setFramePosition(0);
			clip.start();
			played = true;
		}
	}
	
	// M�todo para parar a execu��o
	public void stop() {
		if (clip.isRunning())
			clip.stop();
	}
	
	// M�todo para fechar o clipe de �udio
	public void close() {
		stop();
		clip.close();
	}
	
	// Diz se o �udio vai ou n�o tocar em loop
	public void setLoop(boolean isRepeating){
		loop = isRepeating;
	}

}
