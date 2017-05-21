package nl.parkhaven.wasschema.modules.util;

import nl.parkhaven.wasschema.config.TestConfig;
import nl.parkhaven.wasschema.modules.user.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Ignore("This test should be run manually")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class MailServiceTest {

    @Autowired private MailService mailService;
    private User user;

    @Before
    public void setup() {
        user = new User();
        user.setHouseNumber("230A");
        user.setEmail("yosh.nahar@gmail.com");
        user.setPassword("myPassword");
    }

    @Test
    public void testSendMailContainingPassword() {
        mailService.sendMailContainingPasswordTo(user);
    }

    @Test
    public void testMailThreeHoursReminder() {
        mailService.sendMailThreeHoursReminder(user);
    }

}
