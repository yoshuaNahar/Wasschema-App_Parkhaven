package nl.parkhaven.wasschema.modules.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.parkhaven.wasschema.modules.util.Misc;

@Service
public final class SignupService {

	private static final Logger logger = LoggerFactory.getLogger(SignupService.class);

	private UserDaoImpl userDao;
	private String errorMessage;

	@Autowired
	public SignupService(UserDaoImpl userDao) {
		this.userDao = userDao;
	}

	public void signup(User user, String code) {
		if (credentialsValid(user, code)) {
			addUser(user);
		}
	}

	public boolean errorOccured() {
		return errorMessage != null;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	private boolean credentialsValid(User user, String code) {
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

	private void addUser(User user) {
		if (!userDao.create(user)) {
			errorMessage = "User with your huisnummer alread exists! (Contact beheerder)";
			logger.warn("= Huisnummer or email already exists. Huisnummer: " + user.getHouseNumber() + " Email: "
					+ user.getEmail() + " =");
		}
		logger.info("Signed up. Huisnummer: " + user.getHouseNumber() + " - Email: " + user.getEmail());
	}

	// only to be used in tests, to delete dummy accoutns
	public void deleteUser(User user) {
		userDao.deleteCompletely(user);
	}

}
