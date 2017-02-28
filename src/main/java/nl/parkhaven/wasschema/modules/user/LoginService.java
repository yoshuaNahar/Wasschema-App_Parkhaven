package nl.parkhaven.wasschema.modules.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.parkhaven.wasschema.modules.util.Misc;

@Service
public class LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	public static final String LOGIN_CREDENTIALS_INVALID = "Invalid email or password entered.";
	public static final String NOT_ALL_REQUIRED_FIELDS_FILLED = "Please fill all required fields.";
	public static final String USER_WITH_HOUSENUMBER_ALREADY_EXISTS = "A user with this housenumber already exists.";
	public static final String INCORRECT_SHARED_PASSWORD = "The shared password is incorrect. It is visible in the laundryroom.";
	public static final String NO_USER_WITH_EMAIL = "No user found with this email address.";
	private static final String SHARED_PASSWORD = "3024NH";

	private UserDaoImpl userDao;

	@Autowired
	public LoginService(UserDaoImpl userDao) {
		this.userDao = userDao;
	}

	public boolean loginCredentialsValid(User user) {
		if (user.getEmail() == null || user.getPassword() == null) {
			logger.warn("Required JS check = Email: " + user.getEmail() + " Password: " + user.getPassword());
			return false;
		}
		return true;
	}

	public User login(User user) {
		return userDao.read(user);
	}

	public int[] getWashCounter(User user) {
		return userDao.getWashCounter(user);
	}

	public boolean signupCredentialsValid(User user) {
		if (user.getEmail() == null || !Misc.isHouseNumberValid(user.getHouseNumber()) || user.getPassword() == null
				|| user.getSharedPassword() == null) {
			logger.warn("Not all required data entered. First name: " + user.getFirstName() + " - Last Name: "
					+ user.getLastName() + " - Email: " + user.getEmail() + " - Huisnummer: " + user.getHouseNumber()
					+ " - Wachtwoord: " + user.getPassword());
			return false;
		} else {
			if (checkSharedPasswordValid(user.getSharedPassword())) {
				return true;
			} else {
				logger.warn("Wrong signup code: " + user.getSharedPassword());
				return false;
			}
		}
	}

	public boolean checkSharedPasswordValid(String code) {
		if (code.trim().equalsIgnoreCase(SHARED_PASSWORD)) {
			return true;
		}
		return false;
	}

	public boolean created(User user) {
		if (!userDao.create(user)) {
			logger.warn("= Huisnummer or email already exists. Huisnummer: " + user.getHouseNumber() + " Email: "
					+ user.getEmail() + " =");
			return false;
		}
		logger.info("Signed up. Huisnummer: " + user.getHouseNumber() + " - Email: " + user.getEmail());
		return true;
	}

	// only to be used in tests, to delete dummy accoutns
	public void deleteUser(User user) {
		userDao.deleteCompletely(user);
	}

	public void setRandomPasswordFor(User user) {
		user.setPassword(Misc.generateNewPassword());
		changePasswordInDb(user);
	}

	public boolean emailValid(User user) {
		if (user.getEmail() == null) {
			return false;
		}
		return true;
	}

	public void changePasswordInDb(User user) {
		if (userDao.updatePassword(user)) {
			logger.info("User password changed succesfully and email send! - Email: " + user.getEmail());
		} else {
			logger.warn("Change Password (Forgot email) Email not available: " + user.getEmail() + " =");
		}
	}

}
