package nl.parkhaven.wasschema.modules.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.parkhaven.wasschema.modules.user.LoginService;
import nl.parkhaven.wasschema.modules.user.User;

public class LoginServiceTest {

	private User correctCredentialsUser = new User();
	private User[] falseCredentialsUser = new User[3];
	private String[] expectedDbValues = new String[6];

	// 4 Users: 1 has Correct Credentials, 3 Wrong have Credentials
	@Before
	public void settingUsersCredentials() {
		correctCredentialsUser.setEmail("yosh.nahar@gmail.com");
		correctCredentialsUser.setPassword("asusx52j");

		User signinUserFalseEmailPassword = new User();
		signinUserFalseEmailPassword.setEmail("false.email@gmail.com");
		signinUserFalseEmailPassword.setPassword("falsePassword");

		User signinUserFalseEmail = new User();
		signinUserFalseEmail.setEmail("false.email@gmail.com");

		User signinUserFalsePassword = new User();
		signinUserFalsePassword.setPassword("falsePassword");

		falseCredentialsUser[0] = signinUserFalseEmailPassword;
		falseCredentialsUser[1] = signinUserFalseEmail;
		falseCredentialsUser[2] = signinUserFalsePassword;

		expectedDbValues[0] = "Yoshua";
		expectedDbValues[1] = "Nahar";
		expectedDbValues[2] = "230A";
		expectedDbValues[3] = "yosh.nahar@gmail.com";
		expectedDbValues[4] = "asusx52j";
	}
	/*
	@Test
	public void testLogin() {
		LoginService loginService = new LoginService(correctCredentialsUser);
		loginService.login();
		User signedinUser = loginService.getUser();
		Assert.assertEquals("Is the firstname correct?", signedinUser.getFirstName(), expectedDbValues[0]);
		Assert.assertEquals("Is the surname correct?", signedinUser.getLastName(), expectedDbValues[1]);
		Assert.assertEquals("Is the housenumber correct?", signedinUser.getHouseNumber(), expectedDbValues[2]);
		Assert.assertEquals("Is the email correct?", signedinUser.getEmail(), expectedDbValues[3]);
		Assert.assertEquals("Is the password correct?", signedinUser.getPassword(), expectedDbValues[4]);

		// False login credentials
		for (int i = 0; i < falseCredentialsUser.length; i++) {
			loginService = new LoginService(falseCredentialsUser[i]);
			loginService.login();
			signedinUser = loginService.getUser();
			Assert.assertTrue(loginService.errorOccured());
		}
	}
	*/
}
