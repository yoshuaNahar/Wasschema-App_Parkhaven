package nl.parkhaven.controller;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.NavigableMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.parkhaven.model.SchemaService;
import nl.parkhaven.model.AppointmentService;
import nl.parkhaven.model.SigninService;
import nl.parkhaven.model.SignupService;
import nl.parkhaven.model.db.Database;
import nl.parkhaven.model.entity.Appointment;
import nl.parkhaven.model.entity.User;

@WebServlet(name = "controller", value = { "" }, loadOnStartup = 0)
public class FrontControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int hitCounter;

	public FrontControllerServlet() throws SQLException, PropertyVetoException {
		Database.getConnection();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		showSchema(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// DatePlacer datePlacer = new DatePlacer();
		// request.setAttribute("getDays", datePlacer);

		String loginForm = request.getParameter("signin");
		String appointmentForm = request.getParameter("appointment");
		String signinForm = request.getParameter("register");

		if (loginForm != null) {
			signin(request, response);
		} else if (appointmentForm != null) {
			appointment(request, response);
		} else if (signinForm != null) {
			signup(request, response);
		} else {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
		}

		showSchema(request, response);
	}

	@Override
	public void destroy() {
		// save hitCounter on db.
	}

	private void signin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = new User();
		user.setEmail(email);
		user.setWachtwoord(password);

		SigninService signinService = new SigninService();
		signinService.signin(user);
		if (signinService.errorOccured()) {
			request.setAttribute("errorMessage", signinService.getErrorMessage());
		} else {
			request.getSession().setAttribute("user_email", signinService.getSignedinUser().getEmail());
			request.getSession().setAttribute("user_id", signinService.getSignedinUser().getId());
		}
	}

	private void appointment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String day = request.getParameter("day");
		String time = request.getParameter("time");
		String machine = request.getParameter("machine");
		Integer userId = (Integer) request.getSession().getAttribute("user_id");

		Appointment appointment = new Appointment();
		appointment.setDay(day);
		appointment.setTime(time);
		appointment.setWasmachine(machine);
		appointment.setGebruiker_id(userId);

		AppointmentService appointmentService = new AppointmentService();
		appointmentService.addAppointment(appointment);
		if (appointmentService.errorOccured()) {
			request.setAttribute("errorMessage", appointmentService.getErrorMessage());
		}
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

		SignupService signupService = new SignupService();
		signupService.signup(user, code);
		if (signupService.errorOccured()) {
			request.setAttribute("errorMessage", signupService.getErrorMessage());
		} else {
			signin(request, response);
		}
	}

	private void showSchema(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SchemaService schemaService = new SchemaService();

		Map<Long, String> times = schemaService.getTimes();
		NavigableMap<Long, String> dates = schemaService.getDates();
		Map<Long, String> wasmachines = schemaService.getWasmachines();

		String[] huisnummers = schemaService.getData("C1");
		String[] huisnummers2 = schemaService.getData("C2");
		String[] huisnummers3 = schemaService.getData("D1");
		String[] huisnummers4 = schemaService.getData("D2");
		String[] huisnummers5 = schemaService.getData("C3");
		String[] huisnummers6 = schemaService.getData("C4");
		String[] huisnummers7 = schemaService.getData("D4");
		String[] huisnummers8 = schemaService.getData("D4");

		schemaService.releaseResources();

		request.setAttribute("huis_nummer1", huisnummers);
		request.setAttribute("huis_nummer2", huisnummers2);
		request.setAttribute("huis_nummer3", huisnummers3);
		request.setAttribute("huis_nummer4", huisnummers4);
		request.setAttribute("huis_nummer5", huisnummers5);
		request.setAttribute("huis_nummer6", huisnummers6);
		request.setAttribute("huis_nummer7", huisnummers7);
		request.setAttribute("huis_nummer8", huisnummers8);

		request.setAttribute("wasmachine", wasmachines);
		request.setAttribute("time", times);
		request.setAttribute("date", dates);

		hitCounter++;
		request.setAttribute("hitcounter", hitCounter);
		request.getRequestDispatcher("/homepage.jsp").forward(request, response);
	}
}
