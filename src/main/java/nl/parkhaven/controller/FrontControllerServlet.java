package nl.parkhaven.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.parkhaven.controller.service.AppointmentService;
import nl.parkhaven.controller.service.SigninUserService;
import nl.parkhaven.controller.service.SignupUserService;
import nl.parkhaven.model.AppointmentDaoImpl;
import nl.parkhaven.model.abstraction.AppointmentDao;
import nl.parkhaven.model.entity.Appointment;
import nl.parkhaven.model.entity.User;
import nl.parkhaven.pojos.DatePlacer;

@WebServlet("/index")
public class FrontControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FrontControllerServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DatePlacer datePlacer = new DatePlacer();
		request.setAttribute("getDays", datePlacer);

		AppointmentDao showData = new AppointmentDaoImpl();
		request.setAttribute("huisnummer1", showData.getAllData("A1"));
		// request.setAttribute("huisnummer2",
		// showData.getHuisnummersMachine2());

		request.getRequestDispatcher("/homepage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DatePlacer datePlacer = new DatePlacer();
		request.setAttribute("getDays", datePlacer);

		String loginForm = request.getParameter("signin");
		String appointmentForm = request.getParameter("appointment");
		String signinForm = request.getParameter("register");

		if (loginForm != null) {
			login(request, response);
		} else if (appointmentForm != null) {
			appointment(request, response);
		} else if (signinForm != null) {
			signin(request, response);
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = new User();
		user.setEmail(email);
		user.setWachtwoord(password);

		AppointmentDao showData = new AppointmentDaoImpl();
		request.setAttribute("huisnummer1", showData.getAllData("A1"));
		// request.setAttribute("huisnummer2",
		// showData.getHuisnummersMachine2());

		SigninUserService signinService = new SigninUserService(user);
		if (signinService.validEntry()) {
			signinService.signinMember();
			if (signinService.correctCredentials()) {
				// request.getSession().setAttribute("member",
				// signinService.getSignedinUser());
				request.getSession().setAttribute("userHuisnummer", signinService.getSignedinUser().getHuisnummer());
			} else {
				request.setAttribute("errorMessage", signinService.getSigninErrorMessage());
			}
		} else {
			request.setAttribute("errorMessage", signinService.getSigninErrorMessage());
		}
		request.getRequestDispatcher("/homepage.jsp").forward(request, response);
	}

	private void appointment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String day = request.getParameter("day");
		String time = request.getParameter("time");
		String machine = request.getParameter("machine");
		String userHuisnummer = (String) request.getSession().getAttribute("userHuisnummer");

		Appointment appointment = new Appointment();
		appointment.setDay(day);
		appointment.setTime(time);
		appointment.setMachinenummer(machine);
		appointment.setHuisnummer(userHuisnummer);

		AppointmentService appointmentService = new AppointmentService(appointment);
		if (appointmentService.validEntry()) {
			if (!appointmentService.datePlaced()) {
				request.setAttribute("errorMessage", appointmentService.getErrorMessage());
			}
		} else {
			request.setAttribute("errorMessage", appointmentService.getErrorMessage());
		}
		AppointmentDao showData = new AppointmentDaoImpl();
		request.setAttribute("huisnummer1", showData.getAllData("A1"));
		// request.setAttribute("huisnummer2",
		// showData.getHuisnummersMachine2());

		request.getRequestDispatcher("/homepage.jsp").forward(request, response);
	}

	private void signin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String password = request.getParameter("password");
		String huisnummer = request.getParameter("huisnummer");
		String code = request.getParameter("sharedcode");

		User user = new User();
		user.setEmail(email);
		user.setVoornaam(firstname);
		user.setAchternaam(lastname);
		user.setWachtwoord(password);
		user.setHuisnummer(huisnummer);

		AppointmentDao showData = new AppointmentDaoImpl();
		request.setAttribute("huisnummer1", showData.getAllData("A1"));
		// request.setAttribute("huisnummer2",
		// showData.getHuisnummersMachine2());

		SignupUserService signupService = new SignupUserService(user, code);
		if (signupService.validEntry()) {
			signupService.addMember();
			if (!signupService.correctCredentials()) {
				request.setAttribute("errorMessage", signupService.getSigninErrorMessage());
			}
		} else {
			request.setAttribute("errorMessage", signupService.getSigninErrorMessage());
		}
		request.getRequestDispatcher("/homepage.jsp").forward(request, response);
	}
}
