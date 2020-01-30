package brian.camel.domain;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class JobConfirmationsDto {

	private final List<JobConfirmationDto> confirmations;
	
	@JsonCreator
	public JobConfirmationsDto(
			@JsonProperty("confirmations") final List<JobConfirmationDto> confirmationsIn) {
		confirmations = new LinkedList<>(confirmationsIn);
	}
	
}
