package brian.camel.service;

import org.springframework.stereotype.Service;

import brian.camel.datasource.UserDataSource;
import brian.camel.datasource.UserNotFoundException;
import brian.camel.domain.User;

@Service
public class UserService {
	
	private final UserDataSource userDataSource;
	
	public UserService(final UserDataSource userDataSourceIn) {
		userDataSource = userDataSourceIn;
	}
	
	public void create(final User user) {
		
		userDataSource.create(user);
	}
	
	public User get(final String name) throws UserNotFoundException {
		
		return userDataSource.get(name);
	}
	
}
