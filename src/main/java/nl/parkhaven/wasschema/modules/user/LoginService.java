package nl.parkhaven.wasschema.modules.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LoginService {

	private static final Logger logger = LogManager.getLogger(LoginService.class);

	private UserDaoImpl userDao = new UserDaoImpl();
	private User user;
	private String errorMessage;

	public LoginService(User user) {
		this.user = user;
	}

	public void login() {
		if (credentialsNotEmpty()) {
			user = userDao.read(user);
			checkMemberSignedin();
		}
	}

	public boolean errorOccured() {
		return errorMessage != null;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public User getUser() {
		return user;
	}

	public int[] getWashCounter() {
		return userDao.getWashCounter(user);
	}

	private boolean credentialsNotEmpty() {
		if (user.getEmail() == null || user.getPassword() == null) {
			errorMessage = "No email or password entered!";
			logger.warn("= required check bypassed = Email: " + user.getEmail() + " Password: " + user.getPassword());
			return false;
		}
		return true;
	}

	private void checkMemberSignedin() {
		if (user == null) {
			errorMessage = "Wrong email or Password!";
			logger.warn("Wrong email or password.");
		}
	}

}
