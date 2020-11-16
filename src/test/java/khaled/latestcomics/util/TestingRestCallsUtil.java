package khaled.latestcomics.util;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * @author Khaled
 */
public class TestingRestCallsUtil {
    public static ResponseEntity<String> executeGetRequest(HttpHeaders headers, TestRestTemplate restTemplate, String url) {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        return restTemplate.exchange(
                url,
                HttpMethod.GET, entity, String.class);
    }
}
