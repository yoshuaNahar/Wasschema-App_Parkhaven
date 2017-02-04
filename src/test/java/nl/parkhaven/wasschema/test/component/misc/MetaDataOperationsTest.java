package nl.parkhaven.wasschema.test.component.misc;

import org.junit.Assert;
import org.junit.Test;

import nl.parkhaven.wasschema.component.misc.MetaDataOperations;

public class MetaDataOperationsTest {

	private final MetaDataOperations miscDbOperations = new MetaDataOperations();

	@Test
	public void testMetadataTableOperations() {
		int sumInitial = miscDbOperations.getCounterSum("hitcounter");

		miscDbOperations.insertCounter(10, "hitcounter");

		int sumAfterInsert = miscDbOperations.getCounterSum("hitcounter");
		Assert.assertEquals(sumInitial + 10, sumAfterInsert);

		miscDbOperations.deleteLastCounter();

		int sumAfterDelete = miscDbOperations.getCounterSum("hitcounter");
		Assert.assertEquals(sumInitial, sumAfterDelete);
	}

}
