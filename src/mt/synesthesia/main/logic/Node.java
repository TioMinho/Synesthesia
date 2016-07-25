package mt.synesthesia.main.logic;

public class Node {

	// *****************
	// ****ATRIBUTOS****
	// *****************
	
	// A cor que cada combinação adiciona
	// 1 - Vermelho, 2 - Azul, 3 - Amarelo, 4 - Green
	private int color; 
	
	private Node next; 			// Ponteiro para a próxima cor
	private Node previous;		// Ponteiro para a cor anterior
	
	// *****************
	// *****MÉTODOS*****		
	// *****************
	
	//Contrutor Padrão
	public Node(int color) {
		
		this.color = color;
		
		next = null;
		previous = null;
		
	}
	
	/* GETTERS */
	public int getColor(){
		return color;
	}
	
	public Node getNext(){
		return next;
	}

	public Node getPrevious(){
		return previous;
	}
	
	/* SETTERS */
	public void setColor(int color){
		this.color = color;
	}
	
	public void setNext(Node next){
		this.next = next;
	}
	
	public void setPrevious(Node previous){
		this.previous = previous;
	}
	
}
