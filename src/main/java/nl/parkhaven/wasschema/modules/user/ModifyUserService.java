package nl.parkhaven.wasschema.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ModifyUserService {

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
