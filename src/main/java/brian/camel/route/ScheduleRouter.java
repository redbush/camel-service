package brian.camel.route;

import static org.apache.camel.Exchange.SLIP_ENDPOINT;

import org.apache.camel.DynamicRouter;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.springframework.stereotype.Component;

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
			return "direct:submit";
		default:
			break;
		}
		
		return null;
	}
	
}
