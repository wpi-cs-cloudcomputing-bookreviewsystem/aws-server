package edu.wpi.cs.cloudcomputing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.regexp.internal.RE;
import edu.wpi.cs.cloudcomputing.model.Book;
import edu.wpi.cs.cloudcomputing.model.Review;
import edu.wpi.cs.cloudcomputing.model.User;
import edu.wpi.cs.cloudcomputing.utils.Common;

public class BookDAO {

    //Connection conn;
    DatabaseUtil databaseUtil;

    public BookDAO() {
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

    public List<Book> getAllBooks() throws Exception {
        if (databaseUtil.conn == null) {
            initDBConnection();
        }
        List<Book> allBooks = new ArrayList<>();
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query = "SELECT * FROM Book";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Book book = generateBookFromResultSet(resultSet);
                allBooks.add(book);
            }
            resultSet.close();
            statement.close();
            databaseUtil.conn.close();

            return allBooks;

        } catch (Exception e) {
            throw new Exception("Failed in getting books: " + e.getMessage());
        }
    }

    public Book getBook(String bookISBN) throws Exception {
        if (databaseUtil.conn == null) {
            initDBConnection();
        }

        try {
            Book book = null;

            Statement statement = databaseUtil.conn.createStatement();
            String query = "SELECT * FROM Book WHERE book_isbn='" + bookISBN + "';";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                book = generateBookFromResultSet(resultSet);
            }
            if (book == null) {
                throw new Exception("Book not found");
            }

            resultSet.close();
            statement.close();
            databaseUtil.conn.close();

            return book;

        } catch (Exception e) {
            throw new Exception("Failed in getting book: " + e.getMessage());
        } finally {
            if (databaseUtil.conn != null) {
                databaseUtil.conn.close();
            }
        }
    }

    public boolean addBookWithReview(Book book) throws Exception {
        if (databaseUtil.conn == null) {
            initDBConnection();
        }
        String bookISBN = book.getISBN();
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query = "SELECT * FROM book WHERE book_isbn = " + bookISBN + ";";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Book existbook = generateBookFromResultSet(resultSet);
                existbook.getReviews().add(book.getReviews().get(0));
                resultSet.close();
                return false;
            }
            String insertquery = insertBookQuery(book);
            statement.executeUpdate(insertquery);
            statement.close();
            databaseUtil.conn.close();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed too insert book: " + e.getMessage());
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

    private String insertBookQuery(Book book) {
        String columns = "INSERT INTO book (title, author, ISBN, decription, imageUrl, genre, score)";
        String values = "values (" + book.getTitle() + "," + book.getAuthor() + "," + book.getISBN() + ","
                + book.getDescription() + "," + book.getImageUrl() + "," + book.getGenre() + "," + book.getScore() + ");";
        return columns + values;
    }

	public static void main(String[] args)  {
		BookDAO bookDAO = new BookDAO();
		try {
			List<Book> bookList = bookDAO.getAllBooks();
			System.out.println(bookList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
