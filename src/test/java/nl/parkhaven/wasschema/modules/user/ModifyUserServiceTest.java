package nl.parkhaven.wasschema.modules.user;

import nl.parkhaven.wasschema.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ModifyUserServiceTest {

    @Autowired private ModifyUserService modifyUserService;
    private User user;

    @Before
    public void setup() {
        user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("123");
        user.setId(3);
    }

    @Test
    public void testRememberLaundryRoom() {
        user.setRememberLaundryRoomChecked(true);
        boolean laundryRoomRemembered = modifyUserService.changeRememberLaundryRoom(user);

        assertThat(laundryRoomRemembered, is(true));
    }

    @Test
    public void testChangePassword() {
        user.setEmail("yosh.nahar@gmail.com");
        boolean passwordChanged = modifyUserService.changePassword(user);

        assertThat(passwordChanged, is(true));
    }

    @Test
    public void testChangeEmail() {
        user.setEmail("aa@bb.com");
        boolean emailChanged = modifyUserService.changeEmail(user);

        assertThat(emailChanged, is(true));
    }

    // I am not testing if the appointments of a deleted user is also removed
    @Test
    public void testDeleteAccount() {
        boolean accountDeleted = modifyUserService.deleteAccount(user);

        assertThat(accountDeleted, is(true));
    }

    @Test
    public void testDeleteAccountNonExistent() {
        User nonExistentUser = new User();
        nonExistentUser.setId(777);
        boolean accountDeleted = modifyUserService.deleteAccount(nonExistentUser);

        assertThat(accountDeleted, is(false));
    }

    @Test
    public void testGetAllUsers() {
        Map<Long, User> users = modifyUserService.getAllUsers();

        assertThat(users.size(), is(greaterThan(1)));
    }

}
