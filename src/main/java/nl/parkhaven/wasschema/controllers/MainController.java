package nl.parkhaven.wasschema.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpSession;
import nl.parkhaven.wasschema.modules.appointment.Appointment;
import nl.parkhaven.wasschema.modules.appointment.AppointmentService;
import nl.parkhaven.wasschema.modules.bulletinboard.BulletinBoardService;
import nl.parkhaven.wasschema.modules.bulletinboard.Message;
import nl.parkhaven.wasschema.modules.misc.MetaDataDao;
import nl.parkhaven.wasschema.modules.schema.SchemaService;
import nl.parkhaven.wasschema.modules.user.ModifyUserService;
import nl.parkhaven.wasschema.modules.user.User;
import nl.parkhaven.wasschema.modules.util.DatesStringMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/index.010")
public class MainController {

  // TODO: Remove after 24 juli. Used to check these laundryMachines to not place app
  private static int[] laundryMachinesWhereAppointmentMusntBeMade = new int[] {1, 2, 3, 4, 9, 10,
      11, 12};
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
  private String[] weekRange;
  private int[] weekNumber;

  // If the user is inactive for 15min his session will automatically expire.
  // Doing anything on the main page will result into a redirect to the login page.
  // I currently have not implemented a way to turn the message into an alert

  @Autowired
  public MainController(AppointmentService appointmentService, ModifyUserService modifyUserService,
      BulletinBoardService bulletinBoardService, SchemaService schemaService,
      MetaDataDao miscDbOperations) {
    this.appointmentService = appointmentService;
    this.modifyUserService = modifyUserService;
    this.bulletinBoardService = bulletinBoardService;
    this.schemaService = schemaService;
    this.metaData = miscDbOperations;

    updateSchema();
    times = schemaService.getTimes();
    machines = schemaService.getWashingMachines();

    hitCounter = metaData.getCounterSum("hitcounter");
    totalWashCounter = metaData.getCounterSum("washcounter");
  }

  @GetMapping("/loggedin")
  public String loggedIn() {
    DatesStringMaker datesStringMaker = new DatesStringMaker();
    dates = datesStringMaker.getDates();
    weekRange = datesStringMaker.getWeekRange();
    weekNumber = datesStringMaker.getWeekNumber();

    bulletinBoardMessages = bulletinBoardService.getMessages();
        /* If the admin accepts a message I have no trigger code to call this method this method.
         * This method, to refresh the bulletinBoard will only be called when a user logs in.
		 */

    // I need to put this here only because I have nothing in the code to deal with the weekly db event at 00:05 that removes values...
    updateSchema();
    times = schemaService.getTimes();
    machines = schemaService.getWashingMachines();

    // Based on your sharedPassword and the Remember Laundry Room, I will be able to automatically open the correct laundry room.
    return "redirect:/index.010";
  }

  @GetMapping()
  public String homePage(@RequestParam(name = "week", required = false) String week,
      @RequestParam(name = "laundryRoom", required = false) Integer laundryRoom,
      HttpSession session,
      Model model) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return sessionExpired();
    }

    int weekId = 0;

    if (week != null && week.equals("next")) {
      weekId = 1;
    }

    // User checked remember my laundry room, so that their laundry room is displayed on login.
    if (laundryRoom == null && user.isRememberLaundryRoomChecked()) {
      laundryRoom = Integer.valueOf(user.getSharedPassword());
    } else if (laundryRoom == null) {
      laundryRoom = 0;
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

    model.addAttribute("get_weekRange", weekRange[weekId]);
    model.addAttribute("get_weekNumber", weekNumber[weekId]);

    hitCounter++;
    model.addAttribute("hitcounter", hitCounter);
    model.addAttribute("totalwashcounter", totalWashCounter);

    return "afterlogin";
  }

  @PreDestroy
  public void destroy() {
    metaData.insertCounter(hitCounter, "hitcounter");
    metaData.insertCounter(totalWashCounter, "washcounter");
  }

  @PostMapping(params = {"to_servlet=addAppointment"})
  public String addAppointment(@ModelAttribute Appointment appointment, HttpSession session,
      @RequestParam("week") String week, @RequestParam("laundryRoom") Integer laundryRoom,
      @RequestParam("machinetype") String machineType) {
    // TODO: remove after 24 juli (when people can make an appointment)
    System.out.println(appointment.getMachine());
    System.out.println(laundryRoom);
    if (laundryRoom == 1 || laundryRoom == 3 || Arrays
        .asList(laundryMachinesWhereAppointmentMusntBeMade).contains(appointment.getMachine())) {
      return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom + "&message="
          + "As mentioned in the mail, creating an appointment is only allowed after juli 24.";
    }

    User user = (User) session.getAttribute("user"); // Need this to couple the app to the user id
    if (user == null) {
      return sessionExpired();
    }
    appointment.setUserId(user.getId());

    String washCounterJson = (String) session.getAttribute("wash_counter");
    Type type = new TypeToken<Map<String, Integer>>() {
    }.getType();
    Map<String, Integer> washCounter = new Gson().fromJson(washCounterJson, type);

    boolean canWash = false;

    if (week == null || week.isEmpty()) {
      week = "current";
    }

    machineType = setMachineType(appointment.getMachine());

    int amountOfWashes = washCounter.get(week + machineType);
    if (amountOfWashes < 3) {
      canWash = true;
    }

    String errorMessage = "";

    if (canWash) {
      if (appointmentService.dateFree(appointment)) {
        appointmentService.addAppointment(appointment);
        washCounter.replace(week + machineType, ++amountOfWashes);
        session.setAttribute("wash_counter", new Gson().toJson(washCounter));
        totalWashCounter++;
        updateSchema();
      } else {
        errorMessage = AppointmentService.DATE_PASSED_OR_TAKEN;
      }
    } else {
      errorMessage = AppointmentService.WASH_LIMIT_MET;
    }
    return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom + "&message="
        + errorMessage;
  }

  @PostMapping(params = {"to_servlet=removeAppointment"})
  public String removeAppointment(@ModelAttribute Appointment appointment, HttpSession session,
      @RequestParam("week") String week, @RequestParam("laundryRoom") Integer laundryRoom,
      @RequestParam("machinetype") String machineType, Model model) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return sessionExpired();
    }
    appointment.setUserId(user.getId());

    String washCounterJson = (String) session.getAttribute("wash_counter");
    Type type = new TypeToken<Map<String, Integer>>() {
    }.getType();
    Map<String, Integer> washCounter = new Gson().fromJson(washCounterJson, type);

    if (week == null || week.isEmpty()) {
      week = "current";
    }

    machineType = setMachineType(appointment.getMachine());

    int amountOfWashes = washCounter.get(week + machineType);

    String errorMessage = "";

    if (appointmentService.removeAppointment(appointment)) {
      washCounter.replace(week + machineType, --amountOfWashes);
      session.setAttribute("wash_counter", new Gson().toJson(washCounter));
      updateSchema();
    } else {
      errorMessage = AppointmentService.REMOVE_APPOINTMENT_FAILED;
    }

    return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom + "&message="
        + errorMessage;
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

  @PostMapping(params = {"to_servlet=createMessage"})
  public String createBulletinBoardMessage(@ModelAttribute Message message,
      @RequestParam("week") String week,
      @RequestParam("laundryRoom") Integer laundryRoom,
      HttpSession session) {
    if (session.getAttribute("user") == null) {
      return sessionExpired();
    }
    bulletinBoardService.addMessage(message);
    bulletinBoardMessages = bulletinBoardService.getMessages();

    return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom + "&message="
        + BulletinBoardService.MESSAGE_CREATED;
  }

  @PostMapping(params = {"to_servlet=removeMessage"})
  public String removeBulletinBoardMessage(Model model, @ModelAttribute Message message,
      @RequestParam("week") String week, @RequestParam("laundryRoom") Integer laundryRoom,
      HttpSession session) {
    if (session.getAttribute("user") == null) {
      return sessionExpired();
    }
    bulletinBoardService.deactivateMessage(message);
    bulletinBoardMessages = bulletinBoardService.getMessages();

    return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom;
  }

  // IM HERE
  @PostMapping(params = {"to_servlet=rememberLaundryRoom"})
  public String rememberLaundryRoom(
      @RequestParam(name = "rememberLaundryRoomChecked", required = false) boolean rememberLaundryRoomChecked,
      @RequestParam("week") String week,
      @RequestParam("laundryRoom") Integer laundryRoom, HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return sessionExpired();
    }

    user.setRememberLaundryRoomChecked(rememberLaundryRoomChecked);
    modifyUserService.changeRememberLaundryRoom(user);

    String message = "";
    if (rememberLaundryRoomChecked) {
      message = ModifyUserService.LAUNDRY_ROOM_REMEMBERED;
    }

    return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom + "&message="
        + message;
  }

  @PostMapping(params = {"to_servlet=changePassword"})
  public String changeUserPassword(@RequestParam("password") String password,
      @RequestParam("week") String week,
      @RequestParam("laundryRoom") Integer laundryRoom, HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return sessionExpired();
    }

    user.setPassword(password);
    modifyUserService.changePassword(user);

    return "redirect:/index.010?week=" + week + "&laundryRoom=" + laundryRoom + "&message="
        + ModifyUserService.PASSWORD_CHANGED;
  }

  @PostMapping(params = {"to_servlet=deleteAccount"})
  public String deleteUserAccount(HttpSession session) {
    User user = (User) session.getAttribute("user");
    modifyUserService.deleteAccount(user);
    session.invalidate();

    return "redirect:/";
  }

  @PostMapping()
  public String logout(HttpSession session, Model model) {
    session.invalidate();
    return "redirect:/";
  }

  // adding this again, because users can "borrow" washcounters if they first press on dryers and then choose a washer.
  private String setMachineType(int appointmentMachine) {
    if (appointmentMachine % 4 == 1 || appointmentMachine % 4 == 2) {
      return "wash";
    }
    return "dry";
  }

  private String sessionExpired() {
    return "redirect:/?session=expired";
  }

}
