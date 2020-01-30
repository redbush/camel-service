package brian.camel.route;

import static org.apache.camel.Exchange.SLIP_ENDPOINT;

import org.apache.camel.DynamicRouter;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.springframework.stereotype.Component;

import brian.camel.domain.QueryAuthRespDto;
import brian.camel.domain.Schedule;

@Component
public class ScheduleRouter {

	@DynamicRouter
	public String route(final Exchange exchange, @Header(SLIP_ENDPOINT) final String previous) {
		
		if(previous == null) {
			return "direct:parse";
		}
		
		switch (previous) {
		case "direct://parse":
			return "direct:authorize";
		case "direct://authorize":
			QueryAuthRespDto authResponse = exchange.getIn().getBody(QueryAuthRespDto.class);
			if(!authResponse.isAuthorized()) {
				return "direct:unauthorized";
			}
			exchange.getIn().setBody(exchange.getProperty("SCHEDULE", Schedule.class));
			return "direct:submit";
		default:
			break;
		}
		
		return null;
	}
	
}
