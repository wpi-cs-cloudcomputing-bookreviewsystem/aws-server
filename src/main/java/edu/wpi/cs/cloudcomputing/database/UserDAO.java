package edu.wpi.cs.cloudcomputing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import edu.wpi.cs.cloudcomputing.model.User;
import edu.wpi.cs.cloudcomputing.utils.Common;

public class UserDAO {
	
	Connection conn;	
	public UserDAO(){}
	
	private void initDBConnection() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
	    			Common.jdbcTag + Common.rdsMySqlDatabaseUrl + ":" + Common.rdsMySqlDatabasePort + "/" + Common.dbName, 
	    			Common.dbUsername, 
	    			Common.dbPassword
	    	);
			System.out.println("Database has been connected successfully.");
		}catch (Exception ex) {
			throw new Exception("Failed in database connection");
		}
	}
	
	public User getUser(String emailAddress) throws Exception{
		if (conn == null) {
			initDBConnection();
		}

		try {
			User user = null;
			
        	Statement statement = conn.createStatement();
        	String query = "SELECT * FROM books where email=" + emailAddress;
        	ResultSet resultSet = statement.executeQuery(query);

        	while (resultSet.next()) {
        		user = generateUserFromResultSet(resultSet);     		
        	}
        	if (user == null) {
        		throw new Exception("User not found");
        	}
        	
        	resultSet.close();
        	statement.close();
        	conn.close();
        	
        	return user;
        	
        }catch (Exception e) {
        	throw new Exception("Failed in getting user: " + e.getMessage());
        }
	}
	
	private User generateUserFromResultSet(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setUsername(resultSet.getString("username"));
		user.setEmail(resultSet.getString("email"));
		return user;
	}
	
}
