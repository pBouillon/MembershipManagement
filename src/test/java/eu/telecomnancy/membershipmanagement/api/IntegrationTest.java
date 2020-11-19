package eu.telecomnancy.membershipmanagement.api;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Base class for integration tests
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    /**
     * Default port of the API
     */
    public static final int DEFAULT_PORT = 8080;

    /**
     * Default URL to access the API
     */
    public static final String DEFAULT_URL = "http://localhost";

    /**
     * Template used to perform integration tests
     */
    protected RestTemplate restTemplate = new RestTemplate();

    /**
     * Port used to perform integration tests
     */
    @LocalServerPort
    protected int serverPort = DEFAULT_PORT;

    /**
     * Get the full URI for the provided route
     *
     * @param route API endpoint route
     * @return The full URI for the route
     * @throws URISyntaxException When the URI is invalid
     */
    protected URI getUrlForRoute(String route) throws URISyntaxException {
        return new URI(DEFAULT_URL + ":" + serverPort + route);
    }

}
