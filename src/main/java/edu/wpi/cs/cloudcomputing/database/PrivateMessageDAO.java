package edu.wpi.cs.cloudcomputing.database;

import com.google.gson.Gson;
import edu.wpi.cs.cloudcomputing.model.Book;
import edu.wpi.cs.cloudcomputing.model.PrivateMessage;
import edu.wpi.cs.cloudcomputing.utils.Common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tonggezhu on 3/7/18.
 */
public class PrivateMessageDAO {
    DatabaseUtil databaseUtil;

    public PrivateMessageDAO() {
        this.databaseUtil = new DatabaseUtil();
    }

    public List<Book> getAllBooks(String email) throws Exception{
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        Set<Book> bookList = new HashSet<>();
        try {
            String query = "SELECT * FROM Private_Message WHERE pm_to_user_id=?;";
            PreparedStatement statement = databaseUtil.conn.prepareStatement(query);
            statement.setString(1, email);
            System.out.println(statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("pm_type").equals(Common.RECOMMANDATION)) {
                    String content = resultSet.getString("pm_content");
                    Gson gson = new Gson();
                    Book book = gson.fromJson(content,Book.class);
                    System.out.println(content);
                    bookList.add(book);
                }
            }
            resultSet.close();
            statement.close();
            return new ArrayList(bookList);

        } catch (Exception e) {
            throw new Exception("Failed in getting private message: " + e.getMessage());
        }

    }

    public List<PrivateMessage> getInboxByUserEmail(String email) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        List<PrivateMessage> inboxMessages = new ArrayList<>();
        try {
            PrivateMessage message = null;
            String query = "SELECT * FROM Private_Message WHERE pm_to_user_id=?;";
            PreparedStatement statement = databaseUtil.conn.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                message = generatePrivateMessageFromResultSet(resultSet);
                if (!message.getStatus().equals(Common.IGNORE) && !message.getType().equals(Common.RECOMMANDATION)) {
                    inboxMessages.add(message);
                }
            }
            resultSet.close();
            statement.close();

            return inboxMessages;

        } catch (Exception e) {
            throw new Exception("Failed in getting private message: " + e.getMessage());
        }
    }



    public void addPrivateMessage(PrivateMessage message) throws Exception {

        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        try {
            String date = databaseUtil.currentDate();
            String query = "INSERT INTO Private_Message (pm_id, pm_from_user_id, pm_to_user_id, pm_title, pm_content, pm_type, pm_status, pm_datetime) values ( ? , ?, ? ,?, ? , ? , ? , STR_TO_DATE( ?, '%Y-%m-%d %H:%i:%s'))";
            PreparedStatement statement = databaseUtil.conn.prepareStatement(query);
            statement.setString(1, message.getPmId());
            statement.setString(2, message.getSenderEmail());
            statement.setString(3, message.getReceiverEmail());
            statement.setString(4, message.getTitle());
            statement.setString(5, message.getContent());
            statement.setString(6, message.getType());
            statement.setString(7, message.getStatus());
            statement.setString(8, date);
            System.out.println(statement);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            throw new Exception("Failed too insert book: " + e.getMessage());
        }
    }

    public void deletePrivateMessage(String pmId) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query = "DELETE FROM Private_Message WHERE pm_id='" + pmId + "';";
            statement.execute(query);
            statement.close();
        } catch (Exception e) {
            throw new Exception("Failed to change the status of message to READ: " + e.getMessage());
        }
    }

    public void readPrivateMessage(String pmId) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query = "UPDATE Private_Message SET pm_status='" + Common.READ + "' WHERE pm_id='" + pmId + "';";
            System.out.println(query);
            statement.execute(query);
            statement.close();
        } catch (Exception e) {
            throw new Exception("Failed to change the status of message to READ: " + e.getMessage());
        }
    }

    public void ignorePrivateMessage(String pmId) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query = "UPDATE Private_Message SET pm_status='" + Common.IGNORE + "' WHERE pm_id='" + pmId + "';";
            statement.execute(query);
            statement.close();
        } catch (Exception e) {
            throw new Exception("Failed to change the status of message to IGNORE: " + e.getMessage());
        }
    }

    private PrivateMessage generatePrivateMessageFromResultSet(ResultSet resultSet) throws Exception {
        String pmId = resultSet.getString("pm_id");
        String fromUserEmail = resultSet.getString("pm_from_user_id");
        String toUserEmail = resultSet.getString("pm_to_user_id");
        String title = resultSet.getString("pm_title");
        String content = resultSet.getString("pm_content");
        String type = resultSet.getString("pm_type");
        String status = resultSet.getString("pm_status");
        PrivateMessage privateMessage = new PrivateMessage(pmId, fromUserEmail, toUserEmail, title, content, status, type);
        return privateMessage;
    }
}
