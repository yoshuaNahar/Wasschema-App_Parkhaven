package nl.parkhaven.wasschema.model.prikbord;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.petebevin.markdown.MarkdownProcessor;

public final class PrikbordService {

	private static final Logger logger = LogManager.getLogger(PrikbordService.class);

	private PrikbordDaoImpl prikbordDao;
	private PrikbordMessage prikbordMessage;
	private List<PrikbordMessage> prikbordMessages;
	private String errorMessage;

	public PrikbordService() {
		prikbordDao = new PrikbordDaoImpl();
	}

	public List<PrikbordMessage> getPrikbordMessages() {
		prikbordMessages = prikbordDao.getAllMessages();

		return prikbordMessages;
	}

	public void addMessage(PrikbordMessage prikbordMessage) {
		this.prikbordMessage = prikbordMessage;
		cleanMessage();
		convertMdToHtml();
		prikbordDao.create(prikbordMessage);
	}

	public boolean errorOccured() {
		return errorMessage == null ? false : true;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	private void cleanMessage() {
		String titleMd = prikbordMessage.getTitleInput();
		String bodyMd = prikbordMessage.getBodyInput();

		String titleMdClean = Jsoup.clean(titleMd, Whitelist.basic());
		String bodyMdClean = Jsoup.clean(bodyMd, Whitelist.basic());

		prikbordMessage.setTitleInput(titleMdClean);
		prikbordMessage.setBodyInput(bodyMdClean);
	}

	private void convertMdToHtml() {
		MarkdownProcessor mdProcessor = new MarkdownProcessor();
		String titleHtml = mdProcessor.markdown(prikbordMessage.getTitleInput());
		String bodyHtml = mdProcessor.markdown(prikbordMessage.getBodyInput());

		prikbordMessage.setTitleOutput(titleHtml);
		prikbordMessage.setBodyOutput(bodyHtml);
	}
}
