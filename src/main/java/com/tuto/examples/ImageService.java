package com.tuto.examples;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
	
	private static Logger LOG = LoggerFactory.getLogger(ImageService.class);
	
	@Cacheable("images")
	public byte[] generateImageByteArray(String text, int width, int height, String imgType) throws IOException {
		
		LOG.info("Vai gerar imagem de {} por {} com o texto {} e o formato {}", width, height, text, imgType);
		
		Graphics2D ig2 = null;
		try {
			
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			ig2 = bi.createGraphics();

			// Fundo Cinza claro
			ig2.setColor(Color.lightGray);
			ig2.fillRect(0, 0, width, height);

			// Desenha uma linha envolta
			ig2.setColor(Color.BLACK);
			ig2.drawRect(0, 0, width-1, height-1);

			// Por fim, prepara e desenha o texto
			ig2.setFont(new Font("TimesRoman", Font.BOLD, 20));
			FontMetrics fontMetrics = ig2.getFontMetrics();
			int stringWidth = fontMetrics.stringWidth(text);
			int stringHeight = fontMetrics.getAscent();
			ig2.setPaint(Color.BLACK);
			ig2.drawString(text, (width - stringWidth) / 2, height / 2 + stringHeight / 4);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, imgType.toUpperCase(), baos);

			return baos.toByteArray();

		} finally {
			if(ig2 != null) {
				ig2.dispose();
			}
		}
	}
}
