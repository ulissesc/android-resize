package br.com.constantini;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class AndroidResizer {
	
	private String type = "png";
	private String outFolder = "";
	private List<ImageGenerated> generatedImages = new ArrayList<AndroidResizer.ImageGenerated>();
	
	/**
	 * <b>Gera as novas images a partir da imagem XHDPI.</b>
	 * 
	 * Ou seja, se for passado uma imagem com 96x96, ser‡ gerado: 
	 * <ul>
	 * 	<li>LDPI - 36x36</li>
	 * 	<li>MDPI - 48x48</li>
	 * 	<li>HDPI - 72x72</li>
	 * 	<li>XDPI - 96x96</li>
	 *</ul>
	 * 
	 * @param originalImage
	 * @return
	 */
	public List<ImageGenerated> gerenateAndroidImages(BufferedImage originalImage, String name){
		
		final int width = originalImage.getWidth();
		final int height = originalImage.getHeight();
		
		/* limpa a lista */
		generatedImages.clear();
		
		for(Dimensions dimension : Dimensions.values()){
			
			int newWidth  = (int) ( (width * dimension.percentualSize) / Dimensions.XHDPI.percentualSize );
			int newHeight = (int) ( (height * dimension.percentualSize ) / Dimensions.XHDPI.percentualSize );
			
			
			boolean preserveAlpha = "png".equalsIgnoreCase(type);
			BufferedImage img = Resizer.createResizedCopy(originalImage, newWidth, newHeight, preserveAlpha);
			
			generatedImages.add(new ImageGenerated(
							img, 
							name+"_"+dimension.name()+"."+getType(),
							dimension,
							img.getWidth(),
							img.getHeight()
							));
		}
		
		
		return generatedImages;
	}
	
	
	
	public void salvar() throws IOException{
		for (ImageGenerated imageGenerated : generatedImages) {
			final String outFileName = (outFolder + imageGenerated.getName());
			System.out.println("Salvando " + outFileName );
			ImageIO.write(imageGenerated.getImage(), type,  new File(outFileName));
		}
	}
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setOutFolder(String outFolder) {
		this.outFolder = outFolder;
	}

	public String getOutFolder() {
		return outFolder;
	}
	
	
	
	public class ImageGenerated {
		
		private BufferedImage image;
		private String name;
		private Dimensions androidDimension;
		private int width;
		private int height;
		
		public ImageGenerated(BufferedImage image, String name, Dimensions androidDimension, int width, int height) {
			this.image = image;
			this.name = name;
			this.androidDimension = androidDimension;
			this.width = width;
			this.height = height;
		}

		public BufferedImage getImage() {
			return image;
		}
		
		public String getName() {
			return name;
		}
		
		public Dimensions getAndroidDimension() {
			return androidDimension;
		}
		
		public int getWidth() {
			return width;
		}
		
		public int getHeight() {
			return height;
		}
	}
	
	public enum Dimensions {
		
		LDPI(0.75),
		MDPI(1.0),
		HDPI(1.5),
		XHDPI(2.0);
		
		private Dimensions(double percentualSize){
			this.percentualSize = percentualSize;
		}
		
		/** Percentual do tamanho para cada tipo de icone */
		public final double percentualSize;

	}
}

