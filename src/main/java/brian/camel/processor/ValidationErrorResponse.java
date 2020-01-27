package brian.camel.processor;

import static org.apache.camel.Exchange.EXCEPTION_CAUGHT;
import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.bean.validator.BeanValidationException;

import brian.camel.domain.ErrorResponseDto;

public class ValidationErrorResponse implements Processor {

	@Override
	public void process(final Exchange exchange) throws Exception {
		
		BeanValidationException cause = exchange.getProperty(EXCEPTION_CAUGHT, BeanValidationException.class);
		exchange.getIn().setHeader(HTTP_RESPONSE_CODE, 400);
		exchange.getIn().setBody(new ErrorResponseDto(cause.getMessage()));
	}

}
