package nl.parkhaven.wasschema.modules.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.wasschema.modules.util.Misc;

public final class SignupService {

	private static final Logger logger = LogManager.getLogger(SignupService.class);

	private User user;
	private String code;
	private String errorMessage;

	public SignupService(User user, String code) {
		this.user = user;
		this.code = code;
	}

	public void signup() {
		if (credentialsValid()) {
			addUser();
		}
	}

	public boolean errorOccured() {
		return errorMessage != null;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	private boolean credentialsValid() {
		if (user.getEmail() == null || !Misc.isHouseNumberValid(user.getHouseNumber()) || user.getPassword() == null
				|| code == null) {
			errorMessage = "Not all data entered!";
			logger.warn("Not all required data entered. First name: " + user.getFirstName() + " - Last Name: "
					+ user.getLastName() + " - Email: " + user.getEmail() + " - Huisnummer: " + user.getHouseNumber()
					+ " - Wachtwoord: " + user.getPassword());
			return false;
		} else {
			String password = "3024NH";
			if (code.trim().equalsIgnoreCase(password)) {
				user.setSharedPassword(password);
				return true;
			} else {
				errorMessage = "Shared password incorrect! It is visible in the laundryroom!";
				logger.warn("Wrong signup code: " + code);
				return false;
			}
		}
	}

	private void addUser() {
		UserDaoImpl userDao = new UserDaoImpl();
		if (!userDao.create(user)) {
			errorMessage = "User with your huisnummer alread exists! (Contact beheerder)";
			logger.warn("= Huisnummer or email already exists. Huisnummer: " + user.getHouseNumber() + " Email: "
					+ user.getEmail() + " =");
		}
		logger.info("Signed up. Huisnummer: " + user.getHouseNumber() + " - Email: " + user.getEmail());
	}

	// only to be used in tests, to delete dummy accoutns
	public void deleteUser(User user) {
		UserDaoImpl userDao = new UserDaoImpl();
		userDao.deleteCompletely(user);
	}

}
