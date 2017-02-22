package nl.parkhaven.wasschema.modules.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.wasschema.modules.util.Misc;

public final class ForgotPasswordService {

	private static final Logger logger = LogManager.getLogger(ForgotPasswordService.class);

	private User user;
	private String errorMessage;

	public ForgotPasswordService(User user) {
		this.user = user;
	}

	public void setRandomPasswordForUser() {
		if (credentialsNotEmpty()) {
			user.setPassword(Misc.generateNewPassword());
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
			errorMessage = "No email entered!";
			logger.warn("= required check bypassed = Email: " + user.getEmail() + " Password: " + user.getPassword());
			return false;
		}
		return true;
	}

	private void changePasswordInDb() {
		UserDaoImpl userDao = new UserDaoImpl();
		if (userDao.updatePassword(user)) {
			logger.info("User password changed succesfully and email send! - Email: " + user.getEmail());
		} else {
			errorMessage = "No user found with this email address!";
			logger.warn("= Change Password (Forgot email) Email not available: " + user.getEmail() + " =");
		}
	}

}
