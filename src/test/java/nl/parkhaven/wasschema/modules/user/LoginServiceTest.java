package nl.parkhaven.wasschema.modules.user;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class LoginServiceTest {

	@Mock
	private UserDaoImpl userDao;

	@InjectMocks
	private LoginService loginService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		MockMvcBuilders.standaloneSetup(loginService).build();
	}

	@Test
	public void testLogin() {
		User userWithEmailAndPassword = new User();
		userWithEmailAndPassword.setEmail("email@gmail.com");
		userWithEmailAndPassword.setPassword("password");

		User userWithEmail = new User();
		userWithEmail.setEmail("email@gmail.com");

		User userWithPassword = new User();
		userWithPassword.setPassword("password");

		assertThat(loginService.loginCredentialsValid(userWithEmailAndPassword), equalTo(true));
		assertThat(loginService.loginCredentialsValid(userWithEmail), equalTo(false));
		assertThat(loginService.loginCredentialsValid(userWithPassword), equalTo(false));
	}

}
