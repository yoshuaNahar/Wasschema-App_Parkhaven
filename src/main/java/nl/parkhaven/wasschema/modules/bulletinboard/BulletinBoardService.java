package nl.parkhaven.wasschema.modules.bulletinboard;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petebevin.markdown.MarkdownProcessor;

@Service
public class BulletinBoardService {

	private BulletinBoardDaoImpl bulletinBoardDao;

	@Autowired
	public BulletinBoardService(BulletinBoardDaoImpl bulletinBoardDao) {
		this.bulletinBoardDao = bulletinBoardDao;
	}
	
	private Map<Long, Message> messages;

	public Map<Long, Message> getMessages() {
		messages = bulletinBoardDao.getAllMessages();
		return messages;
	}

	public void addMessage(Message message) {
		cleanMessage(message);
		convertMdToHtml(message);
		bulletinBoardDao.create(message);
	}

	public void deactivateMessage(Message message) {
		bulletinBoardDao.delete(message);
	}

	public Map<Long, Message> getPendingMessages() {
		messages = bulletinBoardDao.getPendingMessagesAdmin();
		return messages;
	}

	public void acceptPendingMessage(Message message) {
		bulletinBoardDao.update(message);
	}

	private void cleanMessage(Message message) {
		String titleMd = message.getTitleInput();
		String bodyMd = message.getBodyInput();

		String titleMdClean = Jsoup.clean(titleMd, Whitelist.basic());
		String bodyMdClean = Jsoup.clean(bodyMd, Whitelist.basic());

		message.setTitleOutput(titleMdClean);
		message.setBodyOutput(bodyMdClean);
	}

	private void convertMdToHtml(Message message) {
		MarkdownProcessor mdProcessor = new MarkdownProcessor();
		String titleHtml = mdProcessor.markdown(message.getTitleOutput());
		String bodyHtml = mdProcessor.markdown(message.getBodyOutput());

		message.setTitleOutput(titleHtml);
		message.setBodyOutput(bodyHtml);
	}

}
