package se.ehm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class SurveillanceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SurveillanceApplication.class, args);
	}
}
