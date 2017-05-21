package nl.parkhaven.wasschema.modules.bulletinboard;

import nl.parkhaven.wasschema.modules.Crud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BulletinBoardDaoImpl implements Crud<Message> {

    private static final String ADD = "INSERT INTO prikbord_bericht (gebruiker_id, title_input, body_input, title_output, body_output) VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_AS_ADMIN = "UPDATE prikbord_bericht SET actief_beheerder='Y' WHERE id=?;";
    private static final String UPDATE_AS_USER = "UPDATE prikbord_bericht SET actief_gebruiker='N' WHERE id=?;";
    private static final String SELECT_MESSAGES = "SELECT id, gebruiker_id, title_output, body_output FROM prikbord_bericht WHERE actief_gebruiker='Y' AND actief_beheerder='Y' ORDER BY id DESC LIMIT 5;";
    private static final String SELECT_UNCHECKED_MESSAGES = "SELECT id, gebruiker_id, title_output, body_output FROM prikbord_bericht WHERE actief_beheerder = 'N' AND actief_gebruiker = 'Y';";
    private JdbcTemplate template;

    @Autowired
    BulletinBoardDaoImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public boolean create(Message message) {
        try {
            this.template.update(ADD, message.getUserId(), message.getTitleInput(),
                    message.getBodyInput(), message.getTitleOutput(), message.getBodyOutput());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Message read(Message message) {
        // I return the entire list of messages from the database, not
        // indiviually.
        throw new RuntimeException("Not implemented!");
    }

    // activate message (by admin)
    @Override
    public boolean update(Message message) {
        this.template.update(UPDATE_AS_ADMIN, message.getId());
        return true;
    }

    // deactivate message (by user or admin)
    @Override
    public boolean delete(Message message) {
        this.template.update(UPDATE_AS_USER, message.getId());
        return true;
    }

    public Map<Long, Message> getAllMessages() {
        List<Message> list = this.template.query(SELECT_MESSAGES, new MessageRowMapper());
        Map<Long, Message> messages = new HashMap<>();
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            Message message = list.get(i);
            messages.put((long) message.getId(), message);
        }
        return messages;
    }

    public Map<Long, Message> getPendingMessagesAdmin() {
        List<Message> list = this.template.query(SELECT_UNCHECKED_MESSAGES, new MessageRowMapper());
        Map<Long, Message> messages = new HashMap<>();
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            messages.put((long) i, list.get(i));
        }
        return messages;
    }

    public static class MessageRowMapper implements RowMapper<Message> {

        @Override
        public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
            Message message = new Message();
            message.setId(rs.getInt("id"));
            message.setUserId(rs.getInt("gebruiker_id"));
            message.setTitleOutput(rs.getString("title_output"));
            message.setBodyOutput(rs.getString("body_output"));

            return message;
        }

    }

}
