package com.example.whenandwhattime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class WhenAndWhatTimeApplication extends SpringBootServletInitializer {

		public static void main(String[] args) {
			SpringApplication.run(WhenAndWhatTimeApplication.class, args);
		}
	
		@Override
		protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		  return application.sources(WhenAndWhatTimeApplication.class);
		}
}
