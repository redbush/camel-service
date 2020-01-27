package brian.camel.datasource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import brian.camel.domain.User;

@Component
public class InMemoryUserDataSource implements UserDataSource {

	private final Map<String, User> users = new ConcurrentHashMap<>();

	@Override
	public void create(final User user) {
		
		users.merge(user.getName(), user, (existingUser, newUser) -> {
			
			existingUser.merge(newUser);
			return existingUser;
		});
	}

	@Override
	public User get(final String name) throws UserNotFoundException {
		
		User user = users.get(name);
		if(user == null) {
			throw new UserNotFoundException();
		}
		return user;
	}
	
}
