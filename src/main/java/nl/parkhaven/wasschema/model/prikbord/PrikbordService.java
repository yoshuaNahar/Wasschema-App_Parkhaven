package nl.parkhaven.wasschema.model.prikbord;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.petebevin.markdown.MarkdownProcessor;

public final class PrikbordService {

	private PrikbordDaoImpl prikbordDao;
	private PrikbordMessage prikbordMessage;
	private Map<Long, PrikbordMessage> prikbordMessages;
	private String errorMessage;

	public PrikbordService() {
		prikbordDao = new PrikbordDaoImpl();
	}

	public Map<Long, PrikbordMessage> getPrikbordMessages() {
		prikbordMessages = prikbordDao.getAllMessages();

		return prikbordMessages;
	}

	public void addMessage(PrikbordMessage prikbordMessage) {
		this.prikbordMessage = prikbordMessage;
		cleanMessage();
		convertMdToHtml();
		prikbordDao.create(prikbordMessage);
	}

	public void deactivateMessage(PrikbordMessage prikbordMessage) {
		this.prikbordMessage = prikbordMessage;
		prikbordDao.delete(prikbordMessage);
	}

	public Map<Long, PrikbordMessage> getPendingMessages() {
		prikbordMessages = prikbordDao.getPendingMessagesAdmin();

		return prikbordMessages;
	}

	public void acceptPendingMessage(PrikbordMessage PrikbordMessage) {
		prikbordDao.update(PrikbordMessage);
	}

	public boolean errorOccured() {
		return errorMessage != null;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	private void cleanMessage() {
		String titleMd = prikbordMessage.getTitleInput();
		String bodyMd = prikbordMessage.getBodyInput();

		String titleMdClean = Jsoup.clean(titleMd, Whitelist.basic());
		String bodyMdClean = Jsoup.clean(bodyMd, Whitelist.basic());

		prikbordMessage.setTitleOutput(titleMdClean);
		prikbordMessage.setBodyOutput(bodyMdClean);
	}

	private void convertMdToHtml() {
		MarkdownProcessor mdProcessor = new MarkdownProcessor();
		String titleHtml = mdProcessor.markdown(prikbordMessage.getTitleOutput());
		String bodyHtml = mdProcessor.markdown(prikbordMessage.getBodyOutput());

		prikbordMessage.setTitleOutput(titleHtml);
		prikbordMessage.setBodyOutput(bodyHtml);
	}
}
