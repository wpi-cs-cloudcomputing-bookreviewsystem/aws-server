package edu.wpi.cs.cloudcomputing.database;

import edu.wpi.cs.cloudcomputing.model.User;

import java.rmi.server.UID;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void addFriend(User u1, User u2) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }

        try {

            Statement statement = databaseUtil.conn.createStatement();
            String userNetworkId = new UID().toString().split(":")[1];
            String columns = "INSERT INTO User_Network (userNetwork_id, userNetwork_user1_id, userNetwork_user2_id)";
            String values1 = "values ('" + userNetworkId + "','" + u1.getEmail() + "','" + u2.getEmail() + "');";
            String values2 = "values ('" + userNetworkId + "','" + u2.getEmail() + "','" + u1.getEmail() + "');";
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
            for (String email: usersemailList) {
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
//        User user1 = new User("USER1", "USER1@EMAIL.COM");
//        User user2 = new User("USER2", "USER2@EMAIL.COM");
//        UserNetworkDAO userNetworkDAO = new UserNetworkDAO();
//        System.out.println(userNetworkDAO.getFriendsList(user1).size());
//    }
}
