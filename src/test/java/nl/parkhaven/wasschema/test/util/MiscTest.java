package nl.parkhaven.wasschema.test.util;

import org.junit.Assert;
import org.junit.Test;

import nl.parkhaven.wasschema.util.Misc;

public class MiscTest {

	@Test
	public void testGenerateNewPassword() {
		// can't test this method
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
