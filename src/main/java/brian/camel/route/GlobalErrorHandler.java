package brian.camel.route;

import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

import brian.camel.domain.ErrorResponseDto;

@Component
public class GlobalErrorHandler {
	
	@Handler
	public ErrorResponseDto handle(final Exchange exchange, final Exception cause) {

		exchange.getIn().setHeader(HTTP_RESPONSE_CODE, 500);
		return new ErrorResponseDto(cause.getMessage());
	}
	
}
