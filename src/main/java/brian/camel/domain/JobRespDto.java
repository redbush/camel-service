package brian.camel.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class JobRespDto {

	private final String jobId;
	
	@JsonCreator
	public JobRespDto(
			@JsonProperty("jobId") final String jobIdIn) {
		
		jobId = jobIdIn;
	}
	
}
