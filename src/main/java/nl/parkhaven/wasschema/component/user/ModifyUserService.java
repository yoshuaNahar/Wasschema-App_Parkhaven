package nl.parkhaven.wasschema.component.user;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.wasschema.util.Misc;

public final class ModifyUserService {

	private static final Logger logger = LogManager.getLogger(ModifyUserService.class);

	private UserDaoImpl userDao = new UserDaoImpl();
	private String errorMessage;

	public void changeHouseNumber(User user) {
		if (Misc.isHouseNumberValid(user.getHouseNumber())) {
			if (!userDao.update(user)) {
				errorMessage = "Housenumber already taken!";
				logger.warn("Housenumber already taken. Id:" + user.getId() + " - email: " + user.getEmail());
			}
		} else {
			errorMessage = "Not a valid housenumber!";
			logger.info("User tried to change housenumber: " + user.getHouseNumber() + " - email: " + user.getEmail());
		}
	}

	public void changePassword(User user) {
		userDao.updatePassword(user);
	}

	public void changeEmail(User user) {
		userDao.updateEmail(user);
	}

	public void deleteAccount(User user) {
		userDao.delete(user);
	}

	public Map<Long, User> getAllUsers() {
		return userDao.selectAllUsers();
	}

	public boolean errorOccured() {
		return errorMessage != null;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
