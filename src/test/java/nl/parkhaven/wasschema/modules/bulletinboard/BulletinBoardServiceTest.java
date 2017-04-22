package nl.parkhaven.wasschema.modules.bulletinboard;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.parkhaven.wasschema.config.TestConfig;

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
		Map<Long, Message> messages = service.getMessages();
		assertThat(messages.size(), greaterThan(-1));
	}

	@Test
	public void testDeactiveMessage() {
		service.addMessage(message);
		service.deactivateMessage(message);
	}

	@Test
	public void testAcceptPendingMessage() {
		service.addMessage(message);

		Map<Long, Message> pendingMessages = service.getPendingMessages();
		Long lastKey = 0L;
		for (Entry<Long, Message> entry : pendingMessages.entrySet()) {
			lastKey = entry.getKey();
		}

		service.acceptPendingMessage(pendingMessages.get(lastKey));
	}

}
