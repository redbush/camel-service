package brian.camel.datasource;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -2170875576344363064L;

	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(final String message) {
		super(message);
	}
	
	public UserNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public UserNotFoundException(final Throwable cause) {
		super(cause);
	}
	
}
