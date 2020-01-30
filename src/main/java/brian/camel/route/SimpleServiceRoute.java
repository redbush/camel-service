package brian.camel.route;

import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.springframework.stereotype.Component;

import brian.camel.domain.User;
import brian.camel.domain.UserDto;
import brian.camel.processor.ValidationErrorResponse;
import brian.camel.service.UserService;

@Component
public class SimpleServiceRoute extends RouteBuilder {

	private final UserService userService;
	
	public SimpleServiceRoute(final UserService userServiceIn) {
		userService = userServiceIn;
	}
	
	@Override
	public void configure() throws Exception {
		
		onException(BeanValidationException.class)
			.handled(true)
			.process(new ValidationErrorResponse());
		
		rest("/users")
			.consumes("application/json")
			.produces("application/json")
			.clientRequestValidation(true)
			.post("/create")
				.type(UserDto.class)
				.to("direct:createUser");
		
		from("direct:createUser")
			.to("bean-validator:user")
			.convertBodyTo(User.class)
			.bean(userService)
			.setHeader(HTTP_RESPONSE_CODE, constant(201))
			.transform().constant(null);
				
	}
	
}
