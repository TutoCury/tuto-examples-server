package com.tuto.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TutoExamplesServerApplication {
	
	private static Logger LOG = LoggerFactory.getLogger(TutoExamplesServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TutoExamplesServerApplication.class, args);
		LOG.info("Let's get started");
	}
}
