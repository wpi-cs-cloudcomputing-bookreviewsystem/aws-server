package edu.wpi.cs.cloudcomputing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.cloudcomputing.model.Book;
import edu.wpi.cs.cloudcomputing.utils.Common;

public class BookDAO {
	
	Connection conn;	
	public BookDAO(){}
	
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
