package nl.parkhaven.model;

import nl.parkhaven.model.abstraction.CrudDao;
import nl.parkhaven.model.entity.User;

public final class SigninService {

	private CrudDao<User> userDao;
	private User user;
	private String errorMessage;

	public void signin(User user) {
		this.user = user;
		if (credentialsValid()) {
			signinMember();
			checkMemberSignedin();
		}
	}

	public boolean errorOccured() {
		return (errorMessage == null) ? false : true;
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
		user = userDao.read(user);
	}

	private void checkMemberSignedin() {
		if (user.getId() == 0) {
			errorMessage = "Wrong email or Password!";
		}
	}

	public User getSignedinUser() {
		return user;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
