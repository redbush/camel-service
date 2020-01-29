package brian.camel.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class Schedule {

	private final String schedule;
	private final String query;
	
}
