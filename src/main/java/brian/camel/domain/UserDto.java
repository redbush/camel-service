package brian.camel.domain;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserDto {

	@NotBlank
	private final String name;
	
	@JsonCreator
	public UserDto(@JsonProperty("name") final String nameIn) {
		name = nameIn;
	}
	
}
