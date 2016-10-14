package nl.parkhaven.model.abstraction;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.NavigableMap;

public interface SchemaDao {

	String[] getWasschemaData(int i, int i2, String wasmachine_id);

	NavigableMap<Integer, Date> getDates();

	ArrayList<Time> getTimes();
}
