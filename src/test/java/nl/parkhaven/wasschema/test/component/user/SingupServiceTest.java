package nl.parkhaven.wasschema.test.component.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.parkhaven.wasschema.component.user.LoginService;
import nl.parkhaven.wasschema.component.user.SignupService;
import nl.parkhaven.wasschema.component.user.User;

public class SingupServiceTest {

	private User user_AllInfo = new User();
	private User user_NecessaryInfo = new User();

	@Before
	public void settingUsersCredentials() {
		user_AllInfo.setFirstName("test");
		user_AllInfo.setLastName("user");
		user_AllInfo.setHouseNumber("100");
		user_AllInfo.setEmail("test.user@gmail.com");
		user_AllInfo.setPassword("testPassword");

		user_NecessaryInfo.setHouseNumber("101");
		user_NecessaryInfo.setEmail("test.user2@hotmail.com");
		user_NecessaryInfo.setPassword("testPassword2");
	}

	@Test
	public void testingSignup() {
		// User 1 - newUserAllInfo
		SignupService signupService = new SignupService(user_AllInfo, "3024NH");
		signupService.signup();
		Assert.assertNull(signupService.getErrorMessage());

		LoginService loginService = new LoginService(user_AllInfo);
		loginService.login();
		Assert.assertNull(loginService.getErrorMessage());

		User user_Signedin = loginService.getUser();
		Assert.assertEquals("Is the firstname correct?", user_AllInfo.getFirstName(), user_Signedin.getFirstName());
		Assert.assertEquals("Is the lastname correct?", user_AllInfo.getLastName(), user_Signedin.getLastName());
		Assert.assertEquals("Is the housenumber correct?", user_AllInfo.getHouseNumber(),
				user_Signedin.getHouseNumber());
		Assert.assertEquals("Is the email correct?", user_AllInfo.getEmail(), user_Signedin.getEmail());
		Assert.assertEquals("Is the password correct?", user_AllInfo.getPassword(), user_Signedin.getPassword());

		signupService.deleteUser(user_Signedin);
		loginService = new LoginService(user_AllInfo);
		loginService.login();
		Assert.assertNull(loginService.getUser());

		// User 2 - newUserNecessaryInfo
		signupService = new SignupService(user_NecessaryInfo, "3024NH");
		signupService.signup();
		Assert.assertNull(signupService.getErrorMessage());

		loginService = new LoginService(user_NecessaryInfo);
		loginService.login();
		Assert.assertNull(loginService.getErrorMessage());

		user_Signedin = loginService.getUser();
		Assert.assertNull("Is the firstname correct?", user_Signedin.getFirstName());
		Assert.assertNull("Is the surname correct?", user_Signedin.getLastName());
		Assert.assertEquals("Is the housenumber correct?", user_NecessaryInfo.getHouseNumber(),
				user_Signedin.getHouseNumber());
		Assert.assertEquals("Is the email correct?", user_NecessaryInfo.getEmail(), user_Signedin.getEmail());
		Assert.assertEquals("Is the password correct?", user_NecessaryInfo.getPassword(), user_Signedin.getPassword());

		signupService.deleteUser(user_Signedin);
		loginService = new LoginService(user_NecessaryInfo);
		loginService.login();
		Assert.assertNull(loginService.getUser());
	}

}
