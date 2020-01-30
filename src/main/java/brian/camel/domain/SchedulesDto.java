package brian.camel.domain;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class SchedulesDto {

	@Valid
	private final List<ScheduleDto> schedules = new LinkedList<>();
	
	@JsonCreator
	public SchedulesDto(@JsonProperty("schedules") final List<ScheduleDto> schedulesIn) {
		schedules.addAll(schedulesIn);
	}
	
}
