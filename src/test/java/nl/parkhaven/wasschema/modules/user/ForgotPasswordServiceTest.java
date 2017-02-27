package nl.parkhaven.wasschema.modules.user;

import org.junit.Assert;
import org.junit.Test;

import nl.parkhaven.wasschema.modules.user.ForgotPasswordService;
import nl.parkhaven.wasschema.modules.user.LoginService;
import nl.parkhaven.wasschema.modules.user.ModifyUserService;
import nl.parkhaven.wasschema.modules.user.User;

public class ForgotPasswordServiceTest {
/*
	@Test
	public void testForgotPasswordService() {
		User user = createDummyUser();

		ForgotPasswordService forgotPasswordService = new ForgotPasswordService(user);
		forgotPasswordService.setRandomPasswordForUser();

		LoginService loginService = new LoginService(user);
		loginService.login();
		user = loginService.getUser();
		
		Assert.assertNotNull(user);

		user.setPassword("123");
		new ModifyUserService().changePassword(user);
	}
	*/
	private User createDummyUser() {
		User user = new User();
		user.setEmail("a@B.com");
		user.setPassword("123");
		user.setId(3);
		return user;
	}

}
