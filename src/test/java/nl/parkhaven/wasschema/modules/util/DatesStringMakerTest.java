package nl.parkhaven.wasschema.modules.util;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import nl.parkhaven.wasschema.modules.util.DatesStringMaker;

public class DatesStringMakerTest {

	@Test
	public void testOverview() {
		// can't test this method
	}

	@Test
	public void testWashDates() {
		DatesStringMaker datesMaker = new DatesStringMaker();

		Map<Long, String> dates = datesMaker.getDates();
		Assert.assertTrue(dates.size() == 14);
		Assert.assertTrue(dates.get(1L).contains("Mon"));
		Assert.assertTrue(dates.get(6L).contains("Sat"));
	}

}
