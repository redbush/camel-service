package brian.camel.it;

import static org.apache.camel.component.mock.MockEndpoint.assertIsSatisfied;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.List;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.fasterxml.jackson.databind.ObjectMapper;

import brian.camel.config.CamelServiceApplication;
import brian.camel.domain.CreateUserDto;

@CamelSpringBootTest
@SpringBootTest(classes = CamelServiceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpoints
class UserRouteIT {
	
	@EndpointInject("mock:direct:createUser")
	private MockEndpoint createUserEndpoint;
	
	@Autowired
	private CamelContext camelContext;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void addAndGet() throws Exception {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(APPLICATION_JSON);
		httpHeaders.setAccept(List.of(APPLICATION_JSON));
		CreateUserDto userDto = new CreateUserDto("joeBob");
		createUserEndpoint.expectedMessageCount(1);
		
		ResponseEntity<String> actual = restTemplate.exchange(
				"/users", 
				POST, 
				new HttpEntity<>(objectMapper.writeValueAsString(userDto), httpHeaders), 
				String.class, Map.of());
		
		assertIsSatisfied(camelContext);
		assertEquals(CREATED, actual.getStatusCode());
		assertEquals(null, actual.getBody());
	}

}
