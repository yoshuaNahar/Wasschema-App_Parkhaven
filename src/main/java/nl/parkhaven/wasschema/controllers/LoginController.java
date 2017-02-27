package nl.parkhaven.wasschema.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import nl.parkhaven.wasschema.modules.user.ForgotPasswordService;
import nl.parkhaven.wasschema.modules.user.LoginService;
import nl.parkhaven.wasschema.modules.user.SignupService;
import nl.parkhaven.wasschema.modules.user.User;
import nl.parkhaven.wasschema.modules.util.MailSender;

@Controller
@RequestMapping(value = "/")
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private SignupService signupService;
	@Autowired
	private ForgotPasswordService forgotPasswordService;

	@GetMapping()
	public String getLoginPage() {
		return "login";
	}

	@PostMapping(params = { "to_servlet=login" })
	public String login(@ModelAttribute User user, HttpSession session, Model model) {
		User loggedInUser = loginService.login(user);

		if (loggedInUser == null) {
			model.addAttribute("message", loginService.getErrorMessage());
			return "login";
		} else {
			session.setAttribute("user", loggedInUser);
			session.setAttribute("wash_counter", loginService.getWashCounter(loggedInUser));
			return "redirect:/index.010";
		}
	}

	@PostMapping(params = { "to_servlet=signup" })
	public String signup(@ModelAttribute User user, HttpSession session, Model model) {
		signupService.signup(user, user.getSharedPassword());

		if (signupService.errorOccured()) {
			model.addAttribute("message", signupService.getErrorMessage());
			return "login";
		} else {
			return login(user, session, model);
		}
	}

	@PostMapping(params = { "to_servlet=forgotPassword" })
	public String forgotPassword(@ModelAttribute User user, Model model) {
		forgotPasswordService.setRandomPasswordForUser(user);

		if (forgotPasswordService.errorOccured()) {
			model.addAttribute("message", forgotPasswordService.getErrorMessage());
		} else {
			MailSender mailSender = new MailSender(user);
			mailSender.sendMailContainingPassword();
			model.addAttribute("message", "A mail has been sent to your email adres.");
		}
		return "login";
	}

}
