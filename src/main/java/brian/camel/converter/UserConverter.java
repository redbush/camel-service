package brian.camel.converter;

import org.apache.camel.Converter;

import brian.camel.domain.User;
import brian.camel.domain.UserDto;

@Converter
public class UserConverter {

	@Converter
	public User convert(final UserDto user) {
		
		return User.builder()
				.name(user.getName())
				.build();
	}

}
