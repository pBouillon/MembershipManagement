package eu.telecomnancy.membershipmanagement.api.configuration;

import eu.telecomnancy.membershipmanagement.api.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Java Spring configuration, providing Beans for all application's services
 */
@Configuration
public class ServiceConfig {

    /**
     * Create a UserService
     *
     * @return A new instance of the UserService
     */
    @Bean
    public UserService userService() {
        return new UserService();
    }

}
