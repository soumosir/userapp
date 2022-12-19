package com.soumosir.userapp;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserappApplicationTests {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";
	private static RestTemplate restTemplate;

	@Autowired
	private TestUserRepository testUserRepository;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@AfterEach
	public void tearDown(){
		testUserRepository.deleteAll();
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/userapp/users");
	}
	@Test
	public void testAddUser() {
		String jsonBody = "{\"name\":\"Soumosir Dutta\",\"username\":\"soumo123\",\"dateOfBirth\":\"11/12/1993\",\"address\":\"3412 Tulane Drive, Maryland 20783.\"}";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");

		ResponseEntity response = restTemplate.postForEntity(baseUrl, entity, String.class);
		assertEquals(jsonBody, response.getBody());
		assertEquals(1, testUserRepository.findAll().size());
	}


}
