package edu.wpi.cs.cloudcomputing.database;

import java.sql.*;

import edu.wpi.cs.cloudcomputing.model.User;

public class UserDAO {

//    DatabaseUtil databaseUtil;

//    public UserDAO() {
//        databaseUtil = new DatabaseUtil();
//    }

    public void saveUser(User user) throws Exception {
//        if (DatabaseUtil.conn == null || databaseUtil.conn.isClosed()) {
//            databaseUtil.initDBConnection();
//        }
        try {

            PreparedStatement statement = null;
            User existingUser = null;
            String query = "SELECT * FROM User WHERE user_email = ?";

            statement = DatabaseUtil.getConnection().prepareStatement(query);
            statement.setString(1, user.getEmail());
            ResultSet resultSet = statement.executeQuery();



//            Statement statement = databaseUtil.conn.createStatement();
//            User existingUser = null;
//            String searchQuery = "SELECT * FROM User WHERE user_email='" + user.getEmail() + "';";
//            System.out.println(searchQuery);
//            ResultSet resultSet = statement.executeQuery(searchQuery);
            while (resultSet.next()) {
                existingUser = generateUserFromResultSet(resultSet);
            }
            if (existingUser != null) {
                throw new Exception("User already exists");
            }

            String insertQuery = "INSERT INTO User (user_id, user_name, user_email) VALUES (null, ? , ?)";

            statement = DatabaseUtil.getConnection().prepareStatement(insertQuery);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());

            statement.executeUpdate();

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            throw new Exception("Failed in getting user: " + e.getMessage());
        }
    }

    public User getUser(String emailAddress) throws Exception {
//        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
//            databaseUtil.initDBConnection();
//        }

        try {
            User user = null;

            PreparedStatement statement = null;
            String query = "SELECT * FROM User WHERE user_email = ?";
            statement = DatabaseUtil.getConnection().prepareStatement(query);
            statement.setString(1, emailAddress);
            ResultSet resultSet = statement.executeQuery();


//            Statement statement = databaseUtil.conn.createStatement();
//            String query = "SELECT * FROM User WHERE user_email='" + emailAddress + "';";
//            System.out.println(query);
//            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                user = generateUserFromResultSet(resultSet);
            }
            if (user == null) {
                throw new Exception("User not found");
            }

            resultSet.close();
            statement.close();
            System.out.println("-=====--"+DatabaseUtil.getConnection().isClosed());

            return user;

        } catch (Exception e) {
            throw new Exception("Failed in getting user: " + e.getMessage());
        }
    }


    private User generateUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUsername(resultSet.getString("user_name"));
        user.setEmail(resultSet.getString("user_email"));
        return user;
    }


//    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAO();
//        User user1 = new User("USER10", "USER10@EMAIL.COM");
////        User user2 = new User("USER2", "USER2@EMAIL.COM");
//        try {
//            userDAO.saveUser(user1);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
