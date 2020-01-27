package brian.camel.processor;

import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import brian.camel.domain.ErrorResponseDto;

public class UserNotFoundRepsonse implements Processor {

	@Override
	public void process(final Exchange exchange) throws Exception {
		
		exchange.getIn().setHeader(HTTP_RESPONSE_CODE, 404);
		exchange.getIn().setBody(new ErrorResponseDto("User not found"));
	}

}
