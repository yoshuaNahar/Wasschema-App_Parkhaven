package nl.parkhaven.wasschema.controllers;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import nl.parkhaven.wasschema.TestConfig;
import nl.parkhaven.wasschema.modules.user.LoginService;
import nl.parkhaven.wasschema.modules.user.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class LoginControllerTest {

	// I'm only unit testing the Normal/Expected Conditions.
	// All loginService methods are tested separately, so there is no need to
	// test
	// unexpected conditions. eg. user.email and user.password is null
	// Plus to be fair, this is almost useless. The controller method only has if/else conditions.
	@Mock
	private LoginService loginService;

	@InjectMocks
	private LoginController loginController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
	}

	@Test
	public void testLogin() throws Exception {
		User userLoggedIn = new User();
		userLoggedIn.setId(1);

		int[] washCounter = new int[] { 0, 2 };

		when(loginService.loginCredentialsValid(ArgumentMatchers.any(User.class))).thenReturn(true);
		when(loginService.login(ArgumentMatchers.any(User.class))).thenReturn(userLoggedIn);
		when(loginService.getWashCounter(ArgumentMatchers.any(User.class))).thenReturn(washCounter);

		RequestBuilder request = post("/").param("to_servlet", "login").param("email", "email@gmail.com")
				.param("password", "password");

		mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/index.010"));

		HttpSession session = mockMvc.perform(request).andReturn().getRequest().getSession();

		User sessionUser = (User) session.getAttribute("user");
		int[] sessionWashCounter = (int[]) session.getAttribute("wash_counter");
	}

	@Test
	public void testGetLoginPage() throws Exception {
		RequestBuilder request = get("/");
		mockMvc.perform(request).andExpect(status().isOk()).andExpect(view().name("login"));
	}

	@Test
	public void testSignup() throws Exception {
		RequestBuilder request = post("/").param("to_servlet", "signup").param("email", "email@gmail.com")
				.param("password", "password").param("houseNumber", "230").param("sharedPassword", "3024NH");

		when(loginService.signupCredentialsValid(ArgumentMatchers.any(User.class))).thenReturn(true);
		when(loginService.created(ArgumentMatchers.any(User.class))).thenReturn(true);
		when(loginService.loginCredentialsValid(ArgumentMatchers.any(User.class))).thenReturn(true);
		when(loginService.login(ArgumentMatchers.any(User.class))).thenReturn(new User());
	
		mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/index.010"));
	}

}
