package eu.telecomnancy.receivers.client.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringApplication.class, args);

		// Print the monitoring context
		System.out.println();
		System.out.println("API Content monitoring:");
		System.out.print("\tWaiting for an operation to occur\r");
	}

}
