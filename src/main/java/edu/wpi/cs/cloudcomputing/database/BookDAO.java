package edu.wpi.cs.cloudcomputing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.cloudcomputing.model.Book;

public class BookDAO {
	Connection conn;
	String jdbcTag = "jdbc:mysql://";
	String rdsMySqlDatabaseUrl = "was-bookreview-db-me.crb32rqtyjdg.us-east-2.rds.amazonaws.com";
	String rdsMySqlDatabasePort = "3306";
	String dbName = "BookReviewDB";
	String username = "";	// edit to your own
	String password = "";	// edit to your own
	
	public BookDAO(){}
	
	private void initDBConnection() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
	    			jdbcTag + rdsMySqlDatabaseUrl + ":" + rdsMySqlDatabasePort + "/" + dbName, 
	    			username, 
	    			password
	    	);
		}catch (Exception ex) {
			throw new Exception("Failed in database connection");
		}
	}

	public List<Book> getAllBooks() throws Exception{
		if (conn == null) {
			initDBConnection();
		}
		List<Book> allBooks = new ArrayList<>();
		try {
        	Statement statement = conn.createStatement();
        	String query = "SELECT * FROM books";
        	ResultSet resultSet = statement.executeQuery(query);

        	while (resultSet.next()) {
        		Book book = generateBookFromResultSet(resultSet);
        		allBooks.add(book);       		
        	}
        	resultSet.close();
        	statement.close();
        	conn.close();
        	
        	return allBooks;
        	
        }catch (Exception e) {
        	throw new Exception("Failed in getting books: " + e.getMessage());
        }
	}
	
	
	
	private Book generateBookFromResultSet(ResultSet resultSet) throws SQLException {
		String title = resultSet.getString("title");
		String author = resultSet.getString("author");
		String ISBN = resultSet.getString("ISBN");
		String description = resultSet.getString("description");
		String imageUrl = resultSet.getString("imageUrl");
		String genre = resultSet.getString("genre");
		Float score = Float.valueOf(resultSet.getString("score"));
		return new Book(title, author, ISBN, description, imageUrl, genre, score);
	}
}
