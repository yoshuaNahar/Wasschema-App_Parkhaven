package nl.parkhaven.wasschema.modules.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MetaDataDao {

	private static final String ADD_META_DATA = "INSERT INTO metadata (counter, counter_type) VALUES (?, ?);";
	private static final String SELECT_COUNTER_SUM = "SELECT SUM(counter) FROM metadata WHERE counter_type = ?;";

	private JdbcTemplate template;

	@Autowired
	MetaDataDao(JdbcTemplate template) {
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

}
