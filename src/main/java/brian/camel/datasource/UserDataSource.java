package brian.camel.datasource;

import brian.camel.domain.User;

public interface UserDataSource {

	void create(User user);
	
	User get(String name) throws UserNotFoundException;
	
}
