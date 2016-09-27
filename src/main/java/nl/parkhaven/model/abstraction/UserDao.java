package nl.parkhaven.model.abstraction;

import nl.parkhaven.model.entity.User;

public interface UserDao {

	User signin(User user);

	User signup(User user);

}
