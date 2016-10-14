package nl.parkhaven.model;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.NavigableMap;

public final class SchemaService {

	private final SchemaDaoImpl schemaDao;
	private String[] huisnummers;
	private ArrayList<Time> times;
	private NavigableMap<Integer, Date> dates;
	private int timesSize;
	private int datesId;

	public SchemaService() {
		schemaDao = new SchemaDaoImpl();
	}

	public String[] getData(String wasmachine_id) {
		int firstId = datesId * timesSize + 1;
		int lastId = (datesId + 20) * timesSize;
		huisnummers = schemaDao.getWasschemaData(firstId, lastId, wasmachine_id);

		return huisnummers;
	}

	public ArrayList<Time> getTimes() {
		times = schemaDao.getTimes();
		timesSize = times.size();

		return times;
	}

	public NavigableMap<Integer, Date> getDates() {
		dates = schemaDao.getDates();
		datesId = dates.firstKey() - 1;

		return dates;
	}

	public void releaseResources() {
		schemaDao.releaseResources();
	}
}
