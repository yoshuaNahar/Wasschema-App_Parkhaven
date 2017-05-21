package nl.parkhaven.wasschema.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nl.parkhaven.wasschema.modules.user.LoginService;
import nl.parkhaven.wasschema.modules.user.User;
import nl.parkhaven.wasschema.modules.util.MailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LoginControllerTest {

    // These types of tests seem messy..

    @Mock private LoginService loginService;
    @Mock private MailService mailService;

    @InjectMocks private LoginController loginController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void testGetLoginPage() throws Exception {
        RequestBuilder request = get("/");
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(view().name("login"));
    }

    @Test
    public void testLogin() throws Exception {
        Type type = new TypeToken<Map<String, Integer>>() {}.getType();

        User userLoggedIn = new User();
        userLoggedIn.setId(1);

        Map<String, Integer> washCounter = new HashMap<>();
        washCounter.put("currentwash", 2);

        when(loginService.isLoginCredentialsValid(ArgumentMatchers.any(User.class))).thenReturn(true);
        when(loginService.login(ArgumentMatchers.any(User.class))).thenReturn(userLoggedIn);
        when(loginService.getWashCounter(ArgumentMatchers.any(User.class))).thenReturn(washCounter);

        RequestBuilder request = post("/").param("to_servlet", "login").param("email", "email@gmail.com")
                .param("password", "password");

        mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index.010/loggedin"));

        HttpSession session = mockMvc.perform(request).andReturn().getRequest().getSession();

        User sessionUser = (User) session.getAttribute("user");
        String sessionWashCounter = (String) session.getAttribute("wash_counter");
        assertThat(sessionUser.getId(), equalTo(1));
        assertThat(new Gson().fromJson(sessionWashCounter, type), equalTo(washCounter));
    }

    @Test
    public void testSignup() throws Exception {
        RequestBuilder request = post("/").param("to_servlet", "signup").param("email", "email@gmail.com")
                .param("password", "password").param("houseNumber", "230").param("sharedPassword", "3024NH");

        when(loginService.signupCredentialsValid(ArgumentMatchers.any(User.class))).thenReturn(true);
        when(loginService.houseNumberExist(ArgumentMatchers.any(User.class))).thenReturn(true);
        when(loginService.signup(ArgumentMatchers.any(User.class))).thenReturn(true);
        when(loginService.isLoginCredentialsValid(ArgumentMatchers.any(User.class))).thenReturn(true);
        when(loginService.login(ArgumentMatchers.any(User.class))).thenReturn(new User());

        mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index.010/loggedin"));
    }

    @Test
    public void testForgotPassword() throws Exception {
        RequestBuilder request = post("/").param("to_servlet", "forgotPassword").param("email", "email@gmail.com");

        when(loginService.setRandomPasswordFor(ArgumentMatchers.any(User.class))).thenReturn(true);

        mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

}
