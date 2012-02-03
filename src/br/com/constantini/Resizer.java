package br.com.constantini;

import java.awt.AlphaComposite;
import java.awt.Image;
import java.awt.image.BufferedImage;

public abstract class Resizer {

	/**
	 * Cria uma copia da imagem com um novo tamanho
	 * @param originalImage
	 * @param scaledWidth
	 * @param scaledHeight
	 * @param preserveAlpha
	 * @return
	 */
	public static BufferedImage createResizedCopy(Image originalImage,
			int scaledWidth, int scaledHeight, boolean preserveAlpha) {
		
		System.out.println("resizing to " + scaledWidth + "x" + scaledHeight);
		
		return resizeAlgoritimo(originalImage, scaledWidth, scaledHeight, preserveAlpha);
	}
	
	
	private static BufferedImage resizeAlgoritimo(Image originalImage,
			int scaledWidth, int scaledHeight, boolean preserveAlpha){
	 
	    // Agora, vamos obter um objeto do tipo imagem novamente, s— que
	    // reduzindo seu tamanho para o tamanho que queremos.
	    Image thumbs = originalImage.getScaledInstance(scaledWidth, scaledHeight, BufferedImage.SCALE_SMOOTH);
	 
	    // J‡ temos nosso thumbs criado em mem—ria. Precisamos salvar esse
	    // thumbs em disco. Para isso, usaremos o objeto BufferedImage
	    int imageType = preserveAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
	    BufferedImage buffer = new BufferedImage(scaledWidth, scaledHeight, imageType);
	 
	    // Desenha a imagem, que foi redimensionada acima, no buffer.
	    buffer.createGraphics().drawImage(thumbs, 0, 0, null);
	    
	    if (preserveAlpha) {
	    	buffer.createGraphics().setComposite(AlphaComposite.Src);
	    }
	 
	    // Faz o "flush" do buffer
	    // buffer.flush();
	    return buffer;
	}
	
}
