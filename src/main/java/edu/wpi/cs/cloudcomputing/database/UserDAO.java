package edu.wpi.cs.cloudcomputing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.wpi.cs.cloudcomputing.model.User;
import edu.wpi.cs.cloudcomputing.utils.Common;
import sun.jvmstat.perfdata.monitor.PerfStringVariableMonitor;

public class UserDAO {

    DatabaseUtil databaseUtil;

    public UserDAO() {
        databaseUtil = new DatabaseUtil();
    }

    private void initDBConnection() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            databaseUtil.conn = DriverManager.getConnection(
                    Common.jdbcTag + Common.rdsMySqlDatabaseUrl + ":" + Common.rdsMySqlDatabasePort + "/" + Common.dbName,
                    Common.dbUsername,
                    Common.dbPassword
            );
            System.out.println("Database has been connected successfully.");
        } catch (Exception ex) {
            throw new Exception("Failed in database connection");
        }
    }

    public void saveUser(User user) throws Exception {
        if (databaseUtil.conn == null) {
            initDBConnection();
        }
        try {
            Statement statement = databaseUtil.conn.createStatement();
            User existingUser = null;
            String searchQuery = "SELECT * FROM User WHERE user_email='" + user.getEmail() + "';";
            System.out.println(searchQuery);
            ResultSet resultSet = statement.executeQuery(searchQuery);
            while (resultSet.next()) {
                existingUser = generateUserFromResultSet(resultSet);
            }
            if (existingUser != null) {
                throw new Exception("User already exists");
            }

            String insertQuery = "INSERT INTO User (user_id, user_name, user_email) VALUES (null, '" + user.getUsername() + "', '" + user.getEmail() + "')";
            statement.executeUpdate(insertQuery);

            resultSet.close();
            statement.close();
            databaseUtil.conn.close();

        } catch (Exception e) {
            throw new Exception("Failed in getting user: " + e.getMessage());
        } finally {
            if (databaseUtil.conn != null) {
                databaseUtil.conn.close();
            }
        }
    }

    public User getUser(String emailAddress) throws Exception {
        if (databaseUtil.conn == null) {
            initDBConnection();
        }

        try {
            User user = null;

            Statement statement = databaseUtil.conn.createStatement();
            String query = "SELECT * FROM User WHERE user_email='" + emailAddress + "';";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                user = generateUserFromResultSet(resultSet);
            }
            if (user == null) {
                throw new Exception("User not found");
            }

            resultSet.close();
            statement.close();
            databaseUtil.conn.close();

            return user;

        } catch (Exception e) {
            throw new Exception("Failed in getting user: " + e.getMessage());
        } finally {
            if (databaseUtil.conn != null) {
                databaseUtil.conn.close();
            }
        }
    }

    private User generateUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUsername(resultSet.getString("user_name"));
        user.setEmail(resultSet.getString("user_email"));
        return user;
    }

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        User u = new User();
        u.setEmail("ddd@ddd.ccc");
        u.setUsername("name");
        try {
            User test = userDAO.getUser("ddd@ddd.ccc");
            System.out.println(test.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
