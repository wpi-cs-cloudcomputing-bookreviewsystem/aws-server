package edu.wpi.cs.cloudcomputing.database;

import edu.wpi.cs.cloudcomputing.model.User;

import java.rmi.server.UID;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by tonggezhu on 2/27/18.
 */
public class UserNetworkDAO {
    DatabaseUtil databaseUtil = new DatabaseUtil();

    public UserNetworkDAO() {
        databaseUtil = new DatabaseUtil();
    }

    public void addFriend(String fromEmail, String toEmail) throws Exception {

        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }

        try {
            Statement statement = databaseUtil.conn.createStatement();
            String userNetworkId = new UID().toString().split(":")[1];
            String columns = "INSERT INTO User_Network (userNetwork_id, userNetwork_user1_id, userNetwork_user2_id, isPending)";
            String values1 = "values ('" + userNetworkId + "','" + fromEmail + "','" + toEmail + "'," + 0 + ");";
            String values2 = "values ('" + userNetworkId + "','" + toEmail + "','" + fromEmail + "'," + 1 + ");";
            String query1 = columns + values1;
            String query2 = columns + values2;
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);

            statement.close();
            databaseUtil.conn.close();

        } catch (Exception e) {
            throw new Exception("Failed in insert UserNetwork " + e.getMessage());
        }

    }

    public void acceptAddFriend(String fromEmail, String toEmail) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String userNetworkId = new UID().toString().split(":")[1];
            String query = "UPDATE User_Network SET isPending = 0 WHERE userNetwork_user1_id ='" + fromEmail + "'AND userNetwork_user2_id='" + toEmail + "';";
            System.out.println(query);
            statement.executeUpdate(query);
            statement.close();
            databaseUtil.conn.close();

        } catch (Exception e) {
            throw new Exception("Failed in update UserNetwork " + e.getMessage());
        }

    }

    public void rejectAddFriend(String fromEmail, String toEmail) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query1 = "DELETE FROM User_Network WHERE userNetwork_user1_id ='" + fromEmail + "' AND userNetwork_user2_id='" + toEmail + "';";
            String query2 = "DELETE FROM User_Network WHERE userNetwork_user1_id ='" + toEmail + "' AND userNetwork_user2_id='" + fromEmail + "';";
            System.out.println(query1);
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
            statement.close();
            databaseUtil.conn.close();

        } catch (Exception e) {
            throw new Exception("Failed in delete UserNetwork " + e.getMessage());
        }
    }

    public String checkFriendship(String myEmail, String requestEmail) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        StringBuilder sb = new StringBuilder();
        ResultSet resultSet = null;
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query = "SELECT isPending FROM User_Network WHERE (userNetwork_user1_id='" + myEmail + "' AND userNetwork_user2_id='" + requestEmail + "') OR (userNetwork_user1_id='" + requestEmail + "'AND userNetwork_user2_id='" + myEmail + "');";
            System.out.println(query);

            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                sb.append(resultSet.getInt("isPending"));
            }

            resultSet.close();
            statement.close();
            databaseUtil.conn.close();
            if (sb.toString().equals("01")) {
                return "pending";
            } else if (sb.toString().equals("00")) {
                return "true";
            } else {
                return "false";
            }
        } catch (Exception e) {
            throw new Exception("Failed in update UserNetwork " + e.getMessage());
        }

    }

    public List<User> getFriendsList(User user) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }

        Set<String> usersemailList = new HashSet<>();
        try {
            Statement statement = databaseUtil.conn.createStatement();

            String searchQuery = "SELECT userNetwork_user2_id FROM User_Network WHERE userNetwork_user1_id='" + user.getEmail() + "';";
            ResultSet resultSet = statement.executeQuery(searchQuery);
            while (resultSet.next()) {
                usersemailList.add(resultSet.getString("userNetwork_user2_id"));

            }

            resultSet.close();
            statement.close();
            databaseUtil.conn.close();
            List<User> setList = new ArrayList<>();
            for (String email : usersemailList) {
                setList.add(generateUserFromUseremail(email));
            }
            return setList;

        } catch (Exception e) {
            throw new Exception("Failed in getting friends list: " + e.getMessage());
        }


    }

    private User generateUserFromUseremail(String email) throws Exception {
        UserDAO userDAO = new UserDAO();
        try {
            return userDAO.getUser(email);
        } catch (Exception e) {
            throw new Exception("Failed in getting user: " + e.getMessage());
        }

    }

//    public static void main(String[] args) throws Exception {
//        String fromEmail = "USER4@EMAIL.COM";
//        String toEmail = "ha@ha.ha";
//        UserNetworkDAO userNetworkDAO = new UserNetworkDAO();
//        userNetworkDAO.rejectAddFriend(fromEmail, toEmail);
//        System.out.println(userNetworkDAO.checkFriendship(toEmail, fromEmail));
//        //System.out.println(userNetworkDAO.getFriendsList(user1).size());
//    }
}
