package nl.parkhaven.wasschema.modules.schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SchemaDaoImpl {

    private static final String SELECT_TIJD = "SELECT DATE_FORMAT(tijd_default, '%H:%i') FROM tijd;";
    private static final String SELECT_LAUNDRYMACHINE = "SELECT name FROM wasmachine;";
    private static final String SELECT_HOUSENUMBER = "SELECT a.huisnummer FROM wasschema x LEFT JOIN gebruiker a ON x.gebruiker_id = a.id WHERE x.wasmachine_id = ? ORDER BY week_dag_tijd_id;";
    private JdbcTemplate template;

    @Autowired
    SchemaDaoImpl(JdbcTemplate template) {
        this.template = template;
    }

    public Map<Long, String> getTimes() {
        List<String> list = this.template.queryForList(SELECT_TIJD, String.class);
        Map<Long, String> times = new HashMap<>();
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            times.put((long) i, list.get(i));
        }
        return times;
    }

    public Map<Long, String> getWashingMachines() {
        List<String> list = this.template.queryForList(SELECT_LAUNDRYMACHINE, String.class);
        Map<Long, String> washmachines = new HashMap<>();
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            washmachines.put((long) i + 1, list.get(i));
        }
        return washmachines;
    }

    public String[][] getWashingSchemaData(int washmachine_id) {
        List<String> list = this.template.queryForList(SELECT_HOUSENUMBER, new Object[] {washmachine_id},
                String.class);
        int listIndex = 0;
        String houseNumbers[][] = new String[2][91];
        for (int week = 0; week < 2; week++) {
            for (int index = 0; index < 91; index++) {
                houseNumbers[week][index] = list.get(listIndex++);
            }
        }
        return houseNumbers;
    }

}
