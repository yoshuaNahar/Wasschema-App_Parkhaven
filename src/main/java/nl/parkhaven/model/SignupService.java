package nl.parkhaven.model;

import nl.parkhaven.model.abstraction.CrudDao;
import nl.parkhaven.model.entity.User;

public final class SignupService {

	private CrudDao<User> userDao;
	private User user;
	private String code;
	private String errorMessage;

	public void signup(User user, String code) {
		this.user = user;
		this.code = code;
		if (credentialsValid()) {
			add();
		}
	}

	public boolean errorOccured() {
		return errorMessage == null ? true : false;
	}

	private boolean credentialsValid() {
		boolean bool = false;
		if (user.getEmail() == null || user.getHuisnummer() == null || user.getWachtwoord() == null || code == null) {
			errorMessage = "Not all data entered!";
		} else {
			String password = "3024NH";
			if (code.equals(password)) {
				bool = true;
			} else {
				errorMessage = "Shared password incorrect! It is visible in the laundryroom!";
			}
		}
		return bool;
	}

	private void add() {
		userDao = new UserDaoImpl();
		if (userDao.create(user)) {
			errorMessage = "User with your huisnummer alread exists! (Contact beheerder)";
		}
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
