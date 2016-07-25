package mt.synesthesia.main.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	
	// *****************
	// ****ATRIBUTOS****
	// *****************

	private String path;			// Localiza��o do arquivo
	private final int width;			// Largura da imagem
	private final int height;		// Altura da imagem
	private int[] pixels;			// Vetor de pixels da imagem
	
	
	// *****************
	// *****M�TODOS*****
	// *****************
	
	// Construtor Personalizado
	public Image(String path, int width, int height){
		
		// Inicializa��o do arquivo e tamanho da imagem
		this.path = path;
		
		this.width = width;
		this.height = height;
		
		// Cria��o do vetor de pixels da imagem
		pixels = new int[ width * height ];
		
		// Carrega a imagem do sprite;
		load();
		
	}
	
	// M�todo para carregar os pixels da imagem
	private void load(){
		try{
			BufferedImage image = ImageIO.read(Image.class.getResource(path));

			image.getRGB(0, 0, width, height, pixels, 0, width);
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/* GETTERS */
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int[] getPixels(){
		return pixels;
	}
	
	/* SETTERS */	
	public void setPixels(int pixelValue, int index){
		pixels[index] = pixelValue;
	}
}
