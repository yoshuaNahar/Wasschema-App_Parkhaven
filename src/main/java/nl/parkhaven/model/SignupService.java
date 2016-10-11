package nl.parkhaven.model;

import nl.parkhaven.model.abstraction.CrudDao;
import nl.parkhaven.model.entity.User;

public final class SignupService {

	private CrudDao<User> userDao;
	private User user;
	private String code;
	private String errorMessage;
	private Long memberId;

	public void signup(User user, String code) {
		this.user = user;
		this.code = code;
		if (credentialsValid()) {
			add();
			credentialsMemberSignedup();
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
		userDao.create(user);
	}

	// TODO USER WITH THESE CREDENTIAL ALREADY EXISTS!
	private void credentialsMemberSignedup() {
		if (memberId == null) {
			errorMessage = "User with these credentials already exists!";
		}
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
