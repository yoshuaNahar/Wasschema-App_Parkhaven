package nl.parkhaven.wasschema;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.parkhaven.wasschema.model.appointment.Appointment;
import nl.parkhaven.wasschema.model.appointment.AppointmentService;
import nl.parkhaven.wasschema.model.misc.MiscDbOperations;
import nl.parkhaven.wasschema.model.prikbord.PrikbordMessage;
import nl.parkhaven.wasschema.model.prikbord.PrikbordService;
import nl.parkhaven.wasschema.model.schema.SchemaService;
import nl.parkhaven.wasschema.model.user.LoginService;
import nl.parkhaven.wasschema.model.user.ModifyUserService;
import nl.parkhaven.wasschema.model.user.ForgotPasswordService;
import nl.parkhaven.wasschema.model.user.SignupService;
import nl.parkhaven.wasschema.model.user.User;
import nl.parkhaven.wasschema.util.Database;
import nl.parkhaven.wasschema.util.DatesStringMaker;
import nl.parkhaven.wasschema.util.MailSender;

@WebServlet(name = "controller", value = { "" }, loadOnStartup = 0)
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int hitCounter;
	private int totalWashCounter;

	private Map<Long, String> times;
	private Map<Long, String> wasmachines;
	private Map<Long, PrikbordMessage> prikbordMessages;

	private Map<Long, String> dates;

	private String[][] huisnummersWasmachine1;
	private String[][] huisnummersWasmachine2;
	private String[][] huisnummersWasmachine3;
	private String[][] huisnummersWasmachine4;
	private String[][] huisnummersWasmachine5;
	private String[][] huisnummersWasmachine6;
	private String[][] huisnummersWasmachine7;
	private String[][] huisnummersWasmachine8;

	private String[] overview;

	@Override
	public void init() {
		SchemaService schemaService = new SchemaService();

		times = schemaService.getTimes();
		wasmachines = schemaService.getWasmachines();

		huisnummersWasmachine1 = schemaService.getData(1);
		huisnummersWasmachine2 = schemaService.getData(2);
		huisnummersWasmachine3 = schemaService.getData(3);
		huisnummersWasmachine4 = schemaService.getData(4);
		huisnummersWasmachine5 = schemaService.getData(5);
		huisnummersWasmachine6 = schemaService.getData(6);
		huisnummersWasmachine7 = schemaService.getData(7);
		huisnummersWasmachine8 = schemaService.getData(8);

		schemaService.releaseResources();

		prikbordMessages = new PrikbordService().getPrikbordMessages();

		DatesStringMaker datesStringMaker = new DatesStringMaker();
		dates = datesStringMaker.getDates();
		overview = datesStringMaker.getOverview();
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
			request.setAttribute("huis_nummer1", huisnummersWasmachine5[week_id]);
			request.setAttribute("huis_nummer2", huisnummersWasmachine6[week_id]);
			request.setAttribute("huis_nummer3", huisnummersWasmachine7[week_id]);
			request.setAttribute("huis_nummer4", huisnummersWasmachine8[week_id]);
		} else {
			request.setAttribute("huis_nummer1", huisnummersWasmachine1[week_id]);
			request.setAttribute("huis_nummer2", huisnummersWasmachine2[week_id]);
			request.setAttribute("huis_nummer3", huisnummersWasmachine3[week_id]);
			request.setAttribute("huis_nummer4", huisnummersWasmachine4[week_id]);
		}

		request.setAttribute("time", times);
		request.setAttribute("date", dates);
		request.setAttribute("wasmachine", wasmachines);
		request.setAttribute("prikbord_messages", prikbordMessages);

		request.setAttribute("week", week);
		request.setAttribute("wasruimte", wasruimte);

		request.setAttribute("get_overview", overview[week_id]);

		hitCounter++;
		request.setAttribute("hitcounter", hitCounter);
		request.setAttribute("totalwashcounter", totalWashCounter);

		request.getRequestDispatcher("/homepage.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String form = request.getParameter("to_servlet");
		if (form == null) {
			form = "";
		}

		switch (form) {
		case "loginForm":
			login(request, response);
			break;
		case "appointmentForm":
			addAppointment(request, response);
			updateSchema(request, response);
			break;
		case "removeAppointmentForm":
			removeAppointment(request, response);
			updateSchema(request, response);
			break;
		case "signupForm":
			signup(request, response);
			break;
		case "createMessageForm":
			createPrikbordMessage(request, response);
			break;
		case "removeMessageForm":
			removePrikbordMessage(request, response);
			break;
		case "forgotPasswordForm":
			sendNewPassword(request, response);
			break;
		case "changeHuisnummerForm":
			changeUserHuisnummer(request, response);
			updateSchema(request, response);
			break;
		case "changePasswordForm":
			changeUserPassword(request, response);
			break;
		case "deleteAccountForm":
			deleteUserAccount(request, response);
			updateSchema(request, response);
		default:
			logout(request);
		}

		doGet(request, response);
	}

	@Override
	public void destroy() {
		MiscDbOperations finalDbOperations = new MiscDbOperations();
		finalDbOperations.storeHitCounter(hitCounter);
		finalDbOperations.storeTotalWashCounter(totalWashCounter);
		Database.closeDataSource();
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = new User();
		user.setEmail(email);
		user.setWachtwoord(password);

		LoginService loginService = new LoginService();
		loginService.login(user);
		if (loginService.errorOccured()) {
			request.setAttribute("errorMessage", loginService.getErrorMessage());
		} else {
			request.getSession().setAttribute("user", loginService.getLoggedinUser());
			MiscDbOperations washCounterDao = new MiscDbOperations();
			request.getSession().setAttribute("was_counter", washCounterDao.getWashCounter(user));
		}

		prikbordMessages = new PrikbordService().getPrikbordMessages();
	}

	private void addAppointment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String week = request.getParameter("week");
		int[] wasCounter = (int[]) request.getSession().getAttribute("was_counter");

		boolean canWash = false;

		if (week != null && week.equals("next")) {
			if (wasCounter[1] < 3) {
				canWash = true;
			}
		} else {
			if (wasCounter[0] < 3) {
				canWash = true;
			}
		}

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
				if (week != null && week.equals("next")) {
					wasCounter[1]++;
				} else {
					wasCounter[0]++;
				}
				request.getSession().setAttribute("was_counter", wasCounter);
				totalWashCounter++;
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
		int[] wasCounter = (int[]) request.getSession().getAttribute("was_counter");
		String week = request.getParameter("week");

		Appointment appointment = new Appointment();
		appointment.setDay(day);
		appointment.setTime(time);
		appointment.setWasmachine(machine);
		appointment.setGebruiker_id(user.getId());

		AppointmentService appointmentService = new AppointmentService();
		appointmentService.removeAppointment(appointment);
		if (appointmentService.errorOccured()) {
			request.setAttribute("errorMessage", appointmentService.getErrorMessage());
		} else {
			if (week != null && week.equals("next")) {
				wasCounter[1]--;
			} else {
				wasCounter[0]--;
			}
			request.getSession().setAttribute("was_counter", wasCounter);
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
		user.setHuisnummer(huisnummer.toUpperCase());

		SignupService signupService = new SignupService();
		signupService.signup(user, code);
		if (signupService.errorOccured()) {
			request.setAttribute("errorMessage", signupService.getErrorMessage());
		} else {
			login(request, response);
		}
	}

	private void updateSchema(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SchemaService schemaService = new SchemaService();

		times = schemaService.getTimes();
		wasmachines = schemaService.getWasmachines();

		huisnummersWasmachine1 = schemaService.getData(1);
		huisnummersWasmachine2 = schemaService.getData(2);
		huisnummersWasmachine3 = schemaService.getData(3);
		huisnummersWasmachine4 = schemaService.getData(4);
		huisnummersWasmachine5 = schemaService.getData(5);
		huisnummersWasmachine6 = schemaService.getData(6);
		huisnummersWasmachine7 = schemaService.getData(7);
		huisnummersWasmachine8 = schemaService.getData(8);

		schemaService.releaseResources();

		DatesStringMaker datesStringMaker = new DatesStringMaker();
		dates = datesStringMaker.getDates();
		overview = datesStringMaker.getOverview();
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

	private void sendNewPassword(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");

		User user = new User();
		user.setEmail(email);
		ForgotPasswordService forgotPasswordService = new ForgotPasswordService();
		forgotPasswordService.forgotPasswordModifyPassword(user);
		if (forgotPasswordService.errorOccured()) {
			request.setAttribute("errorMessage", forgotPasswordService.getErrorMessage());
		} else {
			MailSender mailSender = new MailSender(user);
			mailSender.sendMailContainingPassword();
		}
	}

	private void changeUserHuisnummer(HttpServletRequest request, HttpServletResponse response) {
		String huisnummer = request.getParameter("huisnummer");
		User user = (User) request.getSession().getAttribute("user");

		user.setHuisnummer(huisnummer);

		ModifyUserService modifyUserService = new ModifyUserService();
		modifyUserService.changeHousenumber(user);

		if (modifyUserService.errorOccured()) {
			request.setAttribute("errorMessage", modifyUserService.getErrorMessage());
		} else {
			logout(request);
		}

	}

	private void changeUserPassword(HttpServletRequest request, HttpServletResponse response) {
		String password = request.getParameter("password");
		User user = (User) request.getSession().getAttribute("user");

		user.setWachtwoord(password);

		ModifyUserService modifyUserService = new ModifyUserService();
		modifyUserService.changePassword(user);

		if (modifyUserService.errorOccured()) {
			request.setAttribute("errorMessage", modifyUserService.getErrorMessage());
		}
	}

	private void deleteUserAccount(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");

		ModifyUserService modifyUserService = new ModifyUserService();
		modifyUserService.deleteAccount(user);

		if (modifyUserService.errorOccured()) {
			request.setAttribute("errorMessage", modifyUserService.getErrorMessage());
		}
	}

	private void logout(HttpServletRequest request) {
		// logout button
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

}
