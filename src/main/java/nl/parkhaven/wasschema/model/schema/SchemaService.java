package nl.parkhaven.wasschema.model.schema;

import java.util.Map;

public final class SchemaService {

	private final SchemaDaoImpl schemaDao;
	private String[][] huisnummers;
	private Map<Long, String> times;
	private Map<Long, String> wasmachines;

	/*
	 * The schema is the only class where I don't release the resources in the
	 * Dao classes. I do this because I recall getData multiple times after each
	 * other. I also only call this method twice so I won't forget to release
	 * the resources.
	 */
	public SchemaService() {
		schemaDao = new SchemaDaoImpl();
	}

	public String[][] getData(int wasmachine_id) {
		huisnummers = schemaDao.getWasschemaData(wasmachine_id);
		return huisnummers;
	}

	public Map<Long, String> getWasmachines() {
		wasmachines = schemaDao.getWasmachines();
		return wasmachines;
	}

	public Map<Long, String> getTimes() {
		times = schemaDao.getTimes();
		return times;
	}

	public void releaseResources() {
		schemaDao.releaseResources();
	}

}
