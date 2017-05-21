package nl.parkhaven.wasschema.modules.user;

import nl.parkhaven.wasschema.modules.util.Misc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {

    public static final String LOGIN_CREDENTIALS_INVALID = "Invalid email or password entered.";
    public static final String NOT_ALL_REQUIRED_FIELDS_FILLED = "Please fill all required fields.";
    public static final String USER_WITH_HOUSENUMBER_ALREADY_EXISTS = "A user with this housenumber or email already exists.";
    public static final String INCORRECT_SHARED_PASSWORD = "The shared password is incorrect. It is visible in the laundryroom.";
    public static final String NO_USER_WITH_EMAIL = "No user found with this email address.";
    // Each user should log in with the shared password given by the admin.
    public static final String NONEXISTENT_HOUSE_NUMBER = "The house number you entered is not valid, please add a correct house number.";
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    private static final String SHARED_PASSWORDS[] = {"3024NH", "9999AH", "1234TZ"}; // These codes will be used to pair a user to a laundry room.
    private UserDaoImpl userDao;

    @Autowired
    public LoginService(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    public boolean isLoginCredentialsValid(User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            logger.warn("Required JS check = Email: " + user.getEmail() + " Password: " + user.getPassword());
            return false;
        }
        return true;
    }

    public User login(User user) {
        return userDao.read(user);
    }

    public Map<String, Integer> getWashCounter(User user) {
        return userDao.getWashCounter(user);
    }

    public boolean signupCredentialsValid(User user) {
        if (user.getEmail() == null || user.getHouseNumber() == null || user.getPassword() == null
                || user.getSharedPassword() == null) {
            logger.warn("Not all required data entered. First name: " + user.getFirstName() + " - Last Name: "
                    + user.getLastName() + " - Email: " + user.getEmail() + " - Huisnummer: " + user.getHouseNumber()
                    + " - Wachtwoord: " + user.getPassword());
            return false;
        } else {
            if (sharedPasswordValid(user)) {
                return true;
            } else {
                logger.warn("Wrong signup code: " + user.getSharedPassword());
                return false;
            }
        }
    }

    public boolean houseNumberExist(User user) {
        return userDao.houseNumberExist(user);
    }

    public boolean sharedPasswordValid(User user) {
        for (int i = 0; i < SHARED_PASSWORDS.length; i++) {
            if (SHARED_PASSWORDS[i].equalsIgnoreCase(user.getSharedPassword().trim())) {
                user.setSharedPassword(i + 1 + "");
                return true;
            }
        }
        return false;
    }

    public boolean signup(User user) {
        if (!userDao.create(user)) {
            logger.warn("= Huisnummer or email already exists. Huisnummer: " + user.getHouseNumber() + " Email: "
                    + user.getEmail());
            return false;
        }
        logger.info("Signed up. Huisnummer: " + user.getHouseNumber() + " - Email: " + user.getEmail());
        return true;
    }

    public boolean setRandomPasswordFor(User user) {
        user.setPassword(Misc.generateNewPassword());
        return changePasswordInDb(user);
    }

    private boolean changePasswordInDb(User user) {
        if (userDao.updatePassword(user)) {
            logger.info("User password changed succesfully and email send! - Email: " + user.getEmail());
            return true;
        } else {
            logger.warn("Change Password Email not available: " + user.getEmail());
            return false;
        }
    }

}
