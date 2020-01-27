package brian.camel.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class UserDto {

	private final String name;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;
	
	@JsonCreator
	public UserDto(
			@JsonProperty("name") final String nameIn,
			@JsonProperty("createdAt") final LocalDateTime createdAtIn,
			@JsonProperty("updatedAt") final LocalDateTime updatedAtIn) {
		
		name = nameIn;
		createdAt = createdAtIn;
		updatedAt = updatedAtIn;
	}

}
