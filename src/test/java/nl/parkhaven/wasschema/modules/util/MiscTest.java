package nl.parkhaven.wasschema.modules.util;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.junit.Test;

public class MiscTest {

	@Test
	public void testGenerateNewPassword() {
		assertThat(Misc.generateNewPassword(), isA(String.class));
	}

	@Test
	public void testIsHouseNumberValidMethod() {
		Assert.assertTrue(Misc.isHouseNumberValid("230A"));
		Assert.assertTrue(Misc.isHouseNumberValid("1A"));
		Assert.assertFalse(Misc.isHouseNumberValid("1AB"));
		Assert.assertFalse(Misc.isHouseNumberValid("9999"));
		Assert.assertFalse(Misc.isHouseNumberValid("0"));
		Assert.assertFalse(Misc.isHouseNumberValid("-50A"));
	}

}
