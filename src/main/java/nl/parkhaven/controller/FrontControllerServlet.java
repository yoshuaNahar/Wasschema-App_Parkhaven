package nl.parkhaven.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.parkhaven.controller.service.AppointmentService;
import nl.parkhaven.controller.service.SigninService;
import nl.parkhaven.controller.service.SignupService;
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
			signin(request, response);
		} else if (appointmentForm != null) {
			appointment(request, response);
		} else if (signinForm != null) {
			signup(request, response);
		}
	}

	private void signin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = new User();
		user.setEmail(email);
		user.setWachtwoord(password);

		AppointmentDao showData = new AppointmentDaoImpl();
		request.setAttribute("huisnummer1", showData.getAllData("A1"));
		// request.setAttribute("huisnummer2",
		// showData.getHuisnummersMachine2());

		SigninService signinService = new SigninService();
		signinService.signin(user);
		if (signinService.errorOccured()) {
			request.setAttribute("errorMessage", signinService.getErrorMessage());
		} else {
			request.getSession().setAttribute("userHuisnummer", signinService.getSignedinUser().getHuisnummer());
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

		AppointmentService appointmentService = new AppointmentService();
		appointmentService.addAppointment(appointment);
		if (appointmentService.errorOccured()) {
			request.setAttribute("errorMessage", appointmentService.getErrorMessage());
		}

		AppointmentDao showData = new AppointmentDaoImpl();
		request.setAttribute("huisnummer1", showData.getAllData("A1"));
		// request.setAttribute("huisnummer2",
		// showData.getHuisnummersMachine2());

		request.getRequestDispatcher("/homepage.jsp").forward(request, response);
	}

	private void signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		SignupService signupService = new SignupService();
		signupService.signup(user, code);
		if (signupService.errorOccured()) {
			request.setAttribute("errorMessage", signupService.getErrorMessage());
		} else {
			SigninService signinService = new SigninService();
			signinService.signin(user);
			request.getSession().setAttribute("userHuisnummer", signinService.getSignedinUser().getHuisnummer());
		}

		request.getRequestDispatcher("/homepage.jsp").forward(request, response);
	}
}
