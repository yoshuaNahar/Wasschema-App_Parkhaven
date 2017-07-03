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

  public String[][] getData(int washingMachine_id) {
    return schemaDao.getWashingSchemaData(washingMachine_id);
  }

  public Map<Long, String> getWashingMachines() {
    return schemaDao.getWashingMachines();
  }

  public Map<Long, String> getTimes() {
    return schemaDao.getTimes();
  }

}
