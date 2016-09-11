package nl.parkhaven.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.parkhaven.entity.User;

public class TestSigninUserDAO {

	private UserDaoImpl singinUserDAO = new UserDaoImpl();
	private User loggingIn = new User();
	private User loggedIn = new User();

	@Before
	public void userLoginCredentials() {
		loggingIn.setEmail("yosh.nahar@gmail.com");
		loggingIn.setPassword("compaq");
	}

	@Test
	public void testSigninUserDAO() throws Exception {
		loggedIn = singinUserDAO.signin(loggingIn);
		System.out.println(loggedIn.getEmail() + " " + loggedIn.getMobielnummer());
		Assert.assertTrue("Is the huisnummer correct?", loggedIn.getHuisnummer().equals("230"));
	}
}
