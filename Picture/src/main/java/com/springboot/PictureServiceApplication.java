package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author atom
 * 2019/12/21 1:32
 */
@SpringBootApplication
public class PictureServiceApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(PictureServiceApplication.class, args);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/File/**")
				.addResourceLocations("file:File/");
	}

}
