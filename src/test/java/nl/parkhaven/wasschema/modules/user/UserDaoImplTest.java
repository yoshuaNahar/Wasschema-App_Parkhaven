package nl.parkhaven.wasschema.modules.user;

import nl.parkhaven.wasschema.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class UserDaoImplTest {

    // This is the only test where I directly test a repository. All other
    // repo's will be tested through the corresponding services.
    // I perform this test, because I will signup users in the @Before setup method of LoginServiceTest

    @Autowired private UserDaoImpl userDao;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        user.setHouseNumber("997"); // This house number would give an error in the service layer, but because of that, I know no errors will occur with uniqueness
        user.setSharedPassword("1");

        boolean userCreated = userDao.create(user);

        assertThat(userCreated, is(true));
    }

}
