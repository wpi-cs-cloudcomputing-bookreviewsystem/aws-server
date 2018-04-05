package edu.wpi.cs.cloudcomputing.controller;

import java.lang.reflect.Type;
import java.util.List;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import edu.wpi.cs.cloudcomputing.database.BookDAO;
import edu.wpi.cs.cloudcomputing.model.Book;

import static com.amazonaws.services.kms.model.KeyManagerType.AWS;


public class BookManager {
    BookDAO bookDAO = null;

    public String getAllBooks(String words) throws Exception {

        bookDAO = new BookDAO();
        List<Book> allBooks = null;
        if(words == null){
            allBooks = bookDAO.getAllBooks();
        }else{
            String[] wordList = words.split(" ");
            for(String word: wordList){
                System.out.println("========"+word);
            }

            allBooks = bookDAO.getAllBooks(wordList);
        }
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<Book>>() {
        }.getType();


        return gson.toJson(allBooks, listType);
    }

    public Book getBook(String isbn) throws Exception {
        bookDAO = new BookDAO();
        Book book = bookDAO.getBook(isbn);
        return book;
    }

    public Boolean addBook(String title, String author, String ISBN,
                           String description, String imageUrl, String genre) throws Exception {
        bookDAO = new BookDAO();
        Book book = new Book(title, author, ISBN, description, imageUrl, genre, 0f);
        Boolean res = bookDAO.addBook(book);
        return res;
    }
}
