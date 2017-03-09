
package nl.parkhaven.wasschema.controllers;

import java.lang.reflect.Type;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import nl.parkhaven.wasschema.modules.appointment.Appointment;
import nl.parkhaven.wasschema.modules.appointment.AppointmentService;
import nl.parkhaven.wasschema.modules.bulletinboard.BulletinBoardService;
import nl.parkhaven.wasschema.modules.bulletinboard.Message;
import nl.parkhaven.wasschema.modules.misc.MetaDataDao;
import nl.parkhaven.wasschema.modules.schema.SchemaService;
import nl.parkhaven.wasschema.modules.user.ModifyUserService;
import nl.parkhaven.wasschema.modules.user.User;
import nl.parkhaven.wasschema.modules.util.DatesStringMaker;
import nl.parkhaven.wasschema.modules.util.Misc;

@Controller
@RequestMapping(value = "/index.010")
public class MainController {

	private AppointmentService appointmentService;
	private ModifyUserService modifyUserService;
	private BulletinBoardService bulletinBoardService;
	private SchemaService schemaService;
	private MetaDataDao metaData;

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

	@Autowired
	public MainController(AppointmentService appointmentService, ModifyUserService modifyUserService,
			BulletinBoardService bulletinBoardService, SchemaService schemaService, MetaDataDao miscDbOperations) {
		this.appointmentService = appointmentService;
		this.modifyUserService = modifyUserService;
		this.bulletinBoardService = bulletinBoardService;
		this.schemaService = schemaService;
		this.metaData = miscDbOperations;
		updateSchema();
		times = schemaService.getTimes();
		machines = schemaService.getWashingMachines();
	}

	@GetMapping("/loggedin")
	public String loggedIn() {
		DatesStringMaker datesStringMaker = new DatesStringMaker();
		dates = datesStringMaker.getDates();
		overview = datesStringMaker.getOverview();
		bulletinBoardMessages = bulletinBoardService.getMessages(); // If the admin excepts a message I can't let this class refresh the messages
		return "redirect:/index.010";
	}

	@GetMapping()
	public String homePage(@RequestParam(name = "week", required = false) String week,
			@RequestParam(name = "laundryRoom", required = false) Integer laundryRoom, HttpSession session,
			Model model) {
		if (session.getAttribute("user") == null) {
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

		//		model.addAttribute("week", week);
		model.addAttribute("laundryRoom", laundryRoom);

		model.addAttribute("get_overview", overview[weekId]);

		hitCounter++;
		model.addAttribute("hitcounter", hitCounter);
		model.addAttribute("totalwashcounter", totalWashCounter);

		System.out.println(model.containsAttribute("week"));
		return "afterlogin";
	}

	@PreDestroy
	public void destroy() {
		metaData.insertCounter(hitCounter, "hitcounter");
		metaData.insertCounter(totalWashCounter, "washcounter");
	}

	@PostMapping(params = { "to_servlet=addAppointment" })
	public String addAppointment(@ModelAttribute Appointment appointment, HttpSession session,
			@RequestParam("week") String week, @RequestParam("laundryRoom") Integer laundryRoom,
			@RequestParam("machinetype") String machineType, RedirectAttributes redirectAttrs) {
		User user = (User) session.getAttribute("user"); // Need this to couple the app to the user id
		appointment.setUserId(user.getId());

		String washCounterJson = (String) session.getAttribute("wash_counter");
		Type type = new TypeToken<Map<String, Integer>>() {}.getType();
		Map<String, Integer> washCounter = new Gson().fromJson(washCounterJson, type);

		boolean canWash = false;

		redirectAttrs.addFlashAttribute("week", week);
		redirectAttrs.addFlashAttribute("laundryRoom", laundryRoom);

		if (week == null || week.isEmpty()) {
			week = "current";
		}
		int amountOfWashes = washCounter.get(week + machineType);
		if (amountOfWashes < 3) {
			canWash = true;
		}
		if (canWash) {
			if (appointmentService.dateFree(appointment)) {
				appointmentService.addAppointment(appointment);
				washCounter.replace(week + machineType, ++amountOfWashes);
				session.setAttribute("wash_counter", new Gson().toJson(washCounter));
				totalWashCounter++;
				updateSchema();
			} else {
				redirectAttrs.addFlashAttribute("errorMessage", AppointmentService.DATE_PASSED_OR_TAKEN);
			}
		} else {
			redirectAttrs.addFlashAttribute("errorMessage", AppointmentService.WASH_LIMIT_MET);
		}
		return "redirect:/index.010";
	}

	@PostMapping(params = { "to_servlet=removeAppointment" })
	public String removeAppointment(@ModelAttribute Appointment appointment, HttpSession session,
			@RequestParam("week") String week, @RequestParam("laundryRoom") Integer laundryRoom,
			@RequestParam("machinetype") String machineType, Model model) {
		User user = (User) session.getAttribute("user");
		appointment.setUserId(user.getId());

		String washCounterJson = (String) session.getAttribute("wash_counter");
		Type type = new TypeToken<Map<String, Integer>>() {}.getType();
		Map<String, Integer> washCounter = new Gson().fromJson(washCounterJson, type);

		if (week == null || week.isEmpty()) {
			week = "current";
		}

		int amountOfWashes = washCounter.get(week + machineType);

		if (appointmentService.removeAppointment(appointment)) {
			washCounter.replace(week + machineType, --amountOfWashes);
			session.setAttribute("wash_counter", new Gson().toJson(washCounter));
			updateSchema();
		} else {
			model.addAttribute("errorMessage", AppointmentService.REMOVE_APPOINTMENT_FAILED);
		}

		return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom;
	}

	private void updateSchema() {
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
		bulletinBoardMessages = bulletinBoardService.getMessages();

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
		if (Misc.isHouseNumberValid(houseNumber)) {
			User user = (User) session.getAttribute("user");
			user.setHouseNumber(houseNumber);
			if (modifyUserService.changeHouseNumber(user)) {
				updateSchema();
			} else {
				model.addAttribute("errorMessage", ModifyUserService.HOUSENUMBER_TAKEN);
			}
		} else {
			model.addAttribute("errorMessage", ModifyUserService.INVALID_HOUSENUMBER);
		}
		return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom;
	}

	@PostMapping(params = { "to_servlet=changePassword" })
	public String changeUserPassword(@RequestParam("password") String password, @RequestParam("week") String week,
			@RequestParam("laundryRoom") Integer laundryRoom, HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		user.setPassword(password);
		modifyUserService.changePassword(user);

		return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom;
	}

	@PostMapping(params = { "to_servlet=deleteAccount" })
	public String deleteUserAccount(@RequestParam("week") String week, @RequestParam("laundryRoom") Integer laundryRoom,
			HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		modifyUserService.deleteAccount(user);
		session.invalidate();

		return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom;
	}

	@PostMapping()
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

}
