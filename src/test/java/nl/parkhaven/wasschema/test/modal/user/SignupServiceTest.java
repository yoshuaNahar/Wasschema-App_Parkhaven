package nl.parkhaven.wasschema.test.modal.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.parkhaven.wasschema.model.user.SigninService;
import nl.parkhaven.wasschema.model.user.SignupService;
import nl.parkhaven.wasschema.model.user.User;

public final class SignupServiceTest {

	private SignupService signupService;
	private SigninService signinService;

	private User user_AllInfo;
	private User user_NecessaryInfo;
	private User user_Signedin;

//	@Before
	public void settingUsersCredentials() {
		user_AllInfo = new User();
		user_AllInfo.setVoornaam("test");
		user_AllInfo.setAchternaam("user");
		user_AllInfo.setHuisnummer("1000");
		user_AllInfo.setEmail("test.user@gmail.com");
		user_AllInfo.setWachtwoord("testPassword");
		user_AllInfo.setMobielnummer("123456789");

		user_NecessaryInfo = new User();
		user_NecessaryInfo.setHuisnummer("1001");
		user_NecessaryInfo.setEmail("test.user2@hotmail.com");
		user_NecessaryInfo.setWachtwoord("testPassword2");

		signupService = new SignupService();
		signinService = new SigninService();
	}

//	@Test
	public void testingSignup() {
		// User 1 - newUserAllInfo
		signupService.signup(user_AllInfo, "3024NH");
		if (signupService.errorOccured()) {
			Assert.fail("Error occurred which shouldn't! - " + signupService.getErrorMessage());
		}
		signinService.signin(user_AllInfo);
		if (signinService.errorOccured()) {
			Assert.fail("Error occurred which shouldn't! - " + signinService.getErrorMessage());
		}
		user_Signedin = signinService.getSignedinUser();
		Assert.assertEquals("Is the firstname correct?", user_AllInfo.getVoornaam(), user_Signedin.getVoornaam());
		Assert.assertEquals("Is the surname correct?", user_AllInfo.getAchternaam(), user_Signedin.getAchternaam());
		Assert.assertEquals("Is the housenumber correct?", user_AllInfo.getHuisnummer(), user_Signedin.getHuisnummer());
		Assert.assertEquals("Is the email correct?", user_AllInfo.getEmail(), user_Signedin.getEmail());
		Assert.assertEquals("Is the password correct?", user_AllInfo.getWachtwoord(), user_Signedin.getWachtwoord());
		Assert.assertEquals("Is the mobilenumber correct?", user_AllInfo.getMobielnummer(),
				user_Signedin.getMobielnummer());

		// User 2 - newUserNecessaryInfo
		signupService.signup(user_NecessaryInfo, "3024NH");
		if (signupService.errorOccured()) {
			Assert.fail("Error occurred, which shouldn't! - " + signupService.getErrorMessage());
		}
		signinService.signin(user_NecessaryInfo);
		if (signinService.errorOccured()) {
			Assert.fail("Error occurred, which shouldn't! - " + signinService.getErrorMessage());
		}
		user_Signedin = signinService.getSignedinUser();
		Assert.assertNull("Is the firstname correct?", user_Signedin.getVoornaam());
		Assert.assertNull("Is the surname correct?", user_Signedin.getAchternaam());
		Assert.assertEquals("Is the housenumber correct?", user_NecessaryInfo.getHuisnummer(),
				user_Signedin.getHuisnummer());
		Assert.assertEquals("Is the email correct?", user_NecessaryInfo.getEmail(), user_Signedin.getEmail());
		Assert.assertEquals("Is the password correct?", user_NecessaryInfo.getWachtwoord(),
				user_Signedin.getWachtwoord());
		Assert.assertNull("Is the mobilenumber correct?", user_Signedin.getMobielnummer());
	}
}
