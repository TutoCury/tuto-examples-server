package com.tuto.examples;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TutoExamplesServerApplication.class)
@WebAppConfiguration
public class TutoExamplesServerApplicationTests {

	@Autowired
	private ImageController imageController;
	
	@Test(expected=NotFoundException.class)
	public void testGetImageFromTextSmallWidth() {
		Integer smallWidth = ImageController.MIN_WIDTH - 1;
		Integer goodHeight = ImageController.MIN_HEIGHT + 1;
		
		imageController.getImageFromText(smallWidth, goodHeight, "GoodText", ImageController.PNG);
	}

	@Test(expected=NotFoundException.class)
	public void testGetImageFromTextSmallHeight() {
		Integer smallHeight = ImageController.MIN_WIDTH - 1;
		Integer goodWidth = ImageController.MIN_WIDTH + 1;
		
		imageController.getImageFromText(goodWidth, smallHeight, "GoodText", ImageController.JPG);
	}
}
