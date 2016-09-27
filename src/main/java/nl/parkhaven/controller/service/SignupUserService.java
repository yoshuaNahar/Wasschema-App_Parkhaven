package nl.parkhaven.controller.service;

import nl.parkhaven.model.UserDaoImpl;
import nl.parkhaven.model.abstraction.UserDao;
import nl.parkhaven.model.entity.User;

public class SignupUserService {

	private UserDao userDao = new UserDaoImpl();
	private User user;
	private String code;
	private String signinErrorMessage;
	private Long memberId;

	public SignupUserService(User member, String code) {
		this.user = member;
		this.code = code;
	}

	public boolean validEntry() {
		if (user.getEmail() != null && user.getHuisnummer() != null && user.getWachtwoord() != null && code != null) {
			String password = "3024NH";
			if (code.equals(password)) {
				return true;
			} else {
				signinErrorMessage = "Shared password incorrect! It is visible in the laundryroom!";
				return false;
			}
		} else {
			signinErrorMessage = "Not all data entered!";
			return false;
		}
	}

	// TODO USER WITH THESE CREDENTIAL ALREADY EXISTS!
	public boolean correctCredentials() {
		if (memberId != null) {
			return true;
		} else {
			signinErrorMessage = "User with these credentials already exists!";
			return false;
		}
	}

	public void addMember() {
		user = userDao.signup(user);
	}

	public String getSigninErrorMessage() {
		return signinErrorMessage;
	}
}
