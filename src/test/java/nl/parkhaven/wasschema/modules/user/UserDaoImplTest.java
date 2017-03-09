package nl.parkhaven.wasschema.modules.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import org.apache.commons.configuration.PropertiesConfiguration;
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
public class UserDaoImplTest {

	/*
	 * This is the only test where I directly test a repository. All other
	 * repo's will be tested through the corresponding services.
	 */

	@Autowired
	private PropertiesConfiguration conf;

	@Autowired
	private UserDaoImpl userDao;

	@Test
	public void readTest() {
		User user = new User();
		user.setEmail(conf.getString("test.user.email"));
		user.setPassword(conf.getString("test.user.password"));

		User dbUser = userDao.read(user);

		assertThat(dbUser.getId(), is(3));
	}

	@Test
	public void createUserTest() {
		User user = new User();
		user.setEmail("test@gmail.com");
		user.setPassword("password");
		user.setHouseNumber("997");
		user.setSharedPassword("1");

		User dbUser = new User();
		dbUser.setEmail("test@gmail.com");
		dbUser.setPassword("password");

		userDao.create(user);

		User signedInDbUser = userDao.read(dbUser);
		assertThat(signedInDbUser.getId(), greaterThan(20));
	}

}
