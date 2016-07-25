package mt.synesthesia.main.logic;

public class Combinations {	// Deque Duplamente-Encadeado

	// *****************
	// ****ATRIBUTOS****
	// *****************
	
	private Node header;	// Primeiro elemento da combinação
	private Node trailer;	// Último elemento da combinação
		
	// *****************
	// *****MÉTODOS*****		
	// *****************
	
	// Construtor Padrão
	public Combinations() {
		
		// Inicialização do header e do trailer
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
	
	// Verifica se a fila está vazia
	public boolean isEmpty(){
		if(header == null)
			return true;
		else
			return false;
	}
	
	// Adiciona um elemento no início da combinação
	public void addInicio(){
		// Variável para tratar números aleatórios
		java.util.Random elemento = new java.util.Random();
		
		// Nó temporário com valor aleatório de 1 a 4
		Node temp = new Node(elemento.nextInt(4) + 1);
		
		if(isEmpty()){	
			header = temp;				// O header recebe o nó temporário
			trailer = header;			// O trailer e o header são na mesma posição
		}
		else{
			header.setPrevious(temp);	// Posiciona o nó temporário atrás do header
			temp.setNext(header);		// Liga o nó temporário com o header
			
			header = temp;				// Move a referência do header para o temporário
		}
	}
	
	// Adiciona um elemento especifíco
	public void addInicio(int color){
		
		// Nó temporário com valor aleatório de 1 a 4
		Node temp = new Node(color);
		
		if(isEmpty()){	
			header = temp;				// O header recebe o nó temporário
			trailer = header;			// O trailer e o header são na mesma posição
		}
		else{
			temp.setNext(header);		// Liga o nó temporário com o header
			header.setPrevious(temp);	// Posiciona o nó temporário atrás do header
			
			header = temp;				// Move a referência do header para o temporário
		}
	}
	
	// Adiciona um elemento no final da combinação
	public void addFinal(){
		// Variável para tratar números aleatórios
		java.util.Random elemento = new java.util.Random();
		
		// Nó temporário com valor aleatório de 1 a 4
		Node temp = new Node(elemento.nextInt(4) + 1);
		
		if(isEmpty()){	
			header = temp;				// O header recebe o nó temporário
			trailer = header;			// O trailer e o header são na mesma posição
		}
		else{
			temp.setPrevious(trailer);	// Posiciona o trailer atrás no novo nó
			trailer.setNext(temp);		// Posiciona o novo nó após o trailer
			
			trailer = temp;				// Direciona a referência do trailer para o novo nó
		}
	}
	
	// Adiciona um elemento no final da combinação
	public void addFinal(int color){
		
		// Nó temporário com valor aleatório de 1 a 4
		Node temp = new Node(color);
		
		if(isEmpty()){	
			header = temp;				// O header recebe o nó temporário
			trailer = header;			// O trailer e o header são na mesma posição
		}
		else{
			temp.setPrevious(trailer);	// Posiciona o trailer atrás no novo nó
			trailer.setNext(temp);		// Posiciona o novo nó após o trailer
								
			trailer = temp;				// Direciona a referência do trailer para o novo nó
		}
	}
	
	// Remove um elemento do inicio da combinação
	public Node removerInicio(){
		if(isEmpty()){
			System.out.println("Lista vazia!");	// Avisa que a lista tá vazia
			return null;						// Retorna shit nenhuma
		}
		if(header.getNext() == null){
			Node removido = header;				// Cria uma refererência para retornar o header depois de removido
			
			header = null;
			trailer = null;
			
			return removido;
			
		}
		else{
			Node removido = header;				// Cria uma refererência para retornar o header depois de removido
			
			header = header.getNext();			// Referencia o header como o próximo elemento
			header.setPrevious(null);			// Retira a referência do elemento agora anterior ao header
			
			return removido;					// Retorna o antigo header
		}
	}
	
	// Remove um elemento do final da lista
	public Node removerFinal(){
		if(isEmpty()){
			System.out.println("Lista vazia!");		// Avisa que a lista tá vazia
			return null;							// Retorna shit nenhuma
		}
		if(trailer.getPrevious() == null){
			Node removido = trailer;				// Cria uma refererência para retornar o header depois de removido
			
			header = null;
			trailer = null;
			
			return removido;
			
		}
		else{
			Node removido = trailer;				// Cria uma refererência para retornar o header depois de removido
			
			trailer = trailer.getPrevious();		// Referencia o header como o próximo elemento
			trailer.setNext(null);					// Retira a referência do próximo elemento ao header
					
			return removido;						// Retorna o antigo header
		}
	}
	
}
