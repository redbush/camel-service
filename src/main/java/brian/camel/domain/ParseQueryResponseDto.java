package brian.camel.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ParseQueryResponseDto {

	private final List<String> tables;
	
	@JsonCreator
	public ParseQueryResponseDto(@JsonProperty("tables") final List<String> tablesIn) {
		tables = tablesIn;
	}
	
}
