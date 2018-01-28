package edu.wpi.cs.cloudcomputing.controller;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import edu.wpi.cs.cloudcomputing.database.BookDAO;
import edu.wpi.cs.cloudcomputing.model.Book;

public class BookManager {
	public String getAllBooks() throws Exception {

		BookDAO bookDAO = new BookDAO();
		List<Book> allBooks = bookDAO.getAllBooks();
		
		/*	demo without connecting to DB
		List<Book> allBooks = new ArrayList<>();
		allBooks.add(this.createABook());
		 */

		Gson gson = new GsonBuilder().create();
		Type listType = new TypeToken<List<Book>>(){}.getType();
		return gson.toJson(allBooks, listType);

	}

    private Book createABook(){
    	Book demoBook = new Book();
        demoBook.setTitle("DemoBookTitle");
        demoBook.setAuthor("DemoAuthor");
        demoBook.setDescription("This book is about a demo book of this review system");
        demoBook.setGenre("DemoGenre");
        demoBook.setISBN("DemoISBN");
        demoBook.setScore((float) 4.5);
        demoBook.setImageUrl("");
        return demoBook;
    }
}
