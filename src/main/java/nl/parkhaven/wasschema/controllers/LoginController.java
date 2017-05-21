package nl.parkhaven.wasschema.controllers;

import com.google.gson.Gson;
import nl.parkhaven.wasschema.modules.user.LoginService;
import nl.parkhaven.wasschema.modules.user.User;
import nl.parkhaven.wasschema.modules.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/")
public class LoginController {

    private LoginService loginService;
    private MailService mailService;

    @Autowired
    public LoginController(LoginService loginService, MailService mailService) {
        this.loginService = loginService;
        this.mailService = mailService;
    }

    @GetMapping
    public String getLoginPage() {
        return "login";
    }

    @PostMapping(params = {"to_servlet=login"})
    public String login(@ModelAttribute User user, HttpSession session, Model model) {
        if (loginService.isLoginCredentialsValid(user)) {
            User loggedInUser = loginService.login(user);
            if (loggedInUser == null) {
                model.addAttribute("message", LoginService.LOGIN_CREDENTIALS_INVALID);
            } else {
                session.setAttribute("user", loggedInUser);
                session.setAttribute("wash_counter", new Gson().toJson(loginService.getWashCounter(loggedInUser)));
                return "redirect:/index.010/loggedin";
            }
        } else {
            model.addAttribute("message", LoginService.NOT_ALL_REQUIRED_FIELDS_FILLED);
        }
        return "login";
    }

    @PostMapping(params = {"to_servlet=signup"})
    public String signup(@ModelAttribute User user, HttpSession session, Model model) {
        if (loginService.signupCredentialsValid(user)) {
            if (loginService.houseNumberExist(user)) {
                if (loginService.signup(user)) {
                    return login(user, session, model);
                } else {
                    model.addAttribute("message", LoginService.USER_WITH_HOUSENUMBER_ALREADY_EXISTS);
                }
            } else {
                model.addAttribute("message", LoginService.NONEXISTENT_HOUSE_NUMBER);
            }
        } else {
            if (!loginService.sharedPasswordValid(user)) {
                model.addAttribute("message", LoginService.INCORRECT_SHARED_PASSWORD);
            } else {
                model.addAttribute("message", LoginService.NOT_ALL_REQUIRED_FIELDS_FILLED);
            }
        }
        return "login";
    }

    @PostMapping(params = {"to_servlet=forgotPassword"})
    public String forgotPassword(@ModelAttribute User user, Model model) {
        if (user.getEmail() != null) {
            if (loginService.setRandomPasswordFor(user)) {
                mailService.sendMailContainingPasswordTo(user);
                model.addAttribute("message", "A mail has been sent to your email adres.");
            } else {
                model.addAttribute("message", LoginService.NO_USER_WITH_EMAIL);
            }
        } else {
            model.addAttribute("message", LoginService.NOT_ALL_REQUIRED_FIELDS_FILLED);
        }
        return "login";
    }

}
