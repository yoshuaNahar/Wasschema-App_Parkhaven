package nl.parkhaven.wasschema;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.parkhaven.wasschema.component.user.ForgotPasswordService;
import nl.parkhaven.wasschema.component.user.LoginService;
import nl.parkhaven.wasschema.component.user.SignupService;
import nl.parkhaven.wasschema.component.user.User;
import nl.parkhaven.wasschema.util.MailSender;

// One Servlet per JSP
@WebServlet("")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String form = request.getParameter("to_servlet");
		if (form == null) {
			form = "";
		}

		String message = null;

		switch (form) {
			case "login":
				message = login(request, response);
				break;
			case "register":
				message = signup(request, response);
				break;
			case "forgotPassword":
				message = sendMailWithNewPassword(request, response);
			case "": // logout from main page
				message = "";
		}

		if (message != null) {
			request.setAttribute("message", message);
			doGet(request, response);
		} else {
			response.sendRedirect("index.010");
		}
	}

	private String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = new User();
		user.setEmail(email);
		user.setPassword(password);

		LoginService loginService = new LoginService(user);
		loginService.login();

		if (loginService.errorOccured()) {
			return loginService.getErrorMessage();
		} else {
			request.getSession().setAttribute("user", loginService.getUser());
			request.getSession().setAttribute("wash_counter", loginService.getWashCounter());
			return null;
		}
	}

	private String signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String housenumber = request.getParameter("housenumber");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String code = request.getParameter("sharedcode");

		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setPassword(password);
		user.setHouseNumber(housenumber.toUpperCase());

		SignupService signupService = new SignupService(user, code);
		signupService.signup();

		if (signupService.errorOccured()) {
			return signupService.getErrorMessage();
		} else {
			return login(request, response);
		}
	}

	private String sendMailWithNewPassword(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");

		User user = new User();
		user.setEmail(email);

		ForgotPasswordService forgotPasswordService = new ForgotPasswordService(user);
		forgotPasswordService.setRandomPasswordForUser();

		if (forgotPasswordService.errorOccured()) {
			return forgotPasswordService.getErrorMessage();
		} else {
			MailSender mailSender = new MailSender(user);
			mailSender.sendMailContainingPassword();
			return "A mail has been sent to your email adres.";
		}
	}

}
