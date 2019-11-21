package br.gov.mg.bdmg.pocservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FSControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	//@Test
	public void getHello() throws Exception {

		ResponseEntity<String> response = restTemplate
				.getForEntity(new URL("http://localhost:" + port + "/").toString(), String.class);
		assertEquals("Hello JUnit 5", response.getBody());
	}

}
