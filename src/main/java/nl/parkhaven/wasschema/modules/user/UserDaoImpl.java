package nl.parkhaven.wasschema.modules.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nl.parkhaven.wasschema.modules.Crud;

@Repository
public class UserDaoImpl implements Crud<User> {

    private JdbcTemplate template;

    private static final String SIGNUP = "INSERT INTO gebruiker (voornaam, achternaam, huisnummer, email, wachtwoord, sharedpassword_id) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String LOGIN = "SELECT id, huisnummer, email, admin, remember_laundry_room, sharedpassword_id FROM gebruiker WHERE email = ? AND wachtwoord = ?;";
    private static final String UPDATE_HOUSENUMBER = "UPDATE gebruiker SET huisnummer = ? WHERE id = ?;";
    private static final String UPDATE_PASSWORD = "UPDATE gebruiker SET wachtwoord = ? WHERE id = ?;";
    private static final String UPDATE_EMAIL = "UPDATE gebruiker SET email = ? WHERE id = ?;";
    private static final String REMOVE_ACCOUNT = "UPDATE gebruiker SET actief = 'N', email = CONCAT(email, ?), huisnummer = CONCAT(huisnummer, ?) WHERE id = ?;";
    private static final String SELECT_ALL_USERS = "SELECT id, voornaam, achternaam, huisnummer, email, wachtwoord, admin FROM gebruiker WHERE actief = 'Y';";
    private static final String GET_USER_WASHCOUNTER = "SELECT COUNT(gebruiker_id) FROM wasschema x LEFT JOIN week_dag_tijd a ON x.week_dag_tijd_id = a.id LEFT JOIN wasmachine b ON b.id = x.wasmachine_id WHERE x.gebruiker_id = ? AND a.week_id = ? AND b.machine_type = ?;";
    private static final String REMOVE_USER_APPOINTMENTS = "UPDATE wasschema SET gebruiker_id = NULL WHERE gebruiker_id = ?;";
    private static final String CHECK_HOUSE_NUMBER_EXISTS = "SELECT EXISTS(SELECT 1 FROM house_numbers WHERE house_number = ?);";
    private static final String UPDATE_REMEMBER_LAUNDRY_ROOM = "UPDATE gebruiker SET remember_laundry_room = ? WHERE id = ?;";
    
    private static final String[] MACHINE_TYPES = {"wash", "dry"};
    private static final String[] WEEKS = {"current", "next"};

    @Autowired
    public UserDaoImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public boolean create(User user) {
        try {
            this.template.update(SIGNUP, user.getFirstName(), user.getLastName(), user.getHouseNumber(),
                    user.getEmail(), user.getPassword(), user.getSharedPassword());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public User read(User user) {
        try {
            return this.template.queryForObject(LOGIN, new Object[]{user.getEmail(), user.getPassword()},
                    new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    // There are two methods for changing user settings. The changeable settings are
    // email, password. I removed the change house number settings, because the admin found that unnecessary
    @Override
    public boolean update(User user) {
        int rowsAffected = this.template.update(UPDATE_EMAIL, user.getEmail(), user.getId());
        return rowsAffected == 1;
    }

    public boolean updatePassword(User user) {
        int rowsAffected = this.template.update(UPDATE_PASSWORD, user.getPassword(), user.getId());
        return rowsAffected == 1;
    }

    public boolean updateRememberLaundryRoom(User user) {
        int rowsAffected = this.template.update(UPDATE_REMEMBER_LAUNDRY_ROOM, user.isRememberLaundryRoomChecked(), user.getId());
        return rowsAffected == 1;
    }

    @Override
    public boolean delete(User user) {
        // CONCAT with userId because Db columns are unique
        int rowsAffected = this.template.update(REMOVE_ACCOUNT,
                user.getId(), user.getId(), user.getId());
        if (rowsAffected != 1) {
            return false;
        }
        deleteAppointmentsFromDeletedUser(user);
        return true;
    }

    private void deleteAppointmentsFromDeletedUser(User user) {
        this.template.update(REMOVE_USER_APPOINTMENTS, user.getId());
    }

    // Washcounter to check if user didn't wash more than 3 times
    public Map<String, Integer> getWashCounter(User user) {
        Map<String, Integer> userWashCounter = new HashMap<>();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int value = this.template.queryForObject(GET_USER_WASHCOUNTER,
                        new Object[]{user.getId(), i + 1, MACHINE_TYPES[j]}, Integer.class);
                userWashCounter.put(WEEKS[i] + MACHINE_TYPES[j], value);
            }
        }

        return userWashCounter;
    }

    public boolean checkHouseNumberExists(User user) {
        return this.template.queryForObject(CHECK_HOUSE_NUMBER_EXISTS, new Object[]{user.getHouseNumber()}, Boolean.class);
    }

    public Map<Long, User> selectAllUsers() {
        List<User> list = this.template.query(SELECT_ALL_USERS, new UserRowMapper());
        Map<Long, User> users = new HashMap<>();
        for (User user : list) {
            users.put((long) user.getId(), user);
        }
        return users;
    }

    // TODO Should these types of minor classes be a bean? Or not? I am using this row mapper in other modules...
    public static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setHouseNumber(rs.getString("huisnummer"));
            user.setEmail(rs.getString("email"));
            user.setAdmin(rs.getString("admin").equalsIgnoreCase("Y"));
            user.setRememberLaundryRoomChecked(rs.getBoolean("remember_laundry_room"));
            user.setSharedPassword(rs.getString("sharedpassword_id"));
            return user;
        }
    }

}
