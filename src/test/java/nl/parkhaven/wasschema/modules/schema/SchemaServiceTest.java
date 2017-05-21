package nl.parkhaven.wasschema.modules.schema;

import nl.parkhaven.wasschema.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class SchemaServiceTest {

    @Autowired
    private SchemaService schemaDaoService;

    @Test
    public void testGetWashTimes() {
        Map<Long, String> times = schemaDaoService.getTimes();
        assertThat(times.size(), is(13));
    }

    @Test
    public void testGetWashingMachines() {
        Map<Long, String> dates = schemaDaoService.getWashingMachines();
        assertThat(dates.size(), is(12));
    }

    @Test
    public void testGetWashingSchemaData() {
        String[][] washData = schemaDaoService.getData(1);
        assertThat(washData.length, is(2));
        assertThat(washData[0].length, is(91));
    }

}
