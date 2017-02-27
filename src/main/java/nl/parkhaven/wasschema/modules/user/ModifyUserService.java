package nl.parkhaven.wasschema.modules.user;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.parkhaven.wasschema.modules.util.Misc;

@Service
public final class ModifyUserService {

	private static final Logger logger = LoggerFactory.getLogger(ModifyUserService.class);

	private UserDaoImpl userDao;
	private String errorMessage;

	@Autowired
	public ModifyUserService(UserDaoImpl userDao) {
		this.userDao = userDao;
	}

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
