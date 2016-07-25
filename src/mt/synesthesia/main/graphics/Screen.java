package mt.synesthesia.main.graphics;

public class Screen {

	// *****************
	// ****ATRIBUTOS****
	// *****************
		
	private int width;					// Largura da Tela
	private int height;					// Altura da Tela
	
	private int offsetX, offsetY;		// Defasagem atual da tela
	
	private int[] pixels; 				// Vetor total de pixels da tela
	
	
	// *****************
	// *****MÉTODOS*****
	// *****************
	
	// Construtor Principal
	public Screen(int width, int height){
		
		// Dimensionamento da Tela a partir da janela
		this.width = width;
		this.height = height;
		
		// Cálculo do número de pixels existentes
		pixels = new int[width * height];
				
	}
	
	/*GETTERS*/
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int[] getPixels(){
		return pixels;
	}
	
	// Método de limpar a tela
	public void clear(){
		
		for (int i = 0; i < pixels.length ; i++){
			pixels[i] = 0;
		}
		
	}

	// Método para renderizar um sprite em certa posição
	public void renderImage(int positionX, int positionY, Image image){
		
		// Posiciona a imagem de acordo com a defasagem da tela
		positionX -= offsetX;
		positionY -= offsetY;
			
		// Laço para atribuição de pixels	
		for(int y = 0 ; y < image.getHeight() ; y++){
			
			int absoluteY = y + positionY;	// Calcula a coordenada absoluta no eixo Y da posição especificada
			
			for(int x = 0 ; x < image.getWidth() ; x++){
				
				int absoluteX = x + positionX; // Calcula a coordenada absoluta no eixo X da posição especificada
				
				// Testes para determinar se a posição indicada está adequada ao tamanho da imagem
				if(absoluteX < - image.getWidth() || absoluteX >= width || absoluteY < 0 || absoluteY >= height) 
					break;
				
				if (absoluteX < 0)
					absoluteX = 0;
				
				// Renderização prática da imagem
				pixels[absoluteX + absoluteY * width] = image.getPixels()[x + y * image.getWidth()];
			
			}
		}
	}
	
}

