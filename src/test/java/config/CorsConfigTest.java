package config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bol.kalaha.KalahaApplication;
import com.bol.kalaha.config.Constants;

import io.undertow.util.Headers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = KalahaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"management.server.port=0" })
public class CorsConfigTest {

	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();

	@LocalServerPort
	private int port;

	@Test
	public void checkCorsHeader() throws JSONException {
		headers.put(Headers.ORIGIN_STRING, Arrays.asList(Constants.FRONTEND_ORIGIN));

		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/"), HttpMethod.POST, entity,
				String.class);
		System.out.println(response.getHeaders());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

	}

	@Test
	public void checkCorsHeaderForSwagger() throws JSONException {
		headers.put(Headers.ORIGIN_STRING, Arrays.asList(Constants.FRONTEND_ORIGIN));

		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/v2/api-docs?group=private-api"),
				HttpMethod.GET, entity, String.class);

		System.out.println(response.getHeaders());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
