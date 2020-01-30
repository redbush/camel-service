package brian.camel.aggregator;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import brian.camel.domain.JobConfirmation;
import brian.camel.domain.JobConfirmations;

public class ConfirmationAggregator implements AggregationStrategy {

	@Override
	public Exchange aggregate(
			final Exchange oldExchange, final Exchange newExchange) {
		
		Exception exception = newExchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        JobConfirmations confirmations = null;
        if (oldExchange == null) {
        	confirmations = new JobConfirmations();
        	if(exception != null) {
        		confirmations.addConfirmation(JobConfirmation.builder().jobId(exception.getMessage()).build());
        	} else {
        		confirmations.addConfirmation(newExchange.getIn().getBody(JobConfirmation.class));
        	}
            newExchange.getIn().setBody(confirmations);
            return newExchange;
        } else {
        	confirmations = oldExchange.getIn().getBody(JobConfirmations.class);
        	if(exception != null) {
        		confirmations.addConfirmation(JobConfirmation.builder().jobId(exception.getMessage()).build());
        	} else {
        		confirmations.addConfirmation(newExchange.getIn().getBody(JobConfirmation.class));
        	}
            return oldExchange;
        }
	}

}
