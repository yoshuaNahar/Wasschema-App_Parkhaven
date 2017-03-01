package nl.parkhaven.wasschema.modules.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.greaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.parkhaven.wasschema.config.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class UserDaoImplTest {

	// Testing repos

	@Autowired
	private UserDaoImpl userDao;

	@Test
	public void readTest() {
		User user = new User();
		user.setEmail("yosh.nahar@gmail.com");
		user.setPassword("asusx52j");

		User dbUser = userDao.read(user);

		assertThat(dbUser.getId(), is(1));
	}

	@Test
	public void createUserTest() {
		User user = new User();
		user.setEmail("user@gmail.com");
		user.setPassword("password");
		user.setHouseNumber("239");
		user.setSharedPassword("1");

		User dbUser = new User();
		dbUser.setEmail("user@gmail.com");
		dbUser.setPassword("password");

		userDao.create(user);

		User signedInDbUser = userDao.read(dbUser);
		assertThat(signedInDbUser.getId(), greaterThan(20));
	}

}
