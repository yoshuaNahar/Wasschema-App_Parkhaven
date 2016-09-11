package nl.parkhaven.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.parkhaven.dao.ShowDataDAO;
import nl.parkhaven.entity.Appointment;
import nl.parkhaven.entity.User;
import nl.parkhaven.pojo.DatePlacer;
import nl.parkhaven.service.AppointmentService;
import nl.parkhaven.service.SigninUserService;
import nl.parkhaven.service.SignupUserService;

@WebServlet("/index")
public class FrontControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FrontControllerServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatePlacer dp = new DatePlacer();
		request.setAttribute("getDays", dp);

		ShowDataDAO showData = new ShowDataDAO();
		showData.databaseHuisnummersToArray();
		request.setAttribute("huisnummer1", showData.getHuisnummersMachine1());
		request.setAttribute("huisnummer2", showData.getHuisnummersMachine2());

		request.getRequestDispatcher("/homepage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		DatePlacer dp = new DatePlacer();
		request.setAttribute("getDays", dp);

		String loginForm = request.getParameter("signin");
		String appointmentForm = request.getParameter("appointment");
		String signinForm = request.getParameter("register");

		if(loginForm != null) {
			try {
				login(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(appointmentForm != null) {
			try {
				appointment(request, response);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		else if(signinForm != null) {
			try {
				signin(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = new User();
		user.setEmail(email);
		user.setPassword(password);

		ShowDataDAO showData = new ShowDataDAO();
		request.setAttribute("huisnummer1", showData.getHuisnummersMachine1());
		request.setAttribute("huisnummer2", showData.getHuisnummersMachine2());

		try {
			SigninUserService signinService = new SigninUserService(user);
			if(signinService.validEntry()) {
				signinService.signinMember();
				if(signinService.correctCredentials())
					//request.getSession().setAttribute("member", memberService.getLogedinUser());
					request.getSession().setAttribute("userHuisnummer", signinService.getSignedinUser().getHuisnummer());
				else
					request.setAttribute("errorMessage", signinService.getSigninErrorMessage());
			}
			else
				request.setAttribute("errorMessage", signinService.getSigninErrorMessage());

			request.getRequestDispatcher("/homepage.jsp").forward(request, response);
		}
		catch(Exception e) {
			response.sendRedirect("error.html");
		}
	}

	private void appointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, Exception {
		String day = request.getParameter("day");
		String time = request.getParameter("time");
		String machine = request.getParameter("machine");
		String userHuisnummer = (String) request.getSession().getAttribute("userHuisnummer");

		Appointment ap = new Appointment();
		ap.setDay(day);
		ap.setTime(time);
		ap.setMachinenummer(machine);
		ap.setHuisnummer(userHuisnummer);

		try {
			AppointmentService appointmentService = new AppointmentService(ap);
			if(appointmentService.validEntry()) {
				if(appointmentService.dateFree())
					appointmentService.addAppointment();
				else
					request.setAttribute("errorMessage", appointmentService.getAppointmentErrorMessage());
			}
			else
				request.setAttribute("errorMessage", appointmentService.getAppointmentErrorMessage());

			ShowDataDAO showData = new ShowDataDAO();
			request.setAttribute("huisnummer1", showData.getHuisnummersMachine1());
			request.setAttribute("huisnummer2", showData.getHuisnummersMachine2());

			request.getRequestDispatcher("/homepage.jsp").forward(request, response);
		}
		catch(Exception e) {
			response.sendRedirect("error.html");
		}
	}

	private void signin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String email = request.getParameter("email");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String password = request.getParameter("password");
		String huisnummer = request.getParameter("huisnummer");
		String code = request.getParameter("sharedcode");

		User user = new User();
		user.setEmail(email);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setPassword(password);
		user.setHuisnummer(huisnummer);

		try {
			ShowDataDAO showData = new ShowDataDAO();
			request.setAttribute("huisnummer1", showData.getHuisnummersMachine1());
			request.setAttribute("huisnummer2", showData.getHuisnummersMachine2());

			SignupUserService signupService = new SignupUserService(user, code);
			if(signupService.validEntry()) {
				signupService.addMember();
				if(!signupService.correctCredentials())
					request.setAttribute("errorMessage", signupService.getSigninErrorMessage());
			}	
			else
				request.setAttribute("errorMessage", signupService.getSigninErrorMessage());

			request.getRequestDispatcher("/homepage.jsp").forward(request, response);
		}
		catch(Exception e) {
			response.sendRedirect("error.html");
		}
	}
}
