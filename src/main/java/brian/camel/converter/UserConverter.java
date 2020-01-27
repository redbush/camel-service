package brian.camel.converter;

import org.apache.camel.Converter;

import brian.camel.domain.User;
import brian.camel.domain.UserDto;
import brian.camel.domain.CreateUserDto;

@Converter
public class UserConverter {

	@Converter
	public User convert(final CreateUserDto userDto) {
		
		return User
				.builder()
				.name(userDto.getName())
				.build();
	}
	
	@Converter
	public UserDto convert(final User user) {
		
		return new UserDto(user.getName(), user.getCreatedAt(), user.getUpdatedAt());
	}
	
}
