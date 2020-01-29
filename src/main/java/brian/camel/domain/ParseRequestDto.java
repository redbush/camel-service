package brian.camel.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ParseRequestDto {

	private final String query;
	
	@JsonCreator
	public ParseRequestDto(@JsonProperty("query") final String queryIn) {
		query = queryIn;
	}
	
}
