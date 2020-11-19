package eu.telecomnancy.membershipmanagement.api;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    public static final int DEFAULT_PORT = 8080;

    public static final String DEFAULT_URL = "http://localhost";

    protected RestTemplate restTemplate = new RestTemplate();

    @LocalServerPort
    protected int serverPort = DEFAULT_PORT;

    protected URI getUrlForRoute(String route) throws URISyntaxException {
        return new URI(DEFAULT_URL + ":" + serverPort + route);
    }

}
