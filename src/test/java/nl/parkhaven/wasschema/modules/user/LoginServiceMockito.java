package nl.parkhaven.wasschema.modules.user;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceMockito {

	@Mock
	private UserDaoImpl userDao;

	@InjectMocks
	private LoginService loginService;

	@Test
	public void testLogin() {
		User userInput = new User();
		userInput.setEmail("yosh.nahar@gmail.com");
		userInput.setPassword("password");
		User userOutput = new User();

		when(userDao.read(userInput)).thenReturn(null);
		
		Assert.assertEquals(loginService.login(userInput), userOutput);
	}

}
