
package nl.parkhaven.wasschema.controllers;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import nl.parkhaven.wasschema.modules.appointment.Appointment;
import nl.parkhaven.wasschema.modules.appointment.AppointmentService;
import nl.parkhaven.wasschema.modules.bulletinboard.BulletinBoardService;
import nl.parkhaven.wasschema.modules.bulletinboard.Message;
import nl.parkhaven.wasschema.modules.misc.MetaDataOperations;
import nl.parkhaven.wasschema.modules.schema.SchemaService;
import nl.parkhaven.wasschema.modules.user.ModifyUserService;
import nl.parkhaven.wasschema.modules.user.User;
import nl.parkhaven.wasschema.modules.util.Database;
import nl.parkhaven.wasschema.modules.util.DatesStringMaker;

@Controller
@RequestMapping(value = "/index.010")
public class MainController {

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
	private String[][] housenumbersWashmachine9;
	private String[][] housenumbersWashmachine10;
	private String[][] housenumbersWashmachine11;
	private String[][] housenumbersWashmachine12;

	private String[] overview;

	@PostConstruct
	private void init() {
		updateSchema();

		bulletinBoardMessages = new BulletinBoardService().getMessages();

		DatesStringMaker datesStringMaker = new DatesStringMaker();
		dates = datesStringMaker.getDates();
		overview = datesStringMaker.getOverview();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String homePage(Model model, @RequestParam(name = "week", required = false) String week,
			@RequestParam(name = "wasruimte", required = false) Integer laundryRoom, HttpSession session) {
		System.out.println("I'm doing it again!!!");

		if (session.getAttribute("user") == null) {
			System.out.println("Not here");
			return "redirect:/";
		}

		int weekId = 0;

		if (laundryRoom == null) {
			laundryRoom = 0;
		}

		if (week != null && week.equals("next")) {
			weekId = 1;
		}

		if (laundryRoom == 1) {
			model.addAttribute("huis_nummer1", housenumbersWashmachine1[weekId]);
			model.addAttribute("huis_nummer2", housenumbersWashmachine2[weekId]);
			model.addAttribute("huis_nummer3", housenumbersWashmachine3[weekId]);
			model.addAttribute("huis_nummer4", housenumbersWashmachine4[weekId]);
		} else if (laundryRoom == 2) {
			model.addAttribute("huis_nummer1", housenumbersWashmachine5[weekId]);
			model.addAttribute("huis_nummer2", housenumbersWashmachine6[weekId]);
			model.addAttribute("huis_nummer3", housenumbersWashmachine7[weekId]);
			model.addAttribute("huis_nummer4", housenumbersWashmachine8[weekId]);
		} else if (laundryRoom == 3) {
			model.addAttribute("huis_nummer1", housenumbersWashmachine9[weekId]);
			model.addAttribute("huis_nummer2", housenumbersWashmachine10[weekId]);
			model.addAttribute("huis_nummer3", housenumbersWashmachine11[weekId]);
			model.addAttribute("huis_nummer4", housenumbersWashmachine12[weekId]);
		}

		model.addAttribute("time", times);
		model.addAttribute("date", dates);
		model.addAttribute("wasmachine", washingmachines);
		model.addAttribute("prikbord_messages", bulletinBoardMessages);

		model.addAttribute("week", week);
		model.addAttribute("wasruimte", laundryRoom);

		model.addAttribute("get_overview", overview[weekId]);

		hitCounter++;
		model.addAttribute("hitcounter", hitCounter);
		model.addAttribute("totalwashcounter", totalWashCounter);

		return "afterlogin";
	}

	public void destroy() {
		MetaDataOperations miscDbOperations = new MetaDataOperations();
		miscDbOperations.insertCounter(hitCounter, "hitcounter");
		miscDbOperations.insertCounter(totalWashCounter, "washcounter");
		Database.closeDataSource();
	}

	private void setOverviewDateAndDays() {
		DatesStringMaker datesStringMaker = new DatesStringMaker();
		dates = datesStringMaker.getDates();
		overview = datesStringMaker.getOverview();
	}

	// Set a try catch here, and send to doGet(); There will be an error if
	// the user doesn't do anything for longer than 15 min!

	//

	// This was in the login method before I moved it to the
	// LoginServlet
	// I don't want to get the bulletinboard each time. (Idealy i should
	// show this after a message is accepted by the admin!
	@RequestMapping(method = RequestMethod.POST, params = { "to_servlet=addAppointment" })
	public String addAppointment(Model model, @ModelAttribute Appointment appointment, HttpSession session,
			@RequestParam("week") String week, @RequestParam("laundryRoom") Integer laundryRoom) {
		int[] washCounter = (int[]) session.getAttribute("wash_counter");
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
			User user = (User) session.getAttribute("user");
			appointment.setUserId(user.getId());

			AppointmentService appointmentService = new AppointmentService(appointment);
			appointmentService.addAppointment();

			if (appointmentService.errorOccured()) {
				model.addAttribute("errorMessage", appointmentService.getErrorMessage());
			} else {
				if (week != null && week.equals("next")) {
					washCounter[1]++;
				} else {
					washCounter[0]++;
				}
				session.setAttribute("wash_counter", washCounter);
				totalWashCounter++;
				updateSchema();
			}
		} else {
			model.addAttribute("errorMessage", "Wash Limit for this week met!");
		}
		return homePage(model, week, laundryRoom, session);
	}

	// Add The laundry room Parameter
	@RequestMapping(method = RequestMethod.POST, params = { "to_servlet=removeAppointment" })
	public String removeAppointment(Model model, @ModelAttribute Appointment appointment, HttpSession session,
			@RequestParam("week") String week, @RequestParam("laundryRoom") Integer laundryRoom) {
		User user = (User) session.getAttribute("user");
		appointment.setUserId(user.getId());

		int[] washCounter = (int[]) session.getAttribute("wash_counter");

		AppointmentService appointmentService = new AppointmentService(appointment);
		appointmentService.removeAppointment();

		if (appointmentService.errorOccured()) {
			model.addAttribute("errorMessage", appointmentService.getErrorMessage());
		} else {
			if (week != null && week.equals("next")) {
				washCounter[1]--;
			} else {
				washCounter[0]--;
			}
			session.setAttribute("wash_counter", washCounter);
			updateSchema();
		}

		return homePage(model, week, null, session);
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
		housenumbersWashmachine9 = schemaService.getData(9);
		housenumbersWashmachine10 = schemaService.getData(10);
		housenumbersWashmachine11 = schemaService.getData(11);
		housenumbersWashmachine12 = schemaService.getData(12);

		schemaService.releaseResources();
	}

	@RequestMapping(method = RequestMethod.POST, params = { "to_servlet=createMessage" })
	public String createBulletinBoardMessage(@ModelAttribute Message message) {
		BulletinBoardService bulletinBoardService = new BulletinBoardService();
		bulletinBoardService.addMessage(message);

		 return "/index.010";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "to_servlet=removeMessage" })
	public String removeBulletinBoardMessage(Model model, @ModelAttribute Message message) {
		BulletinBoardService bulletinBoardService = new BulletinBoardService();
		bulletinBoardService.deactivateMessage(message);

		bulletinBoardMessages = bulletinBoardService.getMessages();
		return homePage(model, null, null, null);
	}

	@RequestMapping(method = RequestMethod.POST, params = { "to_servlet=changeHouseNumber" })
	public String changeUserHouseNumber(Model model, HttpSession session,
			@RequestParam("houseNumber") String houseNumber) {
		User user = (User) session.getAttribute("user");
		user.setHouseNumber(houseNumber);

		ModifyUserService modifyUserService = new ModifyUserService();
		modifyUserService.changeHouseNumber(user);

		if (modifyUserService.errorOccured()) {
			model.addAttribute("errorMessage", modifyUserService.getErrorMessage());
		} else {
			updateSchema();
		}
		 return homePage(model, null, null, session);
	}

	@RequestMapping(method = RequestMethod.POST, params = { "to_servlet=changePassword" })
	public String changeUserPassword(Model model, HttpSession session, @RequestParam("password") String password) {
		User user = (User) session.getAttribute("user");
		user.setPassword(password);

		ModifyUserService modifyUserService = new ModifyUserService();
		modifyUserService.changePassword(user);

		if (modifyUserService.errorOccured()) {
			model.addAttribute("errorMessage", modifyUserService.getErrorMessage());
		}
		 return homePage(model, null, null, session);
	}

	@RequestMapping(method = RequestMethod.POST, params = { "to_servlet=deleteAccount" })
	public String deleteUserAccount(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		ModifyUserService modifyUserService = new ModifyUserService();
		modifyUserService.deleteAccount(user);

		if (modifyUserService.errorOccured()) {
			model.addAttribute("errorMessage", modifyUserService.getErrorMessage());
		} else {
			updateSchema();
		}
		return homePage(model, null, null, session);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String logout(HttpSession session) {
		// logout button
		session.invalidate();
		return "redirect:/";
	}

}
