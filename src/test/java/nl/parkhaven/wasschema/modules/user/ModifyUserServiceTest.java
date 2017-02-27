package nl.parkhaven.wasschema.modules.user;

import org.junit.Assert;
import org.junit.Test;

import nl.parkhaven.wasschema.modules.user.LoginService;
import nl.parkhaven.wasschema.modules.user.ModifyUserService;
import nl.parkhaven.wasschema.modules.user.User;

public class ModifyUserServiceTest {

	private ModifyUserService modifyUserService = new ModifyUserService();
	private LoginService loginService;
	/*
	@Test
	public void testChangeHouseNumber() {
		User user = createDummyUser();
		user.setHouseNumber("230A"); // Original is 999

		modifyUserService.changeHouseNumber(user);
		Assert.assertNotNull(modifyUserService.getErrorMessage());

		user.setHouseNumber("1A");
		modifyUserService.changeHouseNumber(user);

		user = login(user);
		Assert.assertNull(loginService.getErrorMessage());

		user.setHouseNumber("999");
		modifyUserService.changeHouseNumber(user);
	}

	@Test
	public void testChangePassword() {
		User user = createDummyUser();
		user.setPassword("123456");

		modifyUserService.changePassword(user);

		user = login(user);
		Assert.assertNull(loginService.getErrorMessage());

		Assert.assertNotEquals(user.getPassword(), "123");

		user.setPassword("123");
		modifyUserService.changePassword(user);
	}

	@Test
	public void testChangeEmail() {
		User user = createDummyUser();
		user.setEmail("aa@bb.com");

		modifyUserService.changeEmail(user);

		user = login(user);
		Assert.assertNull(loginService.getErrorMessage());

		Assert.assertNotEquals(user.getPassword(), "a@B.com");

		user.setEmail("a@B.com");
		modifyUserService.changeEmail(user);
	}

	@Test
	public void testDeleteAccount() {
		User user = createDummyUser();

		modifyUserService.deleteAccount(user);

		user = login(user);
		Assert.assertNull(user);

		user = createDummyUser();
		user.setHouseNumber("999");
		modifyUserService.changeHouseNumber(user);
		user.setEmail("a@B.com");
		modifyUserService.changeEmail(user);
	}

	private User createDummyUser() {
		User user = new User();
		user.setEmail("a@B.com");
		user.setPassword("123");
		user.setId(3);
		return user;
	}
	
	private User login(User user) {
		loginService = new LoginService(user);
		loginService.login();
		return loginService.getUser();
	}
	*/
}
