package br.com.constantini;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Test;

import br.com.constantini.AndroidResizer.Dimensions;
import br.com.constantini.AndroidResizer.ImageGenerated;

public class TestResizer {
	
	
	@Test
	public void resizePNG() throws IOException{
		
		BufferedImage image = ImageIO.read(new File("SoccerDukeSmall.png"));
		BufferedImage resizedCopy = Resizer.createResizedCopy(image, image.getWidth() / 2, image.getHeight() / 2, true);
		
		File outImage = new File("SoccerDukeSmall_resized.png");
		
		ImageIO.write(resizedCopy, "png", outImage);
		
	}
	
	
	@Test
	public void resizeAndroid() throws IOException{
		
//		String originalImageName = "circle_green.png";
		String originalImageName = "SoccerDukeSmall.png";
		
		File file = new File( originalImageName );
		BufferedImage image = ImageIO.read(file);
		
		AndroidResizer androidResizer = new AndroidResizer();
		//androidResizer.setType("jpg");
		String fileName = file.getName();
		
		int lastIndexOf = fileName.lastIndexOf(".");
		if (lastIndexOf >= 0){
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		List<ImageGenerated> gerenateAndroidImages = androidResizer.gerenateAndroidImages(image, fileName);
		
		/* SALVAR */
		androidResizer.salvar();
		
		/* VALIDA TAMANHO DAS IMAGES */
		for (ImageGenerated imageGenerated : gerenateAndroidImages) {
			
			final int width  = imageGenerated.getImage().getWidth();
			final int height = imageGenerated.getImage().getHeight();
			
			if (imageGenerated.getAndroidDimension() == Dimensions.XHDPI){
				Assert.assertEquals(593, width);
				Assert.assertEquals(474, height);
			} else if(imageGenerated.getAndroidDimension() == Dimensions.HDPI){
				Assert.assertEquals(444, width);
				Assert.assertEquals(355, height);
			} else if(imageGenerated.getAndroidDimension() == Dimensions.MDPI){
				Assert.assertEquals(296, width);
				Assert.assertEquals(237, height);
			} else if(imageGenerated.getAndroidDimension() == Dimensions.LDPI){
				Assert.assertEquals(222, width);
				Assert.assertEquals(177, height);
			}
		}
		
	}

}
