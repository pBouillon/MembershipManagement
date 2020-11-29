package eu.telecomnancy.membershipmanagement.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Swagger configuration class
 */
@Configuration
@Import(SpringDataRestConfiguration.class)
public class SpringFoxConfig {

    /**
     * Package in which the controllers are defined
     */
    private final static String controllersPackage = "eu.telecomnancy.membershipmanagement.api.controllers";

    /**
     * Expose controllers and endpoints to be displayed by Swagger
     *
     * @return The associated Java Bean
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(controllersPackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * Specify the Swagger UI information do be displayed
     *
     * @return The associated Java Bean
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Membership management REST API",
                "REST API to manage teams, users and their membership",
                "API TOS",
                "about:blank",
                new Contact(
                        "Pierre Bouillon & Victor Varnier",
                        "https://gitlab.telecomnancy.univ-lorraine.fr/sdisapp2021/membership-management",
                        "membership-management@telecomnancy.eu"),
                "License of API",
                "about:blank",
                Collections.emptyList());
    }

}
