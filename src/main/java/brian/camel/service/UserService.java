package brian.camel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import brian.camel.domain.User;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	public void create(final User user) {
		LOGGER.info("Creating user: {}", user);
	}
	
}
