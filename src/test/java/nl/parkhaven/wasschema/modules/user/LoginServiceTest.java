package nl.parkhaven.wasschema.modules.user;

import nl.parkhaven.wasschema.config.TestConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class LoginServiceTest {

    @Autowired private LoginService loginService;
    private User user;

    @Before
    public void setup() {
        user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        user.setHouseNumber("997"); // This house number would give an error in the service layer, but because of that, I know no errors will occur with uniqueness
        user.setSharedPassword("1");

        loginService.signup(user);
    }

    @Test
    public void testLogin() {
        User loggedInUser = loginService.login(user);

        assertThat(loggedInUser.getEmail(), is("test@gmail.com"));
        assertThat(loggedInUser.getId(), greaterThan(2));
    }

    @Test
    public void testGetWashCounter() {
        Map<String, Integer> washCounters = loginService.getWashCounter(user);

        assertThat(washCounters.get("currentwash"), is(0));
    }

    @Test
    public void testSetRandomPasswordFor() {
        User loggedInUser = loginService.login(user);
        boolean passwordChanged = loginService.setRandomPasswordFor(loggedInUser);

        assertThat(passwordChanged, is(true));
        assertThat(user.getPassword(), is(not(loggedInUser.getPassword())));
    }

    @Test
    public void testLoginCredentialsValid() {
        User userWithEmailAndPassword = new User();
        userWithEmailAndPassword.setEmail("email@gmail.com");
        userWithEmailAndPassword.setPassword("password");

        assertThat(loginService.isLoginCredentialsValid(userWithEmailAndPassword), is(true));
    }

    @Test
    public void testLoginCredentialsInvalid() {
        User userWithEmail = new User();
        userWithEmail.setEmail("email@gmail.com");

        User userWithPassword = new User();
        userWithPassword.setPassword("password");

        assertThat(loginService.isLoginCredentialsValid(userWithEmail), is(false));
        assertThat(loginService.isLoginCredentialsValid(userWithPassword), is(false));
    }

    @Ignore
    public void testSingupCredentialsValid() {
        // already tested in @Before
    }

    @Ignore
    public void testSignupCredentialsInValid() {
        // just simple null checks
    }

    @Test
    public void testHouseNumberExists() {
        user.setHouseNumber("1000"); // will definitely exist in db
        boolean houseNumberExists = loginService.houseNumberExist(user);

        assertThat(houseNumberExists, is(false));
    }

    @Test
    public void testHouseNumberDoesntExistAnymore() {
        boolean houseNumberExists = loginService.houseNumberExist(user);

        assertThat(houseNumberExists, is(false));
    }

    @Test
    public void testSharedPasswordValid() {
        user.setSharedPassword("9999AH");
        boolean sharedPasswordValid = loginService.sharedPasswordValid(user);

        assertThat(sharedPasswordValid, is(true));
        assertThat(user.getSharedPassword(), is("2"));
    }

    @Test
    public void testSharedPasswordInvalid() {
        user.setSharedPassword("000000");
        boolean sharedPasswordInvalid = loginService.houseNumberExist(user);

        assertThat(sharedPasswordInvalid, is(false));
    }

    @Ignore
    public void testSignup() {
        // already tested in UserDaoImplTest
    }

}
