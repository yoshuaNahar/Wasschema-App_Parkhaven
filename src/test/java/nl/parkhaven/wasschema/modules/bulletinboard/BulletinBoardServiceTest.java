package nl.parkhaven.wasschema.modules.bulletinboard;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import nl.parkhaven.wasschema.modules.bulletinboard.BulletinBoardService;
import nl.parkhaven.wasschema.modules.bulletinboard.Message;

public class BulletinBoardServiceTest {

	@Test
	public void testCreateBulletinBoardMessage() {
		BulletinBoardService service = new BulletinBoardService();
		Map<Long, Message> messages = service.getMessages();
		int messageAmountInitial = messages.size();

		Message message = createMessage();
		service.addMessage(message);

		Map<Long, Message> pendingMessages = service.getPendingMessages();
		Long lastKey = 0L;
		for (Entry<Long, Message> entry : pendingMessages.entrySet()) {
			lastKey = entry.getKey();
		}

		service.acceptPendingMessage(pendingMessages.get(lastKey));

		messages = service.getMessages();
		int messageAmountAfterCreate = messages.size();

		Assert.assertEquals(messageAmountInitial, messageAmountAfterCreate - 1);

		service.deactivateMessage(messages.get(0L));

		Assert.assertEquals(messageAmountInitial, service.getMessages().size());

		service.deleteCompletely(messages.get(0L).getId());
	}

	private Message createMessage() {
		Message message = new Message();
		message.setUserId(1);
		message.setUserEmail("yosh.nahar@gmail.com");
		message.setTitleInput("Test Title");
		message.setBodyInput("I want to tell you <br> something.");
		return message;
	}

}
