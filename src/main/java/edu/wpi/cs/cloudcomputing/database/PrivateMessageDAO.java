package edu.wpi.cs.cloudcomputing.database;

import edu.wpi.cs.cloudcomputing.model.PrivateMessage;
import edu.wpi.cs.cloudcomputing.utils.Common;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonggezhu on 3/7/18.
 */
public class PrivateMessageDAO {
    DatabaseUtil databaseUtil;

    public PrivateMessageDAO() {
        this.databaseUtil = new DatabaseUtil();
    }

    public List<PrivateMessage> getInboxByUserEmail(String email) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        List<PrivateMessage> inboxMessages = new ArrayList<>();
        try {
            PrivateMessage message = null;

            Statement statement = databaseUtil.conn.createStatement();
            String query = "SELECT * FROM Private_Message WHERE pm_to_user_id='" + email + "';";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                message = generatePrivateMessageFromResultSet(resultSet);
                if (!message.getStatus().equals(Common.IGNORE)) {
                    inboxMessages.add(message);
                }
            }
            resultSet.close();
            statement.close();
            databaseUtil.conn.close();

            return inboxMessages;

        } catch (Exception e) {
            throw new Exception("Failed in getting private message: " + e.getMessage());
        } finally {
            if (databaseUtil.conn != null) {
                databaseUtil.conn.close();
            }
        }
    }

    public void addPrivateMessage(PrivateMessage message) throws Exception {

        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query = addPrivateMessageQuery(message);
            statement.execute(query);
            statement.close();
            databaseUtil.conn.close();
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
            databaseUtil.conn.close();
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
            databaseUtil.conn.close();
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
            databaseUtil.conn.close();
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
        PrivateMessage privateMessage = new PrivateMessage(pmId, fromUserEmail, toUserEmail, title, content, type, status);
        return privateMessage;
    }

    private String addPrivateMessageQuery(PrivateMessage message) {

        String columns = "INSERT INTO Private_Message (pm_id, pm_from_user_id, pm_to_user_id, pm_title, pm_content, pm_type, pm_status, pm_datetime)";
        String values = "values ('" + message.getPmId() + "','" + message.getSenderEmail() + "','" + message.getReceiverEmail() + "','" + message.getTitle()
                + "','" + message.getContent() + "','" + message.getType() + "','" + message.getStatus() + "'," + databaseUtil.currentDate() + ");";
        return columns + values;

    }
}
