package nl.parkhaven.service;

import nl.parkhaven.dao.SigninUserDAO;
import nl.parkhaven.entity.User;

public class SigninUserService {

	private SigninUserDAO signinDAO = new SigninUserDAO();
	private User logedinUser;
	private User user;
	private String loginErrorMessage;

	public SigninUserService(User member) throws Exception {
		this.user = member;
	}

	public boolean validEntry() {
		if((user.getEmail() != null && !user.getEmail().trim().equals("")) && (user.getPassword() != null && !user.getPassword().trim().equals("")))
			return true;
		else {
			loginErrorMessage = "No email or password entered!";
			return false;
		}
	}

	public boolean correctCredentials() {
		if(logedinUser != null)
			return true;
		else {
			loginErrorMessage = "Wrong email or Password!";
			return false;
			}
		}

	public void signinMember() throws Exception {
		logedinUser = signinDAO.getMemberByUserName(user);
	}

	public User getLogedinUser() {
		return logedinUser;
	}
	public String getSigninErrorMessage() {
		return loginErrorMessage;
	}

	// Debug code!
	/*
	public static void main(String[] args) {
		User user = new User();
		String random = null;
		user.setEmail("yosh.nahar@gmail.com");
		user.setPassword(random);

		try {
			LoginUserService a = new LoginUserService(user);
			System.out.println(a.validEntry());
			System.out.println(user.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	*/
}
