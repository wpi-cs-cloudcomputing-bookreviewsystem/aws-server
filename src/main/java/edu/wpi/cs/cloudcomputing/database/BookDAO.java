package edu.wpi.cs.cloudcomputing.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.wpi.cs.cloudcomputing.model.Book;

public class BookDAO {


    public Book getBook(String bookISBN) throws Exception {

        try {
            Book book = null;

            String query = "SELECT * FROM Book WHERE book_isbn=?;";
            PreparedStatement statement = DatabaseUtil.getConnection().prepareStatement(query);
            statement.setString(1, bookISBN);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                book = generateBookFromResultSet(resultSet);
            }
            if (book == null) {
                throw new Exception("Book not found");
            }

            resultSet.close();
            statement.close();

            return book;

        } catch (Exception e) {
            throw new Exception("Failed in getting book: " + e.getMessage());
        }

    }

    public boolean addBook(Book book) throws Exception {

        String bookISBN = book.getISBN();
        try {
            Statement statement = DatabaseUtil.getConnection().createStatement();
            String query = "SELECT * FROM Book WHERE book_isbn = '" + bookISBN + "';";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                resultSet.close();
                return false;
            }
            PreparedStatement statement1 = DatabaseUtil.getConnection().prepareStatement(insertBookQuery());
            statement1.setString(1, book.getTitle());
            statement1.setString(2, book.getAuthor());
            statement1.setString(3, book.getISBN());
            statement1.setString(4, book.getDescription());
            statement1.setString(5, book.getImageUrl());
            statement1.setString(6, book.getGenre());
            statement1.execute();
            statement.close();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed too insert book: " + e.getMessage());
        }
    }

    public List<Book> getAllBooks() throws Exception {

        List<Book> allBooks = new ArrayList<>();

        try {
            Statement statement = DatabaseUtil.getConnection().createStatement();
            String query = "SELECT * FROM Book";
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Book book = generateBookFromResultSet2(resultSet);
                allBooks.add(book);
            }
            resultSet.close();
            statement.close();
            return allBooks;

        } catch (Exception e) {

            throw new Exception("Failed in getting books: " + e.getMessage());
        }

    }


    public List<Book> getAllBooks(String[] words) throws Exception {

        Set<Book> res = new HashSet<>();

        for (String word : words) {
            ResultSet resultSet = null;

            String query = "SELECT * FROM Book WHERE book_title like ? OR book_author like ? OR book_description like ? OR book_isbn=?;";
            PreparedStatement statement = DatabaseUtil.getConnection().prepareStatement(query);
            statement.setString(4, word);

            word = "%" + word + "%";
            statement.setString(1, word);
            statement.setString(2, word);
            statement.setString(3, word);
            System.out.println(statement);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                res.add(generateBookFromResultSet2(resultSet));
            }
            resultSet.close();
        }
        List<Book> allBooks = new ArrayList<>(res);
        return allBooks;


    }

    private Book generateBookFromResultSet(ResultSet resultSet) throws Exception {
        String title = resultSet.getString("book_title");
        String author = resultSet.getString("book_author");
        String ISBN = resultSet.getString("book_isbn");
        String description = resultSet.getString("book_description");
        String imageUrl = resultSet.getString("book_image_Url");
        String genre = resultSet.getString("book_genre");
        return new Book(title, author, ISBN, description, imageUrl, genre, 0.f);
    }

    private Book generateBookFromResultSet2(ResultSet resultSet) throws Exception {
        String title = resultSet.getString("book_title");
        String description = resultSet.getString("book_description");
        String imageUrl = resultSet.getString("book_image_Url");
        String ISBN = resultSet.getString("book_isbn");

        return new Book(title, null, ISBN, description, imageUrl, null, null);
    }

    private String insertBookQuery() {
        String columns = "INSERT INTO Book (book_title, book_author, book_isbn, book_description, book_image_Url, book_genre) " +
                "values ( ?,?,?,?,?,?);";
        return columns;
    }

}