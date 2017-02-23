package nl.parkhaven.wasschema.modules.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	private UserDaoImpl userDao = new UserDaoImpl();
	private String errorMessage;

	public User login(User user) {
		if (credentialsNotEmpty(user)) {
			user = userDao.read(user);
			checkMemberSignedin(user);
			return user;
		} else {
			return null;
		}
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public int[] getWashCounter(User user) {
		return userDao.getWashCounter(user);
	}

	private boolean credentialsNotEmpty(User user) {
		if (user.getEmail() == null || user.getPassword() == null) {
			errorMessage = "No email or password entered!";
			logger.warn("Required check bypassed = Email: " + user.getEmail() + " Password: " + user.getPassword());
			return false;
		}
		return true;
	}

	private void checkMemberSignedin(User user) {
		if (user == null) {
			errorMessage = "Wrong email or Password!";
			logger.warn("Wrong email or password.");
		}
	}

}
