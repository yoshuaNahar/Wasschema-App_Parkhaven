package nl.parkhaven.model.abstraction;

import java.util.Map;
import java.util.NavigableMap;

public interface SchemaDao {

	String[] getWasschemaData(int i, int i2, String wasmachine_id);

	NavigableMap<Long, String> getDates();

	Map<Long, String> getWasmachines();

	Map<Long, String> getTimes();
}