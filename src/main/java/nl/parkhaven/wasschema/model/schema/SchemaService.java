package nl.parkhaven.wasschema.model.schema;

import java.util.Map;
import java.util.NavigableMap;

public final class SchemaService {

	private final SchemaDaoImpl schemaDao;
	private String[][] huisnummers;
	private Map<Long, String> times;
	private NavigableMap<Long, String> dates;
	private Map<Long, String> wasmachines;
	private int timesSize;
	private int datesId;

	public SchemaService() {
		schemaDao = new SchemaDaoImpl();
	}

	public String[][] getData(int wasmachine_id) {
		int firstId = datesId * timesSize + 1;
		int lastId = (datesId + 14) * timesSize;
		
		System.out.println(firstId + " " + lastId);
		huisnummers = schemaDao.getWasschemaData(firstId, lastId, wasmachine_id);

		return huisnummers;
	}

	public Map<Long, String> getWasmachines() {
		wasmachines = schemaDao.getWasmachines();

		return wasmachines;
	}

	public Map<Long, String> getTimes() {
		times = schemaDao.getTimes();
		timesSize = times.size();

		System.out.println(timesSize);
		return times;
	}

	public NavigableMap<Long, String> getDates() {
		dates = schemaDao.getDates();
		datesId = (int) (dates.firstKey() - 1);

		System.out.println(datesId);
		return dates;
	}

	public void releaseResources() {
		schemaDao.releaseResources();
	}

}
