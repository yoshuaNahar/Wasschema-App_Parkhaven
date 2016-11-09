package nl.parkhaven.wasschema;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.parkhaven.wasschema.model.prikbord.PrikbordMessage;
import nl.parkhaven.wasschema.model.prikbord.PrikbordService;

@WebServlet("/admin.010")
public class AdminControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Map<Long, PrikbordMessage> prikbordMessages;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		showPendingPrikbordMessages(request, response);

		request.getRequestDispatcher("/admin.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String approveMessage = request.getParameter("approve_prikbord_message");

		if (approveMessage != null) {
			acceptPendindingPrikbordMessage(request, response);
		}

		doGet(request, response);
	}


	private void showPendingPrikbordMessages(HttpServletRequest request, HttpServletResponse response) {
		PrikbordService prikbordService = new PrikbordService();

		prikbordMessages = prikbordService.getPendingMessages();
		request.setAttribute("prikbord_messages", prikbordMessages);
	}

	private void acceptPendindingPrikbordMessage(HttpServletRequest request, HttpServletResponse response) {
		String prikbordId = request.getParameter("message_id");

		PrikbordService prikbordService = new PrikbordService();
		PrikbordMessage message = new PrikbordMessage();
		message.setId(prikbordId);

		prikbordService.acceptPendingMessage(message);
	}
}
