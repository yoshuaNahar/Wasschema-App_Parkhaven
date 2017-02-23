package nl.parkhaven.wasschema.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.parkhaven.wasschema.modules.user.ForgotPasswordService;
import nl.parkhaven.wasschema.modules.user.LoginService;
import nl.parkhaven.wasschema.modules.user.SignupService;
import nl.parkhaven.wasschema.modules.user.User;
import nl.parkhaven.wasschema.modules.util.MailSender;

@Controller
@RequestMapping(value = "/")
public class LoginController {

	@Autowired
	private LoginService loginService; // But why would I use Autowired instead of simply doing new Object?

    @RequestMapping(method = RequestMethod.GET)
    public String getLoginPage() {
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST, params = { "to_servlet=login" })
    public String login(Model model, @ModelAttribute User user, HttpSession session) {
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

    @RequestMapping(method = RequestMethod.POST, params = { "to_servlet=signup" })
    public String signup(Model model, @ModelAttribute User user, HttpSession session) {
        SignupService signupService = new SignupService(user, user.getSharedPassword());
        signupService.signup();

        if (signupService.errorOccured()) {
            model.addAttribute("message", signupService.getErrorMessage());
            return "login";
        } else {
            return login(model, user, session);
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = { "to_servlet=forgotPassword" })
    public String forgotPassword(Model model, @ModelAttribute User user) {
        ForgotPasswordService forgotPasswordService = new ForgotPasswordService(user);
        forgotPasswordService.setRandomPasswordForUser();

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
