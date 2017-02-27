package nl.parkhaven.wasschema.modules.schema;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class SchemaService {

	/*
	 * The schema is the only class where I don't release the resources in the
	 * Dao classes. I do this because I recall getData multiple times after each
	 * other. I also only call this method twice so I won't forget to release
	 * the resources.
	 */

	private SchemaDaoImpl schemaDao;

	@Autowired
	public SchemaService(SchemaDaoImpl schemaDao) {
		this.schemaDao = schemaDao;
	}

	private String[][] houseNummers;
	private Map<Long, String> times;
	private Map<Long, String> washingMachines;

	public String[][] getData(int washingMachine_id) {
		houseNummers = schemaDao.getWashingSchemaData(washingMachine_id);
		return houseNummers;
	}

	public Map<Long, String> getWashingMachines() {
		washingMachines = schemaDao.getWashingMachines();
		return washingMachines;
	}

	public Map<Long, String> getTimes() {
		times = schemaDao.getTimes();
		return times;
	}

}
