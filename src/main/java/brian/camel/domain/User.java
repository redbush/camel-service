package brian.camel.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class User {

	private final String name;
	@Builder.Default
	private final LocalDateTime createdAt = LocalDateTime.now();
	@Builder.Default
	private LocalDateTime updatedAt = LocalDateTime.now();
	
	public void merge(final User user) {
		
		updatedAt = LocalDateTime.now();
	}
	
}
