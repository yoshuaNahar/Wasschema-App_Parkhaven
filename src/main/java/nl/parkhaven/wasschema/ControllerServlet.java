package nl.parkhaven.wasschema;

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

import nl.parkhaven.wasschema.model.appointment.Appointment;
import nl.parkhaven.wasschema.model.appointment.AppointmentService;
import nl.parkhaven.wasschema.model.misc.DbProcedureCalls;
import nl.parkhaven.wasschema.model.prikbord.PrikbordMessage;
import nl.parkhaven.wasschema.model.prikbord.PrikbordService;
import nl.parkhaven.wasschema.model.schema.SchemaService;
import nl.parkhaven.wasschema.model.user.SigninService;
import nl.parkhaven.wasschema.model.user.SignupService;
import nl.parkhaven.wasschema.model.user.User;
import nl.parkhaven.wasschema.pojos.DatePlacer;
import nl.parkhaven.wasschema.util.Database;

@WebServlet(name = "controller", value = { "" }, loadOnStartup = 0)
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int hitcounter;

	private Map<Long, String> times;
	private NavigableMap<Long, String> dates;
	private Map<Long, String> wasmachines;
	private Map<Long, PrikbordMessage> prikbordMessages;

	private String[][] huisnummers;
	private String[][] huisnummers2;
	private String[][] huisnummers3;
	private String[][] huisnummers4;
	private String[][] huisnummers5;
	private String[][] huisnummers6;
	private String[][] huisnummers7;
	private String[][] huisnummers8;

	public ControllerServlet() {
	}

	@Override
	public void init() {
		try {
			Database.getConnection();
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		SchemaService schemaService = new SchemaService();

		times = schemaService.getTimes();
		dates = schemaService.getDates();
		wasmachines = schemaService.getWasmachines();
		prikbordMessages = new PrikbordService().getPrikbordMessages();

		huisnummers = schemaService.getData(1);
		huisnummers2 = schemaService.getData(2);
		huisnummers3 = schemaService.getData(3);
		huisnummers4 = schemaService.getData(4);
		huisnummers5 = schemaService.getData(5);
		huisnummers6 = schemaService.getData(6);
		huisnummers7 = schemaService.getData(7);
		huisnummers8 = schemaService.getData(8);

		schemaService.releaseResources();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String week = request.getParameter("week");
		String wasruimte = request.getParameter("wasruimte");
		int week_id = 0;

		if (week != null && week.equals("next")) {
			week_id = 1;
		}

		if (wasruimte != null && wasruimte.equals("that")) {
			request.setAttribute("huis_nummer5", huisnummers5[week_id]);
			request.setAttribute("huis_nummer6", huisnummers6[week_id]);
			request.setAttribute("huis_nummer7", huisnummers7[week_id]);
			request.setAttribute("huis_nummer8", huisnummers8[week_id]);
		} else {
			request.setAttribute("huis_nummer1", huisnummers[week_id]);
			request.setAttribute("huis_nummer2", huisnummers2[week_id]);
			request.setAttribute("huis_nummer3", huisnummers3[week_id]);
			request.setAttribute("huis_nummer4", huisnummers4[week_id]);
		}

		request.setAttribute("time", times);
		request.setAttribute("date", dates);
		request.setAttribute("wasmachine", wasmachines);
		request.setAttribute("prikbord_messages", prikbordMessages);

		request.setAttribute("week", week);
		request.setAttribute("wasruimte", wasruimte);

		request.setAttribute("get_overview", new DatePlacer(week_id).getOverviewDate());

		hitcounter++;
		request.setAttribute("hitcounter", hitcounter);

		request.getRequestDispatcher("/homepage.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String loginForm = request.getParameter("signin");
		String appointmentForm = request.getParameter("appointment");
		String signinForm = request.getParameter("register");
		String prikbordForm = request.getParameter("prikbord");
		String removePrikbordMessageForm = request.getParameter("delete_prikbord_message");
		String removeAppointmentForm = request.getParameter("remove_appointment");

		if (loginForm != null) {
			signin(request, response);
			prikbordMessages = new PrikbordService().getPrikbordMessages();
		} else if (appointmentForm != null) {
			appointment(request, response);
			updateSchema(request, response);
		} else if (removeAppointmentForm != null) {
			removeAppointment(request, response);
			updateSchema(request, response);
		} else if (signinForm != null) {
			signup(request, response);
		} else if (prikbordForm != null) {
			createPrikbordMessage(request, response);
		} else if (removePrikbordMessageForm != null) {
			removePrikbordMessage(request, response);
		} else {
			// logout button
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
		}

		doGet(request, response);
	}

	@Override
	public void destroy() {
		Database.closeConnection();
		// save hitCounter on txt file.
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
			DbProcedureCalls washCounterDao = new DbProcedureCalls();
			request.getSession().setAttribute("wash_counter", washCounterDao.getCounter(user));
			request.getSession().setAttribute("user", signinService.getSignedinUser());
		}
	}

	private void appointment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean canWash = (Boolean) request.getSession().getAttribute("can_wash");

		if (canWash) {
			String day = request.getParameter("day");
			String time = request.getParameter("time");
			String machine = request.getParameter("machine");
			User user = (User) request.getSession().getAttribute("user");

			Appointment appointment = new Appointment();
			appointment.setDay(day);
			appointment.setTime(time);
			appointment.setWasmachine(machine);
			appointment.setGebruiker_id(user.getId());

			AppointmentService appointmentService = new AppointmentService();
			appointmentService.addAppointment(appointment);
			if (appointmentService.errorOccured()) {
				request.setAttribute("errorMessage", appointmentService.getErrorMessage());
			} else {
				int washCounter = (int) request.getSession().getAttribute("wash_counter");
				washCounter++;
				request.getSession().setAttribute("wash_counter", washCounter);
			}
		} else {
			request.setAttribute("errorMessage", "Wash Limit for this week met!");
		}
	}

	private void removeAppointment(HttpServletRequest request, HttpServletResponse response) {
		String day = request.getParameter("day");
		String time = request.getParameter("time");
		String machine = request.getParameter("machine");
		User user = (User) request.getSession().getAttribute("user");

		Appointment appointment = new Appointment();
		appointment.setDay(day);
		appointment.setTime(time);
		appointment.setWasmachine(machine);
		appointment.setGebruiker_id(user.getId());

		AppointmentService appointmentService = new AppointmentService();
		appointmentService.removeAppointment(appointment);
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

	private void updateSchema(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SchemaService schemaService = new SchemaService();

		times = schemaService.getTimes();
		dates = schemaService.getDates();
		wasmachines = schemaService.getWasmachines();

		huisnummers = schemaService.getData(1);
		huisnummers2 = schemaService.getData(2);
		huisnummers3 = schemaService.getData(3);
		huisnummers4 = schemaService.getData(4);
		huisnummers5 = schemaService.getData(5);
		huisnummers6 = schemaService.getData(6);
		huisnummers7 = schemaService.getData(7);
		huisnummers8 = schemaService.getData(8);

		schemaService.releaseResources();
	}

	private void createPrikbordMessage(HttpServletRequest request, HttpServletResponse response) {
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		String userId = request.getParameter("id");

		PrikbordMessage prikbordMessage = new PrikbordMessage();
		prikbordMessage.setTitleInput(title);
		prikbordMessage.setBodyInput(body);
		prikbordMessage.setGebruikerId(userId);

		PrikbordService prikbordService = new PrikbordService();
		prikbordService.addMessage(prikbordMessage);

		if (prikbordService.errorOccured()) {
			request.setAttribute("errorMessage", prikbordService.getErrorMessage());
		}
	}

	private void removePrikbordMessage(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("user_id");
		String messageId = request.getParameter("message_id");

		PrikbordMessage prikbordMessage = new PrikbordMessage();
		prikbordMessage.setId(messageId);
		prikbordMessage.setGebruikerId(userId);

		PrikbordService prikbordService = new PrikbordService();
		prikbordService.deactivateMessage(prikbordMessage);

		prikbordMessages = prikbordService.getPrikbordMessages();
	}

}
