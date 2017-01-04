package nl.parkhaven.wasschema.test.modal.schema;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.parkhaven.wasschema.model.schema.SchemaService;
import nl.parkhaven.wasschema.util.DatesStringMaker;

public final class SchemaServiceTest {

	private Map<Long, String> times;
	private Map<Long, String> dates;

	private SchemaService schemaService;
	private DatesStringMaker datesMaker;

//	@Before
	public void setup() {
		schemaService = new SchemaService();
		datesMaker = new DatesStringMaker();
	}

//	@Test
	public void testGetTimes() {
		times = schemaService.getTimes();
		Assert.assertTrue(times.size() == 13);
		Assert.assertEquals("05:30", times.get(1));
		Assert.assertEquals("23:30", times.get(13));
	}

//	@Test
	public void testGetDates() {
		dates = datesMaker.getDates();
		Assert.assertTrue(dates.size() == 14);
		Assert.assertTrue(dates.get(1).contains("Mon"));
		Assert.assertTrue(dates.get(6).contains("Sat"));
	}

}
