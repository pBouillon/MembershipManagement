package eu.telecomnancy.membershipmanagement.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Spring Web API entry-point class
 */
@SpringBootApplication
public class ApiApplication extends SpringBootServletInitializer {

	/**
	 * Spring Web API entry-point
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
