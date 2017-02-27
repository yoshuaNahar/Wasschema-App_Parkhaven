package nl.parkhaven.wasschema.modules.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.parkhaven.wasschema.modules.util.Misc;

@Service
public final class ForgotPasswordService {

	private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordService.class);

	private UserDaoImpl userDao;
	private String errorMessage;

	@Autowired
	public ForgotPasswordService(UserDaoImpl userDao) {
		this.userDao = userDao;
	}

	public void setRandomPasswordForUser(User user) {
		if (credentialsNotEmpty(user)) {
			user.setPassword(Misc.generateNewPassword());
			changePasswordInDb(user);
		}
	}

	public boolean errorOccured() {
		return errorMessage != null;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	private boolean credentialsNotEmpty(User user) {
		if (user.getEmail() == null) {
			errorMessage = "No email entered!";
			logger.warn("Required check bypassed = Email: " + user.getEmail() + " Password: " + user.getPassword());
			return false;
		}
		return true;
	}

	private void changePasswordInDb(User user) {
		if (userDao.updatePassword(user)) {
			logger.info("User password changed succesfully and email send! - Email: " + user.getEmail());
		} else {
			errorMessage = "No user found with this email address!";
			logger.warn("Change Password (Forgot email) Email not available: " + user.getEmail() + " =");
		}
	}

}
