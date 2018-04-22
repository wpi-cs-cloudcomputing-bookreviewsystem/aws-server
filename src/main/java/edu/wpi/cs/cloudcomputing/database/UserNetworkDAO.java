package edu.wpi.cs.cloudcomputing.database;

import edu.wpi.cs.cloudcomputing.model.User;

import java.rmi.server.UID;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by tonggezhu on 2/27/18.
 */
public class UserNetworkDAO {

    public void addFriend(String fromEmail, String toEmail) throws Exception {

        try {
            Statement statement = DatabaseUtil.getConnection().createStatement();
            String userNetworkId = System.currentTimeMillis() % 100000000 + "";
            String columns = "INSERT INTO User_Network (userNetwork_id, userNetwork_user1_id, userNetwork_user2_id, isPending)";
            String values1 = "values ('" + userNetworkId + "','" + fromEmail + "','" + toEmail + "'," + 0 + ");";
            String values2 = "values ('" + userNetworkId + "','" + toEmail + "','" + fromEmail + "'," + 1 + ");";
            String query1 = columns + values1;
            String query2 = columns + values2;
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
            statement.close();
        } catch (Exception e) {
            throw new Exception("Failed in insert UserNetwork " + e.getMessage());
        }
    }

    public void acceptAddFriend(String fromEmail, String toEmail) throws Exception {

        try {
            String query = "UPDATE User_Network SET isPending = 0 WHERE userNetwork_user1_id =? AND userNetwork_user2_id=?;";
            PreparedStatement statement = DatabaseUtil.getConnection().prepareStatement(query);
            statement.setString(1, fromEmail);
            statement.setString(2, toEmail);
            System.out.println(query);
            statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            throw new Exception("Failed in update UserNetwork " + e.getMessage());
        }

    }

    public void rejectAddFriend(String fromEmail, String toEmail) throws Exception {
        try {
            Statement statement = DatabaseUtil.getConnection().createStatement();
            String query1 = "DELETE FROM User_Network WHERE userNetwork_user1_id ='" + fromEmail + "' AND userNetwork_user2_id='" + toEmail + "';";
            String query2 = "DELETE FROM User_Network WHERE userNetwork_user1_id ='" + toEmail + "' AND userNetwork_user2_id='" + fromEmail + "';";
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
            statement.close();

        } catch (Exception e) {
            throw new Exception("Failed in delete UserNetwork " + e.getMessage());
        }
    }

    public String checkFriendship(String myEmail, String requestEmail) throws Exception {

        StringBuilder sb = new StringBuilder();
        ResultSet resultSet = null;
        try {
            Statement statement = DatabaseUtil.getConnection().createStatement();
            String query = "SELECT isPending FROM User_Network WHERE (userNetwork_user1_id='" + myEmail + "' AND userNetwork_user2_id='" + requestEmail + "') OR (userNetwork_user1_id='" + requestEmail + "'AND userNetwork_user2_id='" + myEmail + "');";

            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                sb.append(resultSet.getInt("isPending"));
            }

            resultSet.close();
            statement.close();

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

    public List<String> getFriendsList(String email) throws Exception {

        Set<String> usersemailList = new HashSet<>();
        try {
            Statement statement = DatabaseUtil.getConnection().createStatement();

            String searchQuery = "SELECT userNetwork_user2_id FROM User_Network WHERE userNetwork_user1_id='" + email + "';";
            ResultSet resultSet = statement.executeQuery(searchQuery);
            while (resultSet.next()) {
                usersemailList.add(resultSet.getString("userNetwork_user2_id"));
            }
            resultSet.close();
            statement.close();
            List<String> setList = new ArrayList<>();
            for (String s : usersemailList) {
                setList.add(s);
            }
            return setList;
        } catch (Exception e) {
            throw new Exception("Failed in getting friends list: " + e.getMessage());
        }


    }
}
