package brian.camel.domain;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ScheduleDto {

	@NotBlank
	private final String schedule;
	@NotBlank
	private final String query;
	
	@JsonCreator
	public ScheduleDto(
			@JsonProperty("schedule") final String scheduleIn,
			@JsonProperty("query") final String queryIn) {
		
		schedule = scheduleIn;
		query = queryIn;
	}
	
}
