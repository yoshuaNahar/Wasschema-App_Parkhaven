package nl.parkhaven.wasschema.test.modal.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.parkhaven.wasschema.model.user.SigninService;
import nl.parkhaven.wasschema.model.user.User;

public final class SigninServiceTest {

	private SigninService signinService;
	private User correctCredentialsUser;
	private User[] falseCredentialsUser;
	private String[] expectedDbValues;
	private User signedinUser;

	// 4 Users: 1 has Correct Credentials, 3 Wrong have Credentials
	@Before
	public void settingUsersCredentials() {
		correctCredentialsUser = new User();
		correctCredentialsUser.setEmail("yosh.nahar@gmail.com");
		correctCredentialsUser.setWachtwoord("asusx52j");

		User signinUserFalseEmailPassword = new User();
		signinUserFalseEmailPassword.setEmail("false.email@gmail.com");
		signinUserFalseEmailPassword.setWachtwoord("falsePassword");

		User signinUserFalseEmail = new User();
		signinUserFalseEmail.setEmail("false.email@gmail.com");
		signinUserFalseEmail.setWachtwoord("compaq");

		User signinUserFalsePassword = new User();
		signinUserFalsePassword.setEmail("yosh.nahar@gmail.com");
		signinUserFalsePassword.setWachtwoord("falsePassword");

		falseCredentialsUser = new User[3];
		falseCredentialsUser[0] = signinUserFalseEmailPassword;
		falseCredentialsUser[1] = signinUserFalseEmail;
		falseCredentialsUser[2] = signinUserFalsePassword;

		signinService = new SigninService();

		expectedDbValues = new String[6];
		expectedDbValues[0] = "Yoshua";
		expectedDbValues[1] = "Nahar";
		expectedDbValues[2] = "230A";
		expectedDbValues[3] = "yosh.nahar@gmail.com";
		expectedDbValues[4] = "asusx52j";
		expectedDbValues[5] = "636493686";
	}

	@Test
	public void testingSignin() {
		signinService.signin(correctCredentialsUser);
		signedinUser = signinService.getSignedinUser();
		Assert.assertEquals("Is the firstname correct?", signedinUser.getVoornaam(), expectedDbValues[0]);
		Assert.assertEquals("Is the surname correct?", signedinUser.getAchternaam(), expectedDbValues[1]);
		Assert.assertEquals("Is the housenumber correct?", signedinUser.getHuisnummer(), expectedDbValues[2]);
		Assert.assertEquals("Is the email correct?", signedinUser.getEmail(), expectedDbValues[3]);
		Assert.assertEquals("Is the password correct?", signedinUser.getWachtwoord(), expectedDbValues[4]);
		Assert.assertEquals("Is the mobilenumber correct?", signedinUser.getMobielnummer(), expectedDbValues[5]);

		// False login credentials
		for (int i = 0; i < falseCredentialsUser.length; i++) {
			signinService.signin(falseCredentialsUser[i]);
			signedinUser = signinService.getSignedinUser();
			Assert.assertNotEquals("Is the firstname not correct?", signedinUser.getVoornaam(), expectedDbValues[0]);
			Assert.assertNotEquals("Is the surname not correct?", signedinUser.getAchternaam(), expectedDbValues[1]);
			Assert.assertNotEquals("Is the housenumber not correct?", signedinUser.getHuisnummer(),
					expectedDbValues[2]);
			Assert.assertNotEquals("Is the email not correct?", signedinUser.getEmail(), expectedDbValues[3]);
			Assert.assertNotEquals("Is the password not correct?", signedinUser.getWachtwoord(), expectedDbValues[4]);
			Assert.assertNotEquals("Is the mobilenumber not correct?", signedinUser.getMobielnummer(),
					expectedDbValues[5]);
			Assert.assertNull(signedinUser.getVoornaam());
			Assert.assertNull(signedinUser.getAchternaam());
			Assert.assertNull(signedinUser.getHuisnummer());
			Assert.assertNull(signedinUser.getEmail());
			Assert.assertNull(signedinUser.getWachtwoord());
			Assert.assertNull(signedinUser.getMobielnummer());
		}
	}
}