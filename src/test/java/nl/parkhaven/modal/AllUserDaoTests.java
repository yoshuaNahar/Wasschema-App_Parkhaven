package nl.parkhaven.modal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import nl.parkhaven.modal.user.UserDaoSigninTest;
import nl.parkhaven.modal.user.UserDaoSignupTest;

@RunWith(Suite.class)

@SuiteClasses({ UserDaoSigninTest.class, UserDaoSignupTest.class })
public class AllUserDaoTests {

}
