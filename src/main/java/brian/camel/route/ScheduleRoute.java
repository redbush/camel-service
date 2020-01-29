package brian.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import brian.camel.domain.ParseRequestDto;
import brian.camel.domain.Schedule;
import brian.camel.domain.ScheduleDto;

@Component
public class ScheduleRoute extends RouteBuilder {

	private final ScheduleRouter scheduleRouter;
	
	public ScheduleRoute(final ScheduleRouter scheduleRouterIn) {
		scheduleRouter = scheduleRouterIn;
	}
	
	@Override
	public void configure() throws Exception {
		
		rest("/schedule")
			.clientRequestValidation(true)
			.produces("application/json")
			.consumes("application/json")
			.post()
				.type(ScheduleDto.class)
				.to("direct:schedule");
		
		from("direct:schedule")
			.to("bean-validator:schedule")
			.convertBodyTo(Schedule.class)
			.bean(scheduleRouter);
		
		from("direct:parse")
			.routeId("parse")
			.log("Parse query")
			.convertBodyTo(ParseRequestDto.class)
			.to("rest://post:/parse?host=localhost");
		
		from("direct:authorize")
			.routeId("authorize")
			.log("Authorize query");
		
		from("direct:submit")
			.routeId("submit")
			.log("Submit schedule");
	}

}
