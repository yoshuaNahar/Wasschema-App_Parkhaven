package nl.parkhaven.wasschema.modules.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.parkhaven.wasschema.config.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ModifyUserServiceTest {

	@Autowired
	private ModifyUserService modifyUserService;

	private User user;

	@Before
	public void setup() {
		user = new User();
		user.setEmail("a@B.com");
		user.setPassword("123");
		user.setId(3);
	}

	@Test
	public void testChangeHouseNumber() {
		user.setHouseNumber("88A");
		modifyUserService.changeHouseNumber(user);
	}

	@Test
	public void testChangePassword() {
		user.setPassword("123456");
		modifyUserService.changePassword(user);
	}

	@Test
	public void testChangeEmail() {
		user.setEmail("aa@bb.com");
		modifyUserService.changeEmail(user);
	}

	@Test
	public void testDeleteAccount() {
		user.setHouseNumber("999");
		modifyUserService.deleteAccount(user);
	}

}
