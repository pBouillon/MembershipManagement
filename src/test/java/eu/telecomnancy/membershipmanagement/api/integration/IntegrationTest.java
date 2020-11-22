package eu.telecomnancy.membershipmanagement.api.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
     * Extract the payload of a response entity and ensure it exists
     *
     * @param responseEntity ResponseEntity from which extract the payload
     * @param <T> Type of the payload to extract
     * @return The extracted payload
     */
    protected <T> T extractPayload(ResponseEntity<T> responseEntity) {
        T payload = responseEntity.getBody();

        assertNotNull(payload);

        return payload;
    }

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
