package com.tuto.examples;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

	private static Logger LOG = LoggerFactory.getLogger(ImageController.class);
	
	public static final int MIN_WIDTH = 100;
	public static final int MAX_WIDTH = 5000;
	public static final int MIN_HEIGHT = 100;
	public static final int MAX_HEIGHT = 5000;

	public static final int MIN_TEXT = 1;
	
	public static final String PNG = "png";
	public static final String JPG = "jpg";
	
	@Autowired
	private ImageService imageService;

	@RequestMapping(path = "/image/{width}/{height}/{text}.{type}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImageFromText(@PathVariable("width") Integer width, @PathVariable("height") Integer height, 
			@PathVariable("text") String text, @PathVariable("type") String imageType) {
		
		validateRequest(text, width, height, imageType);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		if(PNG.equalsIgnoreCase(imageType)) {
			responseHeaders.setContentType(MediaType.IMAGE_PNG);
		} else {
			responseHeaders.setContentType(MediaType.IMAGE_JPEG);
		}
		byte[] imageContent;
		
		try {
			imageContent = imageService.generateImageByteArray(text, width, height, imageType);
		} catch (IOException e) {
			LOG.error("Erro while trying to generate image byte[]", e);
			throw new InternalServerErrorException();
		}
		
		return new ResponseEntity<byte[]>(imageContent, responseHeaders, HttpStatus.OK);
	}
	
	private void validateRequest(String text, Integer width, Integer height, String imageType) {
		if(text == null || text.length() < MIN_TEXT) {
			throw new NotFoundException();
		}
		
		if(width == null || width < MIN_WIDTH || width > MAX_WIDTH) {
			throw new NotFoundException();
		}
		
		if(height == null || height < MIN_HEIGHT || height > MAX_HEIGHT) {
			throw new NotFoundException();
		}

		if(!PNG.equalsIgnoreCase(imageType) && !JPG.equalsIgnoreCase(imageType)) {
			throw new NotFoundException();
		}
	}
}
