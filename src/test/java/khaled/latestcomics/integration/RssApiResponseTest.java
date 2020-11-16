package khaled.latestcomics.integration;

import khaled.latestcomics.LatestComicsApplication;
import khaled.latestcomics.util.Constants;
import khaled.latestcomics.util.TestingRestCallsUtil;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Khaled
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LatestComicsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RssApiResponseTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    ;

    @DisplayName("make sure response is returned as JSON")
    @Test
    public void AssertRequestReturnsJSONData() {
        headers = new HttpHeaders();
        String jsonMimeType = MediaType.APPLICATION_JSON_UTF8_VALUE;
        ResponseEntity<String> response = TestingRestCallsUtil
                .executeGetRequest(headers, restTemplate, Constants.RSS_TO_JSON_API_URL);
        assertNotEquals(null, response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        MediaType mimeType = response.getHeaders().getContentType();

        assertThat(jsonMimeType, Is.is(equalToIgnoringCase(mimeType.toString())));
    }
}
