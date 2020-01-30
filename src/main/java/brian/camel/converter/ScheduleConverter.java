package brian.camel.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.Converter;

import brian.camel.domain.JobConfirmation;
import brian.camel.domain.JobConfirmationDto;
import brian.camel.domain.JobConfirmations;
import brian.camel.domain.JobConfirmationsDto;
import brian.camel.domain.JobReqDto;
import brian.camel.domain.JobRespDto;
import brian.camel.domain.ParseQueryRequestDto;
import brian.camel.domain.ParseQueryResponseDto;
import brian.camel.domain.QueryAuthReqDto;
import brian.camel.domain.Schedule;
import brian.camel.domain.Schedules;
import brian.camel.domain.Schedules.SchedulesBuilder;
import brian.camel.domain.SchedulesDto;

@Converter
public class ScheduleConverter {

	@Converter
	public Schedules convert(final SchedulesDto schedulesDto) {
		
		SchedulesBuilder schedulesBuilder = Schedules.builder();
		schedulesDto.getSchedules().forEach(scheduleDto -> {
			Schedule schedule = Schedule
				.builder()
				.query(scheduleDto.getQuery())
				.schedule(scheduleDto.getSchedule())
				.build();
			schedulesBuilder.schedule(schedule);
		});
	
		return schedulesBuilder.build();
	}
	
	@Converter
	public ParseQueryRequestDto convert(final Schedule schedule) {
		
		return new ParseQueryRequestDto(schedule.getQuery());
	}
	
	@Converter
	public QueryAuthReqDto convert(final ParseQueryResponseDto parseResponse) {
		
		return new QueryAuthReqDto(parseResponse.getTables());
	}
	
	@Converter
	public JobReqDto convertToJob(final Schedule schedule) {
		
		return new JobReqDto(schedule.getSchedule(), schedule.getQuery());
	}
	
	@Converter
	public JobConfirmation convert(final JobRespDto jobRespDto) {
		
		return JobConfirmation
				.builder()
				.jobId(jobRespDto.getJobId())
				.build();
	}
	
	@Converter
	public JobConfirmationsDto convert(final JobConfirmations jobConfirmations) {
		
		List<JobConfirmationDto> confirmationDtos = jobConfirmations.getConfirmations().stream().map(jobConfirmation -> {
			return new JobConfirmationDto(jobConfirmation.getJobId());
		}).collect(Collectors.toList());
		
		return new JobConfirmationsDto(confirmationDtos);
	}
	
}
