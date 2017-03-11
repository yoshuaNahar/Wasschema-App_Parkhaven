package nl.parkhaven.wasschema.modules.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.parkhaven.wasschema.modules.util.DatesStringMaker;

public class DatesStringMakerTest {

	private DatesStringMaker datesStringMaker;

	@Before
	public void setup() {
		datesStringMaker = new DatesStringMaker();
	}

	@Test
	public void testOverview() {
		String[] dates = datesStringMaker.getOverview();
		assertThat(dates.length, is(2));
	}

	@Test
	public void testWashDates() {
		Map<Long, String> dates = datesStringMaker.getDates();
		Assert.assertTrue(dates.size() == 14);
		Assert.assertTrue(dates.get(1L)
				.contains("Mon"));
		Assert.assertTrue(dates.get(6L)
				.contains("Sat"));
	}

}
