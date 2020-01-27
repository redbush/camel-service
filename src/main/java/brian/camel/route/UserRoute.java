package brian.camel.route;

import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.springframework.stereotype.Component;

import brian.camel.datasource.UserNotFoundException;
import brian.camel.domain.CreateUserDto;
import brian.camel.domain.User;
import brian.camel.domain.UserDto;
import brian.camel.processor.UserNotFoundRepsonse;
import brian.camel.processor.ValidationErrorResponse;
import brian.camel.service.UserService;

@Component
public class UserRoute extends RouteBuilder {

	private final UserService userService;
	
	public UserRoute(final UserService userServiceIn) {
		userService = userServiceIn;
	}
	
	@Override
	public void configure() throws Exception {
		
		onException(BeanValidationException.class)
			.handled(true)
			.process(new ValidationErrorResponse());
		
		onException(UserNotFoundException.class)
			.handled(true)
			.process(new UserNotFoundRepsonse());
		
		rest("/users")
			.clientRequestValidation(true)
			.produces("application/json")
			.post()
				.consumes("application/json")
				.type(CreateUserDto.class)
				.to("direct:createUser")
			.get("{name}")
				.to("direct:findUser");
		
		from("direct:createUser")
			.to("bean-validator:user")
			.convertBodyTo(User.class)
			.bean(userService, "create")
			.setHeader(HTTP_RESPONSE_CODE, constant(201))
			.setBody(constant(null));
		
		from("direct:findUser")
			.bean(userService, "get(${header.name})")
			.convertBodyTo(UserDto.class);
	}

}
