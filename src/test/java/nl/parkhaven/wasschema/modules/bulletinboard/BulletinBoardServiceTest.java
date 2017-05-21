package nl.parkhaven.wasschema.modules.bulletinboard;

import nl.parkhaven.wasschema.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class BulletinBoardServiceTest {

    @Autowired
    private BulletinBoardService service;
    private Message message;

    @Before
    public void setup() {
        message = new Message();
        message.setUserId(1);
        message.setUserEmail("yosh.nahar@gmail.com");
        message.setTitleInput("Test Title");
        message.setBodyInput("I want to tell you <br> something.");
    }

    @Test
    public void testGetMessages() {
        service.getMessages();
    }

    // bad test
    @Test
    public void testAddMessageAndAcceptPendingMessage() {
        Map<Long, Message> messages = service.getMessages();
        int messagesCount = messages.size();

        service.addMessage(message);
        service.acceptPendingMessage(message);

        messages = service.getMessages();
        int messagesCountNew = messages.size();

        assertThat(messagesCountNew, is(messagesCount));
    }

    @Test
    public void testDeactivateMessage() {
        service.deactivateMessage(message);
    }

}
