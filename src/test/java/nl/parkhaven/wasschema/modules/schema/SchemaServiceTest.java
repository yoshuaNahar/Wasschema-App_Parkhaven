package nl.parkhaven.wasschema.modules.schema;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class SchemaServiceTest {

	/*
	 * The schema is the only class where I don't release the resources in the
	 * Dao classes. I do this because I recall getData multiple times after each
	 * other. I also only call this method twice so I won't forget to release
	 * the resources.
	 * Always call getTimes first, and always close connection after last getData call!
	 */

	private SchemaService schemaService = new SchemaService();

	public void testWashTimesCorrect() {
		Map<Long, String> times = schemaService.getTimes();
		Assert.assertTrue(times.size() == 13);
		Assert.assertEquals("05:30", times.get(0L));
		Assert.assertEquals("23:30", times.get(12L));
	}

	public void testGetWashingMachines() {
		testWashTimesCorrect();
		Map<Long, String> dates = schemaService.getWashingMachines();
		Assert.assertTrue(dates.size() == 12);
		Assert.assertEquals("A1", dates.get(1L));
		Assert.assertEquals("B4", dates.get(8L));
	}

	@Test
	public void testGetWashingSchemaData() {
		testGetWashingMachines();
		// can't test specific values of the washData
		// only if it runs correctly
		String[][] washData = schemaService.getData(1);
		Assert.assertEquals(2, washData.length);
		Assert.assertEquals(91, washData[0].length);
		schemaService.releaseResources();
	}

}
