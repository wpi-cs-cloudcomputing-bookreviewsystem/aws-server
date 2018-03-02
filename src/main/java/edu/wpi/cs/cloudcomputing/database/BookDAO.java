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

    public boolean updateBook(Book book) throws Exception {
        if (databaseUtil.conn == null) {
            databaseUtil.initDBConnection();
        }
        Boolean res = false;
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String update = "book_title = '" + book.getTitle()
                    + "', book_author='" + book.getAuthor()
                    + "', book_description='" + book.getDescription()
                    + "', book_image_url='" + book.getImageUrl()
                    + "', book_genre='" + book.getGenre() + "'";
            String query = "UPDATE Book SET" + update + "WHERE book_isbn='" + book.getISBN() + "';";
            res = statement.execute(query);
            return res;
        } catch (Exception e) {
            throw new Exception("Failed too insert report: " + e.getMessage());
        }


    }

    public boolean addBook(Book book) throws Exception {
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
            System.out.println(insertquery);
            statement.execute(insertquery);
            statement.close();
            databaseUtil.conn.close();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed too insert book: " + e.getMessage());
        }
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

//    public static void main(String[] args) {
//        BookDAO bookDAO = new BookDAO();
//        try {
//
//            Book demoBook = new Book();
//        demoBook.setTitle("Animals in Translation");
//        demoBook.setAuthor("Temple Grandin, Catherine Johnson");
//        demoBook.setDescription("In this exciting new e edition, Temple Grandin returns to her groundbreaking work, Animals in Translation, to address the last ten years of developments in behavioral research, animal welfare, and farming regulations. Originally published in 2005, Animals in Translation received unanimous critical praise and was a bestseller in both hardcover and paperback, and Grandin’s Q&A updates this classic text with the most current scientific research.");
//        demoBook.setGenre("Animal");
//        demoBook.setISBN("0156031442");
//        demoBook.setScore((float) 4.5);
//        demoBook.setImageUrl("http://www.templegrandin.com/_images/_books/AnimalsAutismTemple.png");
//            Boolean res = bookDAO.addBook(demoBook);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
