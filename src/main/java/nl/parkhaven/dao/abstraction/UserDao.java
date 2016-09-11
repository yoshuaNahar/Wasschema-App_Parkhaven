package nl.parkhaven.dao.abstraction;

import nl.parkhaven.entity.User;

public interface UserDao {

	User signin(User user);
	
	User signup(User user);

}
