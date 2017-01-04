package nl.parkhaven.wasschema.model.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.wasschema.util.Misc;

public final class SignupService {

	private static final Logger logger = LogManager.getLogger(SignupService.class);

	private UserDaoImpl userDao;
	private User user;
	private String code;
	private String errorMessage;

	public void signup(User user, String code) {
		this.user = user;
		this.code = code;
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
		if (user.getEmail() == null || !Misc.isHuisnummerValid(user.getHuisnummer()) || user.getWachtwoord() == null
				|| code == null) {
			errorMessage = "Not all data entered!";
			logger.warn("Not all required data entered. First name: " + user.getVoornaam() + " - Last Name: "
					+ user.getAchternaam() + " - Email: " + user.getEmail() + " - Huisnummer: " + user.getHuisnummer()
					+ " - Wachtwoord: " + user.getWachtwoord());
			return false;
		} else {
			String password = "3024NH";
			if (code.trim().equalsIgnoreCase(password)) {
				return true;
			} else {
				errorMessage = "Shared password incorrect! It is visible in the laundryroom!";
				logger.warn("Wrong signup code: " + code);
				return false;
			}
		}
	}

	private void addUser() {
		userDao = new UserDaoImpl();
		if (!userDao.create(user)) {
			errorMessage = "User with your huisnummer alread exists! (Contact beheerder)";
			logger.warn("= Huisnummer or email already exists. Huisnummer: " + user.getHuisnummer() + " Email: "
					+ user.getEmail() + " =");
		}
		logger.info("Signed up. Huisnummer: " + user.getHuisnummer() + " - Email: " + user.getEmail());
	}

}
