package brian.camel.converter;

import org.apache.camel.Converter;

import brian.camel.domain.ParseRequestDto;
import brian.camel.domain.Schedule;
import brian.camel.domain.ScheduleDto;

@Converter
public class ScheduleConverter {

	@Converter
	public Schedule convert(final ScheduleDto scheduleDto) {
		
		return Schedule
				.builder()
				.schedule(scheduleDto.getSchedule())
				.query(scheduleDto.getQuery())
				.build();
	}
	
	@Converter
	public ParseRequestDto convert(final Schedule schedule) {
		
		return new ParseRequestDto(schedule.getQuery());
	}
	
}
