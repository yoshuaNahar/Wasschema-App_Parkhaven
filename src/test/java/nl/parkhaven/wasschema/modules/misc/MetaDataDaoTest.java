package nl.parkhaven.wasschema.modules.misc;

import nl.parkhaven.wasschema.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class MetaDataDaoTest {

    @Autowired
    private MetaDataDao metaDataDao;

    @Test
    public void testMetadataTableOperations() {
        assertThat(metaDataDao.getCounterSum("hitcounter"), is(greaterThan(0)));

        metaDataDao.insertCounter(10, "hitcounter");
    }

}
