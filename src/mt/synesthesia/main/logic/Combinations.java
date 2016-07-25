package mt.synesthesia.main.logic;

public class Combinations {	// Deque Duplamente-Encadeado

	// *****************
	// ****ATRIBUTOS****
	// *****************
	
	private Node header;	// Primeiro elemento da combina��o
	private Node trailer;	// �ltimo elemento da combina��o
		
	// *****************
	// *****M�TODOS*****		
	// *****************
	
	// Construtor Padr�o
	public Combinations() {
		
		// Inicializa��o do header e do trailer
		header = null;
		trailer = null;
		
	}
 
	/* GETTERS */
	public Node getHeader(){
		return header;
	}
	
	public Node getTrailer(){
		return trailer;
	}
	
	// Verifica se a fila est� vazia
	public boolean isEmpty(){
		if(header == null)
			return true;
		else
			return false;
	}
	
	// Adiciona um elemento no in�cio da combina��o
	public void addInicio(){
		// Vari�vel para tratar n�meros aleat�rios
		java.util.Random elemento = new java.util.Random();
		
		// N� tempor�rio com valor aleat�rio de 1 a 4
		Node temp = new Node(elemento.nextInt(4) + 1);
		
		if(isEmpty()){	
			header = temp;				// O header recebe o n� tempor�rio
			trailer = header;			// O trailer e o header s�o na mesma posi��o
		}
		else{
			header.setPrevious(temp);	// Posiciona o n� tempor�rio atr�s do header
			temp.setNext(header);		// Liga o n� tempor�rio com o header
			
			header = temp;				// Move a refer�ncia do header para o tempor�rio
		}
	}
	
	// Adiciona um elemento especif�co
	public void addInicio(int color){
		
		// N� tempor�rio com valor aleat�rio de 1 a 4
		Node temp = new Node(color);
		
		if(isEmpty()){	
			header = temp;				// O header recebe o n� tempor�rio
			trailer = header;			// O trailer e o header s�o na mesma posi��o
		}
		else{
			temp.setNext(header);		// Liga o n� tempor�rio com o header
			header.setPrevious(temp);	// Posiciona o n� tempor�rio atr�s do header
			
			header = temp;				// Move a refer�ncia do header para o tempor�rio
		}
	}
	
	// Adiciona um elemento no final da combina��o
	public void addFinal(){
		// Vari�vel para tratar n�meros aleat�rios
		java.util.Random elemento = new java.util.Random();
		
		// N� tempor�rio com valor aleat�rio de 1 a 4
		Node temp = new Node(elemento.nextInt(4) + 1);
		
		if(isEmpty()){	
			header = temp;				// O header recebe o n� tempor�rio
			trailer = header;			// O trailer e o header s�o na mesma posi��o
		}
		else{
			temp.setPrevious(trailer);	// Posiciona o trailer atr�s no novo n�
			trailer.setNext(temp);		// Posiciona o novo n� ap�s o trailer
			
			trailer = temp;				// Direciona a refer�ncia do trailer para o novo n�
		}
	}
	
	// Adiciona um elemento no final da combina��o
	public void addFinal(int color){
		
		// N� tempor�rio com valor aleat�rio de 1 a 4
		Node temp = new Node(color);
		
		if(isEmpty()){	
			header = temp;				// O header recebe o n� tempor�rio
			trailer = header;			// O trailer e o header s�o na mesma posi��o
		}
		else{
			temp.setPrevious(trailer);	// Posiciona o trailer atr�s no novo n�
			trailer.setNext(temp);		// Posiciona o novo n� ap�s o trailer
								
			trailer = temp;				// Direciona a refer�ncia do trailer para o novo n�
		}
	}
	
	// Remove um elemento do inicio da combina��o
	public Node removerInicio(){
		if(isEmpty()){
			System.out.println("Lista vazia!");	// Avisa que a lista t� vazia
			return null;						// Retorna shit nenhuma
		}
		if(header.getNext() == null){
			Node removido = header;				// Cria uma referer�ncia para retornar o header depois de removido
			
			header = null;
			trailer = null;
			
			return removido;
			
		}
		else{
			Node removido = header;				// Cria uma referer�ncia para retornar o header depois de removido
			
			header = header.getNext();			// Referencia o header como o pr�ximo elemento
			header.setPrevious(null);			// Retira a refer�ncia do elemento agora anterior ao header
			
			return removido;					// Retorna o antigo header
		}
	}
	
	// Remove um elemento do final da lista
	public Node removerFinal(){
		if(isEmpty()){
			System.out.println("Lista vazia!");		// Avisa que a lista t� vazia
			return null;							// Retorna shit nenhuma
		}
		if(trailer.getPrevious() == null){
			Node removido = trailer;				// Cria uma referer�ncia para retornar o header depois de removido
			
			header = null;
			trailer = null;
			
			return removido;
			
		}
		else{
			Node removido = trailer;				// Cria uma referer�ncia para retornar o header depois de removido
			
			trailer = trailer.getPrevious();		// Referencia o header como o pr�ximo elemento
			trailer.setNext(null);					// Retira a refer�ncia do pr�ximo elemento ao header
					
			return removido;						// Retorna o antigo header
		}
	}
	
}
