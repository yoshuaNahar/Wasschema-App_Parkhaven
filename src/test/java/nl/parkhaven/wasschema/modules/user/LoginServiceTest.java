package nl.parkhaven.wasschema.modules.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

	@Mock
	private UserDaoImpl userDao;

	@InjectMocks
	private LoginService loginService;

	@Test
	public void testLogin() {
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
