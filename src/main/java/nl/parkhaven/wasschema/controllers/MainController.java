
package nl.parkhaven.wasschema.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nl.parkhaven.wasschema.modules.appointment.Appointment;
import nl.parkhaven.wasschema.modules.appointment.AppointmentService;
import nl.parkhaven.wasschema.modules.bulletinboard.BulletinBoardService;
import nl.parkhaven.wasschema.modules.bulletinboard.Message;
import nl.parkhaven.wasschema.modules.misc.MetaDataOperations;
import nl.parkhaven.wasschema.modules.schema.SchemaService;
import nl.parkhaven.wasschema.modules.user.ModifyUserService;
import nl.parkhaven.wasschema.modules.user.User;
import nl.parkhaven.wasschema.modules.util.DatesStringMaker;

@Controller
@RequestMapping(value = "/index.010")
public class MainController {

	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private ModifyUserService modifyUserService;
	@Autowired
	private BulletinBoardService bulletinBoardService;
	@Autowired
	private SchemaService schemaService;
	@Autowired
	private MetaDataOperations miscDbOperations;

	private int hitCounter;
	private int totalWashCounter;

	private Map<Long, String> times;
	private Map<Long, String> machines;
	private Map<Long, Message> bulletinBoardMessages;

	private Map<Long, String> dates;

	private String[][] housenumbersMachine1;
	private String[][] housenumbersMachine2;
	private String[][] housenumbersMachine3;
	private String[][] housenumbersMachine4;
	private String[][] housenumbersMachine5;
	private String[][] housenumbersMachine6;
	private String[][] housenumbersMachine7;
	private String[][] housenumbersMachine8;
	private String[][] housenumbersMachine9;
	private String[][] housenumbersMachine10;
	private String[][] housenumbersMachine11;
	private String[][] housenumbersMachine12;

	private String[] overview;

	// @PostConstruct
	private void init() {
		updateSchema();

		bulletinBoardMessages = bulletinBoardService.getMessages();

		DatesStringMaker datesStringMaker = new DatesStringMaker();
		dates = datesStringMaker.getDates();
		overview = datesStringMaker.getOverview();
	}

	@GetMapping()
	public String homePage(@RequestParam(name = "week", required = false) String week,
			@RequestParam(name = "laundryRoom", required = false) Integer laundryRoom, HttpSession session,
			Model model) {
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
			model.addAttribute("huis_nummer1", housenumbersMachine1[weekId]);
			model.addAttribute("huis_nummer2", housenumbersMachine2[weekId]);
			model.addAttribute("huis_nummer3", housenumbersMachine3[weekId]);
			model.addAttribute("huis_nummer4", housenumbersMachine4[weekId]);
		} else if (laundryRoom == 2) {
			model.addAttribute("huis_nummer1", housenumbersMachine5[weekId]);
			model.addAttribute("huis_nummer2", housenumbersMachine6[weekId]);
			model.addAttribute("huis_nummer3", housenumbersMachine7[weekId]);
			model.addAttribute("huis_nummer4", housenumbersMachine8[weekId]);
		} else if (laundryRoom == 3) {
			model.addAttribute("huis_nummer1", housenumbersMachine9[weekId]);
			model.addAttribute("huis_nummer2", housenumbersMachine10[weekId]);
			model.addAttribute("huis_nummer3", housenumbersMachine11[weekId]);
			model.addAttribute("huis_nummer4", housenumbersMachine12[weekId]);
		}

		model.addAttribute("time", times);
		model.addAttribute("date", dates);
		model.addAttribute("wasmachine", machines);
		model.addAttribute("prikbord_messages", bulletinBoardMessages);

		model.addAttribute("week", week);
		model.addAttribute("laundryRoom", laundryRoom);

		model.addAttribute("get_overview", overview[weekId]);

		hitCounter++;
		model.addAttribute("hitcounter", hitCounter);
		model.addAttribute("totalwashcounter", totalWashCounter);

		return "afterlogin";
	}

	public void destroy() {
		miscDbOperations.insertCounter(hitCounter, "hitcounter");
		miscDbOperations.insertCounter(totalWashCounter, "washcounter");
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
	@PostMapping(params = { "to_servlet=addAppointment" })
	public String addAppointment(@ModelAttribute Appointment appointment, HttpSession session,
			@RequestParam("week") String week, @RequestParam("laundryRoom") Integer laundryRoom, Model model) {
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

			appointmentService.addAppointment(appointment);

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
		return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom;
	}

	// Add The laundry room Parameter
	@PostMapping(params = { "to_servlet=removeAppointment" })
	public String removeAppointment(@ModelAttribute Appointment appointment, HttpSession session,
			@RequestParam("week") String week, @RequestParam("laundryRoom") Integer laundryRoom, Model model) {
		User user = (User) session.getAttribute("user");
		appointment.setUserId(user.getId());

		System.out.println("3");
		int[] washCounter = (int[]) session.getAttribute("wash_counter");

		appointmentService.removeAppointment(appointment);
		System.out.println("4");

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

			System.out.println("5");

		}
		System.out.println("6");

		return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom;
	}

	private void updateSchema() {
		times = schemaService.getTimes();
		machines = schemaService.getWashingMachines();

		housenumbersMachine1 = schemaService.getData(1);
		housenumbersMachine2 = schemaService.getData(2);
		housenumbersMachine3 = schemaService.getData(3);
		housenumbersMachine4 = schemaService.getData(4);
		housenumbersMachine5 = schemaService.getData(5);
		housenumbersMachine6 = schemaService.getData(6);
		housenumbersMachine7 = schemaService.getData(7);
		housenumbersMachine8 = schemaService.getData(8);
		housenumbersMachine9 = schemaService.getData(9);
		housenumbersMachine10 = schemaService.getData(10);
		housenumbersMachine11 = schemaService.getData(11);
		housenumbersMachine12 = schemaService.getData(12);
	}

	@PostMapping(params = { "to_servlet=createMessage" })
	public String createBulletinBoardMessage(@ModelAttribute Message message, @RequestParam("week") String week,
			@RequestParam("laundryRoom") Integer laundryRoom) {
		bulletinBoardService.addMessage(message);

		return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom;
	}

	@PostMapping(params = { "to_servlet=removeMessage" })
	public String removeBulletinBoardMessage(Model model, @ModelAttribute Message message,
			@RequestParam("week") String week, @RequestParam("laundryRoom") Integer laundryRoom) {
		bulletinBoardService.deactivateMessage(message);

		bulletinBoardMessages = bulletinBoardService.getMessages();
		return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom;
	}

	@PostMapping(params = { "to_servlet=changeHouseNumber" })
	public String changeUserHouseNumber(@RequestParam("houseNumber") String houseNumber,
			@RequestParam("week") String week, @RequestParam("laundryRoom") Integer laundryRoom, HttpSession session,
			Model model) {
		User user = (User) session.getAttribute("user");
		user.setHouseNumber(houseNumber);

		modifyUserService.changeHouseNumber(user);

		if (modifyUserService.errorOccured()) {
			model.addAttribute("errorMessage", modifyUserService.getErrorMessage());
		} else {
			updateSchema();
		}
		return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom;
	}

	@PostMapping(params = { "to_servlet=changePassword" })
	public String changeUserPassword(@RequestParam("password") String password, @RequestParam("week") String week,
			@RequestParam("laundryRoom") Integer laundryRoom, HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		user.setPassword(password);

		modifyUserService.changePassword(user);

		if (modifyUserService.errorOccured()) {
			model.addAttribute("errorMessage", modifyUserService.getErrorMessage());
		}
		return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom;
	}

	@PostMapping(params = { "to_servlet=deleteAccount" })
	public String deleteUserAccount(@RequestParam("week") String week, @RequestParam("laundryRoom") Integer laundryRoom,
			HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");

		modifyUserService.deleteAccount(user);

		if (modifyUserService.errorOccured()) {
			model.addAttribute("errorMessage", modifyUserService.getErrorMessage());
		} else {
			updateSchema();
		}
		return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom;
	}

	@PostMapping()
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

}
