package brian.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class GlobalErrorRoute extends RouteBuilder {

	public static final String DIRECT_ERROR_ROUTE = "direct:error";

	private final GlobalErrorHandler errorHandler;
	
	public GlobalErrorRoute(final GlobalErrorHandler errorHandlerIn) {
		errorHandler = errorHandlerIn;
	}
	
	@Override
	public void configure() throws Exception {
		
		from(DIRECT_ERROR_ROUTE)
			.bean(errorHandler);
	}

}
