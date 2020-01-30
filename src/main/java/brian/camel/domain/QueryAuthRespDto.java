package brian.camel.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class QueryAuthRespDto {

	private final boolean authorized;
	
	@JsonCreator
	public QueryAuthRespDto(@JsonProperty("authorized") final boolean authorizedIn) {
		authorized = authorizedIn;
	}
	
}
