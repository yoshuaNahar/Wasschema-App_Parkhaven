package nl.parkhaven.wasschema.modules.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.configuration.PropertiesConfiguration;
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
public class LoginServiceTest {

	@Autowired
	private LoginService loginService;

	@Autowired
	private PropertiesConfiguration conf;

	private User user;

	@Before
	public void setup() {
		user = new User();
		user.setEmail(conf.getString("test.user.email"));
		user.setPassword(conf.getString("test.user.password"));
		user.setId(conf.getInt("test.user.id"));
	}

	@Test
	public void testLogin() {
		loginService.login(user);
	}

	@Test
	public void testGetWashCounter() {
		loginService.getWashCounter(user);
	}

	@Test
	public void testSetRandomPasswordFor() {
		loginService.setRandomPasswordFor(user);
	}

	@Test
	public void testLoginCredentialsValid() {
		User userWithEmailAndPassword = new User();
		userWithEmailAndPassword.setEmail("email@gmail.com");
		userWithEmailAndPassword.setPassword("password");

		User userWithEmail = new User();
		userWithEmail.setEmail("email@gmail.com");

		User userWithPassword = new User();
		userWithPassword.setPassword("password");

		assertThat(loginService.loginCredentialsValid(userWithEmailAndPassword), is(true));
		assertThat(loginService.loginCredentialsValid(userWithEmail), is(false));
		assertThat(loginService.loginCredentialsValid(userWithPassword), is(false));
	}

}
