package nl.parkhaven.wasschema.model.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.wasschema.util.Misc;

public final class ForgotPasswordService {

	private static final Logger logger = LogManager.getLogger(ForgotPasswordService.class);

	private UserDaoImpl userDao;
	private User user;
	private String errorMessage;

	public void forgotPasswordModifyPassword(User user) {
		this.user = user;
		if (credentialsNotEmpty()) {
			user.setWachtwoord(Misc.generateNewPassword());
			changePasswordInDb();
		}
	}

	public boolean errorOccured() {
		return errorMessage != null;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	private boolean credentialsNotEmpty() {
		if (user.getEmail() == null) {
			errorMessage = "No email or password entered!";
			logger.warn("= required check bypassed = Email: " + user.getEmail() + " Password: " + user.getWachtwoord());
			return false;
		}
		return true;
	}

	private void changePasswordInDb() {
		userDao = new UserDaoImpl();
		if (userDao.updatePassword(user)) {
			logger.info("User password changed succesfully and email send! - Email: " + user.getEmail());
		} else {
			errorMessage = "No user found with this email address!";
			logger.warn("= Change Password (Forgot email) Email not available: " + user.getEmail() + " =");
		}
		userDao.releaseResources();
	}

}
