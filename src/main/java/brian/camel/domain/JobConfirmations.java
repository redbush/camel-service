package brian.camel.domain;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class JobConfirmations {

	private final List<JobConfirmation> confirmations = Collections.synchronizedList(new LinkedList<>());
	
	public void addConfirmation(final JobConfirmation jobConfirmation) {
		confirmations.add(jobConfirmation);
	}
	
}
