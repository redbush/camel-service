package brian.camel.domain;

import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;

@Builder
@Getter
@EqualsAndHashCode
public class Schedules {

	@Singular
	private final List<Schedule> schedules;
	
}
