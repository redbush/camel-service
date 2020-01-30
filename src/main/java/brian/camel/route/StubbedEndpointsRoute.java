package brian.camel.route;

import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import brian.camel.domain.ParseQueryRequestDto;
import brian.camel.domain.QueryAuthReqDto;

@Component
public class StubbedEndpointsRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		rest("/parse")
			.clientRequestValidation(true)
			.consumes("application/json")
			.produces("application/json")
			.post()
				.type(ParseQueryRequestDto.class)
				.to("direct:stubbedParse");
		
		rest("/authorize")
			.clientRequestValidation(true)
			.consumes("application/json")
			.produces("application/json")
			.post()
				.type(QueryAuthReqDto.class)
				.to("direct:stubbedAuth");
		
		rest("/submit")
			.clientRequestValidation(true)
			.consumes("application/json")
			.produces("application/json")
			.post()
				.to("direct:stubbedSubmit");
		
		from("direct:stubbedParse")
			.log("Returning stubbed parse response")
			.process((exchange) -> {
				ParseQueryRequestDto parseRequestDto = exchange.getIn().getBody(ParseQueryRequestDto.class);
				if(parseRequestDto.getQuery().contains("DENIED")) {
					exchange.getIn().setBody(Map.of("tables", List.of("DENIED")));
				} else if(parseRequestDto.getQuery().contains("SLOW")) {
					Thread.sleep(5000);
				} else {
					exchange.getIn().setBody(Map.of("tables", List.of("tableA", "tableB")));
				}
				exchange.getIn().setHeader(HTTP_RESPONSE_CODE, 200);
			});
		
		from("direct:stubbedAuth")
			.log("Returning stubbed auth response")
			.process((exchange) -> {
				QueryAuthReqDto authReqDto = exchange.getIn().getBody(QueryAuthReqDto.class);
				if(authReqDto.getTables().contains("DENIED")) {
					exchange.getIn().setBody(Map.of("authorized", "false"));
				} else {
					exchange.getIn().setBody(Map.of("authorized", "true"));
				}
				exchange.getIn().setHeader(HTTP_RESPONSE_CODE, 200);
			});
		
		from("direct:stubbedSubmit")
			.log("Returning stubbed submit response")
			.process((exchange) -> {
				exchange.getIn().setBody(Map.of("jobId", UUID.randomUUID().toString()));
				exchange.getIn().setHeader(HTTP_RESPONSE_CODE, 200);
			});
	}

}
