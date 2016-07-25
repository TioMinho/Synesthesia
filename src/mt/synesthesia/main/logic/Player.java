package mt.synesthesia.main.logic;

import java.awt.Graphics;

import mt.synesthesia.main.graphics.*;

public class Player {

	// *****************
	// ****ATRIBUTOS****
	// *****************
	
	private Combinations playerCombinations;
	private String name;
	private int score;
	private boolean missed;
	private Board playerBoard;
	private GUI playerGUI;
	
	// *****************
	// *****MÉTODOS*****
	// *****************
	
	// Construtor Padrão
	public Player() {
		
		// Inicializa a lista de combinações do jogador
		playerCombinations = new Combinations();
		
		// Inicialização Padrão
		name = "PLAYER";
		
		// Inicializa o score do jogador
		score = 1;
		
		// Inicializa seus erros como false
		missed = false;
		
		// Inicializa o Board do jogador com um valor padrão
		playerBoard = new Board();
		
		// Inicia a GUI do jogador
		playerGUI = new GUI();
		
	}
	
	/*GETTERS*/
	public Combinations getCombinations(){
		return playerCombinations;
	}
	
	public int getScore(){
		return score;
	}
	
	public boolean isMissed(){
		return missed;
	}
	
	public Board getBoard() {
		return playerBoard;
	}
	
	public GUI getGUI() {
		return playerGUI;
	}
	
	public String getName() {
		return name;
	}
	
	/*SETTERS*/
	public void setPlayerCombinations(Combinations playerCombinations) {
		this.playerCombinations = playerCombinations;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	public void setMissed(boolean missed) {
		this.missed = missed;
	}
	
	public void setGUI(GUI playerGUI) {
		this.playerGUI = playerGUI;
	}
	
	public void setBoardSkin(String boardSkin) {
		this.playerBoard.setSkin(boardSkin);;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	// Método para incrementar o número do turno
	public void turnPassed() {
		score++;
	}
	
	// Updeita a GUI
	public void updateGUI(Graphics g, int gameMode, long timerStart){
		playerGUI.update(g, name, score, gameMode, timeElapsed(timerStart));
	}
	
	// Verificar quanto tempo se passou desde que começou o turno do jogador
	public int timeElapsed(long startTime) {
		return (int) ((System.currentTimeMillis() - startTime) / 1000);
	}
	
}
