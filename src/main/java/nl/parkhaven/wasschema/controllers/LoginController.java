package nl.parkhaven.wasschema.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import nl.parkhaven.wasschema.modules.user.LoginService;
import nl.parkhaven.wasschema.modules.user.User;
import nl.parkhaven.wasschema.modules.util.MailSenderService;

@Controller
@RequestMapping(value = "/")
public class LoginController {

	private LoginService loginService;
	private MailSenderService mailSenderService; 

	@Autowired
	public LoginController(LoginService loginService, MailSenderService mailSenderService) {
		this.loginService = loginService;
		this.mailSenderService = mailSenderService;
	}

	@GetMapping()
	public String getLoginPage() {
		return "login";
	}

	@PostMapping(params = { "to_servlet=login" })
	public String login(@ModelAttribute User user, HttpSession session, Model model) {
		if (loginService.loginCredentialsValid(user)) {
			User loggedInUser = loginService.login(user);
			if (loggedInUser == null) {
				model.addAttribute("message", LoginService.LOGIN_CREDENTIALS_INVALID);
			} else {
				session.setAttribute("user", loggedInUser);
				session.setAttribute("wash_counter", loginService.getWashCounter(loggedInUser));
				return "redirect:/index.010";
			}
		} else {
			model.addAttribute("message", LoginService.NOT_ALL_REQUIRED_FIELDS_FILLED);
		}
		return "login";
	}

	@PostMapping(params = { "to_servlet=signup" })
	public String signup(@ModelAttribute User user, HttpSession session, Model model) {
		if (loginService.signupCredentialsValid(user)) {
			if (loginService.created(user)) {
				return login(user, session, model);
			} else {
				model.addAttribute("message", LoginService.USER_WITH_HOUSENUMBER_ALREADY_EXISTS);
			}
		} else {
			if (loginService.checkSharedPasswordValid(user.getSharedPassword())) {
				model.addAttribute("message", LoginService.INCORRECT_SHARED_PASSWORD);
			} else {
				model.addAttribute("message", LoginService.NOT_ALL_REQUIRED_FIELDS_FILLED);
			}
		}
		return "login";
	}

	@PostMapping(params = { "to_servlet=forgotPassword" })
	public String forgotPassword(@ModelAttribute User user, Model model) {
		if (loginService.emailValid(user)) {
			loginService.setRandomPasswordFor(user);
			mailSenderService.sendMailContainingPasswordTo(user);
			model.addAttribute("message", "A mail has been sent to your email adres.");
		} else {
			model.addAttribute("message", LoginService.NOT_ALL_REQUIRED_FIELDS_FILLED);
		}
		return "login";
	}

}
