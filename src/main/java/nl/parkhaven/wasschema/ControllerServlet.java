package nl.parkhaven.wasschema;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
import nl.parkhaven.wasschema.util.Database;
import nl.parkhaven.wasschema.mail.MailTimer;

@WebServlet(name = "controller", value = { "" }, loadOnStartup = 0)
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int hitcounter;

	private Map<Long, String> times;
	private NavigableMap<Long, String> dates;
	private Map<Long, String> wasmachines;
	private List<PrikbordMessage> prikbordMessages;

	private String[] huisnummers;
	private String[] huisnummers2;
	private String[] huisnummers3;
	private String[] huisnummers4;
	private String[] huisnummers5;
	private String[] huisnummers6;
	private String[] huisnummers7;
	private String[] huisnummers8;

	public ControllerServlet() throws SQLException, PropertyVetoException {
		Database.getConnection();
		SchemaService schemaService = new SchemaService();

		times = schemaService.getTimes();
		dates = schemaService.getDates();
		wasmachines = schemaService.getWasmachines();

		huisnummers = schemaService.getData("C1");
		huisnummers2 = schemaService.getData("C2");
		huisnummers3 = schemaService.getData("D1");
		huisnummers4 = schemaService.getData("D2");
		huisnummers5 = schemaService.getData("C3");
		huisnummers6 = schemaService.getData("C4");
		huisnummers7 = schemaService.getData("D4");
		huisnummers8 = schemaService.getData("D4");

		prikbordMessages = new PrikbordService().getPrikbordMessages();

		schemaService.releaseResources();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

		request.setAttribute("prikbord_messages", prikbordMessages);

		hitcounter++;
		request.setAttribute("hitcounter", hitcounter);

		request.getRequestDispatcher("/homepage.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// DatePlacer datePlacer = new DatePlacer();
		// request.setAttribute("getDays", datePlacer);

		String loginForm = request.getParameter("signin");
		String appointmentForm = request.getParameter("appointment");
		String signinForm = request.getParameter("register");
		String prikbordForm = request.getParameter("prikbord");

		if (loginForm != null) {
			signin(request, response);
		} else if (appointmentForm != null) {
			appointment(request, response);
			updateSchema(request, response);
		} else if (signinForm != null) {
			signup(request, response);
		} else if (prikbordForm != null) {
			prikbordMessage(request, response);
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
				DbProcedureCalls mailDao = new DbProcedureCalls();
				MailTimer.addSchedule(user.getEmail(), mailDao.getDateForEmail(appointment));
				// For washCounter
				int washCounter = (int) request.getSession().getAttribute("wash_counter");
				washCounter++;
				request.getSession().setAttribute("wash_counter", washCounter);
			}
		} else {
			request.setAttribute("errorMessage", "Wash Limit for this week met!");
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

		huisnummers = schemaService.getData("C1");
		huisnummers2 = schemaService.getData("C2");
		huisnummers3 = schemaService.getData("D1");
		huisnummers4 = schemaService.getData("D2");
		huisnummers5 = schemaService.getData("C3");
		huisnummers6 = schemaService.getData("C4");
		huisnummers7 = schemaService.getData("D4");
		huisnummers8 = schemaService.getData("D4");

		schemaService.releaseResources();
	}
// 		prikbordMessages = prikbordService.getPrikbordMessages(); // On the admin page, if the admin verified this message! 
	private void prikbordMessage(HttpServletRequest request, HttpServletResponse response) {
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
}
