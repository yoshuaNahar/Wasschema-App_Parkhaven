package nl.parkhaven.wasschema.controllers;

import java.util.Map;
import static java.util.Objects.isNull;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nl.parkhaven.wasschema.modules.bulletinboard.BulletinBoardService;
import nl.parkhaven.wasschema.modules.bulletinboard.Message;
import nl.parkhaven.wasschema.modules.user.ModifyUserService;
import nl.parkhaven.wasschema.modules.user.User;

@Controller
@RequestMapping(value = "/admin.010")
public class AdminController {

	private ModifyUserService modifyUserService;
	private BulletinBoardService bulletinBoardService;

	private Map<Long, Message> bulletinBoardMessages;

	@Autowired
	public AdminController(ModifyUserService modifyUserService, BulletinBoardService bulletinBoardService) {
		this.modifyUserService = modifyUserService;
		this.bulletinBoardService = bulletinBoardService;
	}

	@GetMapping
	public String adminPage(HttpSession session, Model model) {
		if (isNull(session.getAttribute("user"))) {
			return "redirect:/";
		}
		bulletinBoardMessages = bulletinBoardService.getPendingMessages();
		model.addAttribute("prikbord_messages", bulletinBoardMessages);
		return "admin";
	}

	@PostMapping(params = { "to_servlet=acceptMessage" })
	public String handleMessage(@ModelAttribute Message message,
			@RequestParam("messageAccepted") Boolean messageAccepted) {
		if (messageAccepted) {
			bulletinBoardService.acceptPendingMessage(message);
		} else {
			bulletinBoardService.deactivateMessage(message);
		}
		return "redirect:/admin.010";
	}

	@PostMapping(params = { "to_servlet=getAllUsers" })
	public String showAllUsers(Model model) {
		model.addAttribute("users", modifyUserService.getAllUsers());
		return "admin";
	}

	@PostMapping(params = { "to_servlet=deleteSelectedUser" })
	public String deleteSelectedUser(@ModelAttribute User user, Model model) {
		modifyUserService.deleteAccount(user);
		return showAllUsers(model);
	}

}
