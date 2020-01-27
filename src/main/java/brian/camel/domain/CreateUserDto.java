package brian.camel.domain;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class CreateUserDto {

	@NotBlank
	private final String name;
	
	@JsonCreator
	public CreateUserDto(@JsonProperty("name") final String nameIn) {
		name = nameIn;
	}

}
