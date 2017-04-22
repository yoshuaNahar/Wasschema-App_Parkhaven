package nl.parkhaven.wasschema.modules.util;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;

import nl.parkhaven.wasschema.config.TestConfig;
import nl.parkhaven.wasschema.modules.user.LoginService;
import nl.parkhaven.wasschema.modules.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class MiscTest {

	@Autowired
	private LoginService loginService;

	@Test
	public void testGenerateNewPassword() {
		assertThat(Misc.generateNewPassword(), isA(String.class));
	}

	@Test
	public void testIsHouseNumberValidMethod() {
		User user = new User();

		user.setHouseNumber("230A");
		Assert.assertTrue(loginService.checkHouseNumberExists(user));

		user.setHouseNumber("1");
		Assert.assertFalse(loginService.checkHouseNumberExists(user));
	}

}
