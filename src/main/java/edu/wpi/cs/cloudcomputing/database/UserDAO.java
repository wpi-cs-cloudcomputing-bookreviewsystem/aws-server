package edu.wpi.cs.cloudcomputing.database;

import java.sql.*;

import edu.wpi.cs.cloudcomputing.model.User;

public class UserDAO {

    public void saveUser(User user) throws Exception {

        try {
            PreparedStatement statement = null;
            User existingUser = null;
            String query = "SELECT * FROM User WHERE user_email = ?";

            statement = DatabaseUtil.getConnection().prepareStatement(query);
            statement.setString(1, user.getEmail());
            ResultSet resultSet = statement.executeQuery();

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

        try {
            User user = null;

            PreparedStatement statement = null;
            String query = "SELECT * FROM User WHERE user_email = ?";
            statement = DatabaseUtil.getConnection().prepareStatement(query);
            statement.setString(1, emailAddress);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                user = generateUserFromResultSet(resultSet);
            }
            if (user == null) {
                throw new Exception("User not found");
            }
            resultSet.close();
            statement.close();

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
}
