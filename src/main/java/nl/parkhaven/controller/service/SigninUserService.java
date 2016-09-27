package nl.parkhaven.controller.service;

import nl.parkhaven.model.UserDaoImpl;
import nl.parkhaven.model.abstraction.UserDao;
import nl.parkhaven.model.entity.User;

public class SigninUserService {

	private UserDao userDao = new UserDaoImpl();
	private User user;
	private User signedinUser;
	private String signinErrorMessage;

	public SigninUserService(User member) {
		this.user = member;
	}

	public boolean validEntry() {
		if ((user.getEmail() != null && !user.getEmail().trim().equals(""))
				&& (user.getWachtwoord() != null && !user.getWachtwoord().trim().equals("")))
			return true;
		else {
			signinErrorMessage = "No email or password entered!";
			return false;
		}
	}

	public boolean correctCredentials() {
		if (signedinUser != null)
			return true;
		else {
			signinErrorMessage = "Wrong email or Password!";
			return false;
		}
	}

	public void signinMember() {
		signedinUser = userDao.signin(user);
	}

	public User getSignedinUser() {
		return signedinUser;
	}

	public String getSigninErrorMessage() {
		return signinErrorMessage;
	}
}
