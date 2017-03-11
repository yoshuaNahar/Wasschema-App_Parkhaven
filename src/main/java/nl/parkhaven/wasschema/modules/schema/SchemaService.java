package nl.parkhaven.wasschema.modules.schema;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchemaService {

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
