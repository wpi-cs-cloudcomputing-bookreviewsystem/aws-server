package edu.wpi.cs.cloudcomputing.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.wpi.cs.cloudcomputing.model.Book;

public class BookDAO {

    DatabaseUtil databaseUtil;

    public BookDAO() {
        databaseUtil = new DatabaseUtil();
    }

    public Book getBook(String bookISBN) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
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
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
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
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
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

    public List<Book> getAllBooks(String words) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        List<Book> allBooks = new ArrayList<>();
        if (words == null || words == "") {
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
        } else {
            allBooks = searchBook(words, databaseUtil);
            databaseUtil.conn.close();
            return allBooks;
        }
    }


    private List<Book> searchBook(String words, DatabaseUtil util) throws Exception {
        String[] wordsSet = words.split(" ");
        Set<ResultSet> res = new HashSet<>();
        String pattern = "[0-9]{10}";

        try {
            Statement statement = util.conn.createStatement();
            for (String word : wordsSet) {
                ResultSet resultSet = null;
                if (word.matches(pattern)) {
                    String query = "SELECT * FROM Book WHERE book_isbn='" + word + "';";
                    resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {
                        res.add(resultSet);
                    }
                    resultSet.close();
                } else {
                    String query1 = "SELECT * FROM Book WHERE book_title like '%" + word + "%';"
                            + "SELECT * FROM Book WHERE book_author like '%" + word + "%';";
                    System.out.println(query1);
                    CallableStatement cstmt = util.conn.prepareCall(query1);
                    boolean hasMoreResultSets = cstmt.execute();

                    while (hasMoreResultSets) {
                        ResultSet resultSet1 = cstmt.getResultSet();
                        while (resultSet != null && resultSet.next()) {
                            res.add(resultSet);
                        }
                        resultSet.close();
                    }
                }
            }

            statement.close();
            databaseUtil.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Book> bookList = new ArrayList<>();

        try {
            for (ResultSet resultSet : res) {
                Book book = generateBookFromResultSet(resultSet);
                bookList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookList;

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

    public static void main(String[] args) throws Exception {
        BookDAO bookDAO = new BookDAO();
//        Book book = new Book();
//        book.setISBN("0312510780");
//        book.setScore(0f);
//        book.setAuthor("Roger Priddy");
//        String s = "Your little one will soon learn some essential first words and pictures with this bright board book. There are 100 color photographs to look at and talk about, and 100 simple first words to read and learn, too. The pages are made from tough board for hours of fun reading, and the cover is softly padded for little hands to hold.";
//        book.setDescription(s);
//        book.setTitle("First 100 Words");
//        book.setGenre("Board book");
//        book.setImageUrl("https://images-na.ssl-images-amazon.com/images/I/51AvKQWCl%2BL._SX405_BO1,204,203,200_.jpg");
//        bookDAO.addBook(book);
        System.out.println(bookDAO.getAllBooks("flower Priddy").size());

    }
}