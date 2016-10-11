package nl.parkhaven.model;

import nl.parkhaven.model.abstraction.CrudDao;
import nl.parkhaven.model.entity.User;

public final class SigninService {

	private CrudDao<User> userDao;
	private User user;
	private User signedinUser;
	private String errorMessage;

	public void signin(User user) {
		this.user = user;
		if (credentialsValid()) {
			signinMember();
			checkMemberSignedin();
		}
	}

	public boolean errorOccured() {
		return errorMessage == null ? true : false;
	}

	private boolean credentialsValid() {
		if (user.getEmail() == null || user.getEmail().trim().equals("")
				|| (user.getWachtwoord() == null || user.getWachtwoord().trim().equals(""))) {
			errorMessage = "No email or password entered!";
			return false;
		}
		return true;
	}

	private void signinMember() {
		userDao = new UserDaoImpl();
		userDao.read(user);
	}

	private void checkMemberSignedin() {
		if (signedinUser == null) {
			errorMessage = "Wrong email or Password!";
		}
	}

	public User getSignedinUser() {
		return signedinUser;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
