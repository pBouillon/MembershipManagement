package eu.telecomnancy.receivers.client.logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class LoggerApplication {

	public static void main(String[] args)  {
		SpringApplication.run(LoggerApplication.class, args);
	}

}
