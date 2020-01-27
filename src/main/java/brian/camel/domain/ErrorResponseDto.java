package brian.camel.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
public class ErrorResponseDto {

	private final String message;
	
	@JsonCreator
	public ErrorResponseDto(@JsonProperty("message") final String messageIn) {
		message = messageIn;
	}
	
}
