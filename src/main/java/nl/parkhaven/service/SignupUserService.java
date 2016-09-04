package nl.parkhaven.service;

import nl.parkhaven.dao.SignupUserDAO;
import nl.parkhaven.entity.User;

public class SignupUserService {

	private SignupUserDAO registerDAO = new SignupUserDAO();

	private User user;
	private String code;
	private String signinErrorMessage;

	Long memberId;

	public SignupUserService(User member, String code) {
		this.user = member;
		this.code = code;
	}

	public boolean validEntry() {
		if (user.getEmail() != null && user.getHuisnummer() != null && user.getPassword() != null && code != null) {
			String password = "3024NH";
			if (code.equals(password))
				return true;
			else
				signinErrorMessage = "Shared password incorrect! It is visible in the laundryroom!";
			return false;
		} else {
			signinErrorMessage = "Not all data entered!";
			return false;
		}
	}

	public boolean correctCredentials() {
		if (memberId != null)
			return true;
		else {
			signinErrorMessage = "User with these credentials already exists!";
			return false;
		}
	}

	public void addMember() throws Exception {
		if (registerDAO.getMemberByUserName(user.getEmail()) == null) {
			memberId = registerDAO.insertMember(user);
		}
	}

	public String getSigninErrorMessage() {
		return signinErrorMessage;
	}
}
