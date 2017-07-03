package nl.parkhaven.wasschema.modules.user;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModifyUserService {

  public static final String PASSWORD_CHANGED = "You can now login with your new password.";
  public static final String LAUNDRY_ROOM_REMEMBERED = "Your appointed laundry room will automatically be shown when logging in.";
  private UserDaoImpl userDao;

  @Autowired
  public ModifyUserService(UserDaoImpl userDao) {
    this.userDao = userDao;
  }

  public boolean changeRememberLaundryRoom(User user) {
    return userDao.updateRememberLaundryRoom(user);
  }

  public boolean changePassword(User user) {
    return userDao.updatePassword(user);
  }

  public boolean changeEmail(User user) {
    return userDao.update(user);
  }

  public boolean deleteAccount(User user) {
    return userDao.delete(user);
  }

  public Map<Long, User> getAllUsers() {
    return userDao.selectAllUsers();
  }

}
