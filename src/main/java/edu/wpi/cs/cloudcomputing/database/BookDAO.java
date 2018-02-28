package edu.wpi.cs.cloudcomputing.database;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.wpi.cs.cloudcomputing.model.Book;
import edu.wpi.cs.cloudcomputing.model.Review;

public class BookDAO {

    DatabaseUtil databaseUtil;

    public BookDAO() {
        databaseUtil = new DatabaseUtil();
    }


    public List<Book> getAllBooks() throws Exception {
        if (databaseUtil.conn == null) {
            databaseUtil.initDBConnection();
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
            databaseUtil.initDBConnection();
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
            databaseUtil.initDBConnection();
        }
        String bookISBN = book.getISBN();
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query = "SELECT * FROM Book WHERE book_isbn = '" + bookISBN + "';";

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


    private Book generateBookFromResultSet(ResultSet resultSet) throws Exception {
        String title = resultSet.getString("book_title");
        String author = resultSet.getString("book_author");
        String ISBN = resultSet.getString("book_isbn");
        String description = resultSet.getString("book_description");
        String imageUrl = resultSet.getString("book_image_Url");
        String genre = resultSet.getString("book_genre");
        RatingDAO ratingDAO = new RatingDAO();
        Float score = ratingDAO.getAvergeRatingFromBookISBN(ISBN);
        return new Book(title, author, ISBN, description, imageUrl, genre, score);
    }

    private String insertBookQuery(Book book) {
        String columns = "INSERT INTO Book (book_title, book_author, book_isbn, book_description, book_image_Url, book_genre)";
        String values = "values ('" + book.getTitle() + "','" + book.getAuthor() + "','" + book.getISBN() + "','"
                + book.getDescription() + "','" + book.getImageUrl() + "','" + book.getGenre() + "');";
        return columns + values;
    }

    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        try {

            Book book = new Book("title", "author",
                    "test1", "description", null, "genre", 4.5f);
            Review review = null;
            List<Review> reviewList = new LinkedList<>();
            reviewList.add(review);
            book.setReviews(reviewList);

            Book book1 = bookDAO.getBook("test1");
            System.out.println(book1.getGenre());
            System.out.println(book1.getScore());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
