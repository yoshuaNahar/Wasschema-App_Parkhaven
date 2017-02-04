package nl.parkhaven.wasschema.model.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LoginService {

	private static final Logger logger = LogManager.getLogger(LoginService.class);

	private UserDaoImpl userDao;
	private User user;
	private String errorMessage;

	public void login(User user) {
		this.user = user;
		if (credentialsNotEmpty()) {
			signinMember();
			checkMemberSignedin();
		}
	}

	public boolean errorOccured() {
		return errorMessage != null;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public User getLoggedinUser() {
		return user;
	}

	private boolean credentialsNotEmpty() {
		if (user.getEmail() == null  || user.getWachtwoord() == null) {
			errorMessage = "No email or password entered!";
			logger.warn("= required check bypassed = Email: " + user.getEmail() + " Password: " + user.getWachtwoord());
			return false;
		}
		return true;
	}

	private void signinMember() {
		userDao = new UserDaoImpl();
		user = userDao.read(user);
	}

	private void checkMemberSignedin() {
		if (user.getId() == 0) {
			errorMessage = "Wrong email or Password!";
			logger.warn("Wrong email or password. Email: " + user.getEmail() + " Password: " + user.getWachtwoord());
		}
	}

}
