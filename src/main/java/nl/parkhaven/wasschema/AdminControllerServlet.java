package nl.parkhaven.wasschema;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.parkhaven.wasschema.component.bulletinboard.Message;
import nl.parkhaven.wasschema.component.bulletinboard.BulletinBoardService;

@WebServlet("/admin.010")
public class AdminControllerServlet extends HttpServlet {

	private static final long serialVersionUID = 2L;

	private Map<Long, Message> bulletinBoardMessages;
	private String isMessageAccepted;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		showPendingBulletinBoardMessages(request, response);

		request.getRequestDispatcher("/admin.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		isMessageAccepted = request.getParameter("accept_prikbord_message");

		if (isMessageAccepted != null) {
			handlePendindingBulletinBoardMessage(request, response);
		}

		doGet(request, response);
	}

	private void showPendingBulletinBoardMessages(HttpServletRequest request, HttpServletResponse response) {
		BulletinBoardService bulletinBoardService = new BulletinBoardService();

		bulletinBoardMessages = bulletinBoardService.getPendingMessages();
		request.setAttribute("prikbord_messages", bulletinBoardMessages);
	}

	private void handlePendindingBulletinBoardMessage(HttpServletRequest request, HttpServletResponse response) {
		String bulletinBoardMesssageId = request.getParameter("message_id");

		BulletinBoardService BulletinBoardService = new BulletinBoardService();
		Message message = new Message();
		message.setId(bulletinBoardMesssageId);

		if (isMessageAccepted.equals("true")) {
			BulletinBoardService.acceptPendingMessage(message);
		} else {
			BulletinBoardService.deactivateMessage(message);
		}
	}

}
