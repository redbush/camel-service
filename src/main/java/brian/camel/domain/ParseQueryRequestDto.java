package brian.camel.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ParseQueryRequestDto {

	private final String query;
	
	@JsonCreator
	public ParseQueryRequestDto(@JsonProperty("query") final String queryIn) {
		query = queryIn;
	}
	
}
