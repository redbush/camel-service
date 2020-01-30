package brian.camel.it;

import static org.apache.camel.component.mock.MockEndpoint.assertIsSatisfied;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.List;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.AdviceWithRouteBuilder;
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
import brian.camel.domain.ScheduleDto;

@CamelSpringBootTest
@SpringBootTest(classes = CamelServiceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpoints
class ScheduleRouteIT {
	
	@EndpointInject("mock:direct:schedule")
	private MockEndpoint scheduleEndpoint;
	
	@EndpointInject("mock:direct:parse")
	private MockEndpoint parseEndpoint;
	
	@EndpointInject("mock:direct:authorize")
	private MockEndpoint authorizeEndpoint;
	
	@EndpointInject("mock:direct:submit")
	private MockEndpoint submitEndpoint;
	
	@Autowired
	private CamelContext camelContext;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void schedule() throws Exception {

		// TODO: update this isn't done and will fail
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(APPLICATION_JSON);
		httpHeaders.setAccept(List.of(APPLICATION_JSON));
		ScheduleDto scheduleDto = new ScheduleDto("schedule", "query");
		scheduleEndpoint.expectedMessageCount(1);
		parseEndpoint.expectedMessageCount(1);
		authorizeEndpoint.expectedMessageCount(1);
		submitEndpoint.expectedMessageCount(1);
		AdviceWithRouteBuilder.adviceWith(camelContext, "parse", adviceRouteBuilder -> {
			adviceRouteBuilder.weaveByToUri("*").replace().to("mock:parseRestEndpoint");
		});
		
		ResponseEntity<String> actual = restTemplate.exchange(
				"/schedule", 
				POST, 
				new HttpEntity<>(objectMapper.writeValueAsString(scheduleDto), httpHeaders), 
				String.class, Map.of());
		
		assertIsSatisfied(camelContext);
		assertEquals(OK, actual.getStatusCode());
		assertEquals(null, actual.getBody());
	}

}
