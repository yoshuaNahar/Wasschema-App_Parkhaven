package nl.parkhaven.wasschema.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.parkhaven.wasschema.modules.bulletinboard.BulletinBoardService;
import nl.parkhaven.wasschema.modules.bulletinboard.Message;
import nl.parkhaven.wasschema.modules.user.ModifyUserService;
import nl.parkhaven.wasschema.modules.user.User;

@WebServlet("/admin.010")
public class AdminController extends HttpServlet {

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
		String form = request.getParameter("to_servlet");
		if (form == null) {
			form = "";
		}

		switch (form) {
			case "accept_messageForm":
				isMessageAccepted = request.getParameter("accept_prikbord_message");
				if (isMessageAccepted != null) {
					handlePendindingBulletinBoardMessage(request, response);
				}
				break;
			case "getAllUsersForm":
				showAllUsers(request, response);
				break;
			case "deleteSelectedUserForm":
				deleteSelectedUser(request, response);
				showAllUsers(request, response);
				break;
			default:
				doGet(request, response);
				return;
		}

		doGet(request, response);
	}

	private void showAllUsers(HttpServletRequest request, HttpServletResponse response) {
		ModifyUserService modifyUserService = new ModifyUserService();
		request.setAttribute("users", modifyUserService.getAllUsers());
	}

	private void deleteSelectedUser(HttpServletRequest request, HttpServletResponse response) {
		User user = new User();
		user.setId(Integer.parseInt(request.getParameter("id")));
		user.setPassword(request.getParameter("password"));

		ModifyUserService modifyUserService = new ModifyUserService();
		modifyUserService.deleteAccount(user);
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
