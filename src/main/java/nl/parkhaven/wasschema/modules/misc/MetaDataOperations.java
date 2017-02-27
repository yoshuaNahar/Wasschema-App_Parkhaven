package nl.parkhaven.wasschema.modules.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public final class MetaDataOperations {

	private static final String ADD_META_DATA = "INSERT INTO metadata (counter, counter_type) VALUES (?, ?);";
	private static final String SELECT_COUNTER_SUM = "SELECT SUM(counter) FROM metadata WHERE counter_type = ?;";
	private static final String REMOVE = "REMOVE FROM metadata ORDER BY id DESC LIMIT 1;";

	private JdbcTemplate template;

	@Autowired
	public MetaDataOperations(JdbcTemplate template) {
		this.template = template;
	}

	// used when restarting application, to save the counter for amount of page
	// refreshes, and washes placed
	public void insertCounter(int counter, String type) {
		this.template.update(ADD_META_DATA, new Object[] { counter, type });
	}

	// not used yet, but this is to get the old counters after a restart
	public int getCounterSum(String type) {
		return this.template.queryForObject(SELECT_COUNTER_SUM, new Object[] { type }, Integer.class);
	}

	// method only to be used for testing
	public void deleteLastCounter() {
		/*
		 * REMOVE FROM metadata WHERE id = SELECT MAX(id) FROM metadata; In
		 * MySQL, you can't modify (INSERT, UPDATE, DELETE) the same table which
		 * you use in the SELECT part.
		 * http://stackoverflow.com/questions/45494/mysql-error-1093-cant-
		 * specify-target-table-for-update-in-from-clause
		 */
		this.template.update(REMOVE);
	}

}
