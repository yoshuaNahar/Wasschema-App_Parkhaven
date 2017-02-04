package nl.parkhaven.wasschema.model.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.wasschema.util.Misc;

public final class ModifyUserService {

	private static final Logger logger = LogManager.getLogger(ModifyUserService.class);

	private UserDaoImpl userDao;
	private String errorMessage;

	public void changeHousenumber(User user) {
		userDao = new UserDaoImpl();
		if (Misc.isHuisnummerValid(user.getHuisnummer())) {
			if (!userDao.update(user)) {
				errorMessage = "An unknown error has occurred!";
				logger.warn("An unknown error has occured when changing house number!?");
			}
		} else {
			errorMessage = "Not a valid huisnummer!";
			logger.info("User tried to change huisnummer: " + user.getHuisnummer() + " - email: " + user.getEmail());
		}
	}

	public void changePassword(User user) {
		userDao = new UserDaoImpl();
		if (!userDao.updatePassword(user)) {
			errorMessage = "An unknown error has occurred!";
			logger.warn("An unknown error has occured when changing house number!?");
		}
	}

	public void deleteAccount(User user) {
		userDao = new UserDaoImpl();
		if (!userDao.delete(user)) {
			errorMessage = "Password not correct!";
			logger.warn("Email not correct! userID: " + user.getId() + " - userPass: "+ user.getWachtwoord());
		}
	}

	public boolean errorOccured() {
		return errorMessage != null;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
