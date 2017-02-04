package nl.parkhaven.wasschema;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.parkhaven.wasschema.component.appointment.Appointment;
import nl.parkhaven.wasschema.component.appointment.AppointmentService;
import nl.parkhaven.wasschema.component.bulletinboard.Message;
import nl.parkhaven.wasschema.component.bulletinboard.BulletinBoardService;
import nl.parkhaven.wasschema.component.misc.MetaDataOperations;
import nl.parkhaven.wasschema.component.schema.SchemaService;
import nl.parkhaven.wasschema.component.user.ForgotPasswordService;
import nl.parkhaven.wasschema.component.user.LoginService;
import nl.parkhaven.wasschema.component.user.ModifyUserService;
import nl.parkhaven.wasschema.component.user.SignupService;
import nl.parkhaven.wasschema.component.user.User;
import nl.parkhaven.wasschema.util.Database;
import nl.parkhaven.wasschema.util.DatesStringMaker;
import nl.parkhaven.wasschema.util.MailSender;

@WebServlet(name = "controller", value = { "" }, loadOnStartup = 0)
public class ControllerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private int hitCounter;
	private int totalWashCounter;

	private Map<Long, String> times;
	private Map<Long, String> washingmachines;
	private Map<Long, Message> bulletinBoardMessages;

	private Map<Long, String> dates;

	private String[][] housenumbersWashmachine1;
	private String[][] housenumbersWashmachine2;
	private String[][] housenumbersWashmachine3;
	private String[][] housenumbersWashmachine4;
	private String[][] housenumbersWashmachine5;
	private String[][] housenumbersWashmachine6;
	private String[][] housenumbersWashmachine7;
	private String[][] housenumbersWashmachine8;

	private String[] overview;

	@Override
	public void init() {
		updateSchema();

		bulletinBoardMessages = new BulletinBoardService().getMessages();

		DatesStringMaker datesStringMaker = new DatesStringMaker();
		dates = datesStringMaker.getDates();
		overview = datesStringMaker.getOverview();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String week = request.getParameter("week");
		String washroom = request.getParameter("wasruimte");
		int week_id = 0;

		if (week != null && week.equals("next")) {
			week_id = 1;
		}

		if (washroom != null && washroom.equals("that")) {
			request.setAttribute("huis_nummer1", housenumbersWashmachine5[week_id]);
			request.setAttribute("huis_nummer2", housenumbersWashmachine6[week_id]);
			request.setAttribute("huis_nummer3", housenumbersWashmachine7[week_id]);
			request.setAttribute("huis_nummer4", housenumbersWashmachine8[week_id]);
		} else {
			request.setAttribute("huis_nummer1", housenumbersWashmachine1[week_id]);
			request.setAttribute("huis_nummer2", housenumbersWashmachine2[week_id]);
			request.setAttribute("huis_nummer3", housenumbersWashmachine3[week_id]);
			request.setAttribute("huis_nummer4", housenumbersWashmachine4[week_id]);
		}

		request.setAttribute("time", times);
		request.setAttribute("date", dates);
		request.setAttribute("wasmachine", washingmachines);
		request.setAttribute("prikbord_messages", bulletinBoardMessages);

		request.setAttribute("week", week);
		request.setAttribute("wasruimte", washroom);

		request.setAttribute("get_overview", overview[week_id]);

		hitCounter++;
		request.setAttribute("hitcounter", hitCounter);
		request.setAttribute("totalwashcounter", totalWashCounter);

		request.getRequestDispatcher("/homepage.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String form = request.getParameter("to_servlet");
		if (form == null) {
			form = "";
		}

		// Set a try catch here, and send to doGet(); There will be an error if the user doesn't do anything for longer than 15 min!
		switch (form) {
			case "loginForm":
				login(request, response);
				setOverviewDateAndDays();
				break;
			case "appointmentForm":
				addAppointment(request, response);
				updateSchema();
				break;
			case "removeAppointmentForm":
				removeAppointment(request, response);
				updateSchema();
				break;
			case "signupForm":
				signup(request, response);
				break;
			case "createMessageForm":
				createPrikbordMessage(request, response);
				break;
			case "removeMessageForm":
				removeBulletinBoardMessage(request, response);
				break;
			case "forgotPasswordForm":
				sendNewPassword(request, response);
				break;
			case "changeHuisnummerForm":
				changeUserHouseNumber(request, response);
				updateSchema();
				break;
			case "changePasswordForm":
				changeUserPassword(request, response);
				break;
			case "deleteAccountForm":
				deleteUserAccount(request, response);
				updateSchema();
			default:
				logout(request);
		}

		doGet(request, response);
	}

	@Override
	public void destroy() {
		MetaDataOperations miscDbOperations = new MetaDataOperations();
		miscDbOperations.insertCounter(hitCounter, "hitcounter");
		miscDbOperations.insertCounter(totalWashCounter, "washcounter");
		Database.closeDataSource();
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = new User();
		user.setEmail(email);
		user.setPassword(password);

		LoginService loginService = new LoginService(user);
		loginService.login();

		if (loginService.errorOccured()) {
			request.setAttribute("errorMessage", loginService.getErrorMessage());
		} else {
			request.getSession().setAttribute("user", loginService.getUser());
			request.getSession().setAttribute("was_counter", loginService.getWashCounter());
		}

		bulletinBoardMessages = new BulletinBoardService().getMessages();
	}

	private void setOverviewDateAndDays() {
		DatesStringMaker datesStringMaker = new DatesStringMaker();
		dates = datesStringMaker.getDates();
		overview = datesStringMaker.getOverview();
	}

	private void addAppointment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String week = request.getParameter("week");
		int[] washCounter = (int[]) request.getSession().getAttribute("was_counter");

		boolean canWash = false;

		if (week != null && week.equals("next")) {
			if (washCounter[1] < 3) {
				canWash = true;
			}
		} else {
			if (washCounter[0] < 3) {
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
			appointment.setWashingMachine(machine);
			appointment.setUserId(user.getId());

			AppointmentService appointmentService = new AppointmentService(appointment);
			appointmentService.addAppointment();

			if (appointmentService.errorOccured()) {
				request.setAttribute("errorMessage", appointmentService.getErrorMessage());
			} else {
				if (week != null && week.equals("next")) {
					washCounter[1]++;
				} else {
					washCounter[0]++;
				}
				request.getSession().setAttribute("was_counter", washCounter);
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
		int[] washCounter = (int[]) request.getSession().getAttribute("was_counter");
		String week = request.getParameter("week");

		Appointment appointment = new Appointment();
		appointment.setDay(day);
		appointment.setTime(time);
		appointment.setWashingMachine(machine);
		appointment.setUserId(user.getId());

		AppointmentService appointmentService = new AppointmentService(appointment);
		appointmentService.removeAppointment();

		if (appointmentService.errorOccured()) {
			request.setAttribute("errorMessage", appointmentService.getErrorMessage());
		} else {
			if (week != null && week.equals("next")) {
				washCounter[1]--;
			} else {
				washCounter[0]--;
			}
			request.getSession().setAttribute("was_counter", washCounter);
		}
	}

	private void signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String huisnummer = request.getParameter("huisnummer");
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String code = request.getParameter("sharedcode");

		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setHouseNumber(huisnummer.toUpperCase());

		SignupService signupService = new SignupService(user, code);
		signupService.signup();

		if (signupService.errorOccured()) {
			request.setAttribute("errorMessage", signupService.getErrorMessage());
		} else {
			login(request, response);
		}
	}

	private void updateSchema() {
		SchemaService schemaService = new SchemaService();

		times = schemaService.getTimes();
		washingmachines = schemaService.getWashingMachines();

		housenumbersWashmachine1 = schemaService.getData(1);
		housenumbersWashmachine2 = schemaService.getData(2);
		housenumbersWashmachine3 = schemaService.getData(3);
		housenumbersWashmachine4 = schemaService.getData(4);
		housenumbersWashmachine5 = schemaService.getData(5);
		housenumbersWashmachine6 = schemaService.getData(6);
		housenumbersWashmachine7 = schemaService.getData(7);
		housenumbersWashmachine8 = schemaService.getData(8);

		schemaService.releaseResources();
	}

	private void createPrikbordMessage(HttpServletRequest request, HttpServletResponse response) {
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		String userId = request.getParameter("id");

		Message bulletinBoardMessage = new Message();
		bulletinBoardMessage.setTitleInput(title);
		bulletinBoardMessage.setBodyInput(body);
		bulletinBoardMessage.setUserId(userId);

		BulletinBoardService bulletinBoardService = new BulletinBoardService();
		bulletinBoardService.addMessage(bulletinBoardMessage);
	}

	private void removeBulletinBoardMessage(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("user_id");
		String messageId = request.getParameter("message_id");

		Message bulletinBoardMessage = new Message();
		bulletinBoardMessage.setId(messageId);
		bulletinBoardMessage.setUserId(userId);

		BulletinBoardService bulletinBoardService = new BulletinBoardService();
		bulletinBoardService.deactivateMessage(bulletinBoardMessage);

		bulletinBoardMessages = bulletinBoardService.getMessages();
	}

	private void sendNewPassword(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");

		User user = new User();
		user.setEmail(email);

		ForgotPasswordService forgotPasswordService = new ForgotPasswordService(user);
		forgotPasswordService.setRandomPasswordForUser();

		if (forgotPasswordService.errorOccured()) {
			request.setAttribute("errorMessage", forgotPasswordService.getErrorMessage());
		} else {
			MailSender mailSender = new MailSender(user);
			mailSender.sendMailContainingPassword();
		}
	}

	private void changeUserHouseNumber(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		String houseNumber = request.getParameter("huisnummer");

		user.setHouseNumber(houseNumber);

		ModifyUserService modifyUserService = new ModifyUserService();
		modifyUserService.changeHouseNumber(user);

		if (modifyUserService.errorOccured()) {
			request.setAttribute("errorMessage", modifyUserService.getErrorMessage());
		} else {
			logout(request);
		}
	}

	private void changeUserPassword(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		String password = request.getParameter("password");

		user.setPassword(password);

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
