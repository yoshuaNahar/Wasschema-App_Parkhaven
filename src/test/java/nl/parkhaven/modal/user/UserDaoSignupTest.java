package nl.parkhaven.modal.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import nl.parkhaven.model.UserDaoImpl;
import nl.parkhaven.model.abstraction.CrudDao;
import nl.parkhaven.model.entity.User;

@Ignore("Data Creating Test")
public class UserDaoSignupTest {

	private CrudDao<User> userDaoImpl;

	private User newUser;
	private User signedupUser;
	private User signedinUser;

	@Before
	public void settingUsersCredentials() {
		newUser = new User();
		newUser.setVoornaam("test");
		newUser.setAchternaam("user");
		newUser.setHuisnummer("1000");
		newUser.setEmail("test.user@gmail.com");
		newUser.setWachtwoord("testPassword");
		newUser.setMobielnummer("123456789");

		userDaoImpl = new UserDaoImpl();
	}

	@Test
	public void testingSignin() {
		userDaoImpl.create(newUser);
		signedinUser = userDaoImpl.read(newUser);
		Assert.assertEquals("Is the firstname correct?", signedupUser.getVoornaam(), signedinUser.getVoornaam());
		Assert.assertEquals("Is the surname correct?", signedupUser.getAchternaam(), signedinUser.getAchternaam());
		Assert.assertEquals("Is the housenumber correct?", signedupUser.getHuisnummer(), signedinUser.getHuisnummer());
		Assert.assertEquals("Is the email correct?", signedupUser.getEmail(), signedinUser.getEmail());
		Assert.assertEquals("Is the password correct?", signedupUser.getWachtwoord(), signedinUser.getWachtwoord());
		Assert.assertEquals("Is the mobilenumber correct?", signedupUser.getMobielnummer(), signedinUser.getMobielnummer());
	}
}
