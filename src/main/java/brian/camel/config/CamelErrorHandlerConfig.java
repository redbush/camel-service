package brian.camel.config;

import static brian.camel.route.GlobalErrorRoute.DIRECT_ERROR_ROUTE;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelErrorHandlerConfig {

	@Bean
	public CamelContextConfiguration contextConfiguration() {
		
		return new CamelContextConfiguration() {
			
			@Override
			public void beforeApplicationStart(CamelContext context) {
				
				DeadLetterChannelBuilder deadLetterChannelBuilder = new DeadLetterChannelBuilder();
				deadLetterChannelBuilder.setDeadLetterUri(DIRECT_ERROR_ROUTE);
				deadLetterChannelBuilder.setUseOriginalMessage(true);
				ExtendedCamelContext extendedCamelContext = context.adapt(ExtendedCamelContext.class);
				extendedCamelContext.setErrorHandlerFactory(deadLetterChannelBuilder);
			}

			@Override
			public void afterApplicationStart(CamelContext camelContext) {
				
			}
		};
	}

}
