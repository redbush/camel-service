package brian.camel.route;

import static org.apache.camel.model.dataformat.JsonLibrary.Jackson;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.springframework.stereotype.Component;

import brian.camel.aggregator.ConfirmationAggregator;
import brian.camel.domain.JobConfirmation;
import brian.camel.domain.JobConfirmationsDto;
import brian.camel.domain.JobReqDto;
import brian.camel.domain.JobRespDto;
import brian.camel.domain.ParseQueryRequestDto;
import brian.camel.domain.ParseQueryResponseDto;
import brian.camel.domain.QueryAuthReqDto;
import brian.camel.domain.QueryAuthRespDto;
import brian.camel.domain.Schedule;
import brian.camel.domain.Schedules;
import brian.camel.domain.SchedulesDto;
import brian.camel.processor.ValidationErrorResponse;

@Component
public class ScheduleRoute extends RouteBuilder {

	private final ScheduleRouter scheduleRouter;
	
	public ScheduleRoute(final ScheduleRouter scheduleRouterIn) {
		scheduleRouter = scheduleRouterIn;
	}
	
	@Override
	public void configure() throws Exception {
		
		onException(BeanValidationException.class)
			.handled(true)
			.process(new ValidationErrorResponse());
		
		rest("/schedule")
			.clientRequestValidation(true)
			.produces("application/json")
			.consumes("application/json")
			.post()
				.type(SchedulesDto.class)
				.to("direct:scheduleSplitter");
		
		from("direct:scheduleSplitter")
			.to("bean-validator:schedules")
			.convertBodyTo(Schedules.class)
			.split(simple("${body.schedules}"))
				.aggregationStrategy(new ConfirmationAggregator())
				.parallelProcessing()
				.parallelAggregate()
				.to("direct:schedule")
			.end()
				.convertBodyTo(JobConfirmationsDto.class);
		
		from("direct:schedule")
			.process((exchange) -> {
				exchange.setProperty("SCHEDULE", exchange.getIn().getBody(Schedule.class));
			})
			.bean(scheduleRouter);
		
		from("direct:parse")
			.routeId("parse")
			.convertBodyTo(ParseQueryRequestDto.class)
			.circuitBreaker()
				.hystrixConfiguration()
					.executionTimeoutInMilliseconds(1000)
				.end()
				.to("rest:post:parse?host=localhost:8080&bridgeEndpoint=true")
			.onFallback()
				.process((exchange) -> {
					throw new Exception("timeoutError");
				})
			.end()
				.unmarshal().json(Jackson, ParseQueryResponseDto.class);
		
		from("direct:authorize")
			.routeId("authorize")
			.convertBodyTo(QueryAuthReqDto.class)
			.to("rest:post:authorize?host=localhost:8080&bridgeEndpoint=true")
			.unmarshal().json(Jackson, QueryAuthRespDto.class);
		
		from("direct:unauthorized")
			.routeId("unauthorized")
			.process((exchange) -> {
				throw new Exception("authError");
			});
		
		from("direct:submit")
			.routeId("submit")
			.convertBodyTo(JobReqDto.class)
			.to("rest:post:submit?host=localhost:8080&bridgeEndpoint=true")
			.unmarshal().json(Jackson, JobRespDto.class)
			.convertBodyTo(JobConfirmation.class);

	}

}
