package edu.wpi.cs.cloudcomputing.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
		Gson gson = new GsonBuilder().create();
		Type listType = new TypeToken<List<Book>>(){}.getType();
		return gson.toJson(allBooks, listType);
	}

	public String getBook(String isbn) throws Exception{
		BookDAO bookDAO = new BookDAO();
		Book book = bookDAO.getBook(isbn);
		Gson gson = new GsonBuilder().create();
		return gson.toJson(book);
	}
	
//	public String getAllCachedBooks() throws Exception {
//		List<Book> allBooks = createLibrary();
//		Gson gson = new GsonBuilder().create();
//		Type listType = new TypeToken<List<Book>>(){}.getType();
//		return gson.toJson(allBooks, listType);
//	}
//
//	public String getCachedBooks(String ISBN) throws Exception {
//		List<Book> allBooks = createLibrary();
//		for (Book book : allBooks) {
//			if (book.getISBN().equals(ISBN)) {
//				Gson gson = new GsonBuilder().create();
//				return gson.toJson(book);
//			}
//		}
//		throw new Exception("Book Not Found");
//	}
	
//	private List<Book> createLibrary() {
//		List<Book> library = new ArrayList<>();
//		library.add(this.createBookOne());
//		library.add(this.createBookTwo());
//		return library;
//	}

//    private Book createBookOne(){
//    	Book demoBook = new Book();
//        demoBook.setTitle("Animals in Translation: Using the Mysteries of Autism to Decode Animal Behavior");
//        demoBook.setAuthor("Temple Grandin, Catherine Johnson");
//        demoBook.setDescription("In this exciting new e edition, Temple Grandin returns to her groundbreaking work, Animals in Translation, to address the last ten years of developments in behavioral research, animal welfare, and farming regulations. Originally published in 2005, Animals in Translation received unanimous critical praise and was a bestseller in both hardcover and paperback, and Grandin’s Q&A updates this classic text with the most current scientific research.");
//        demoBook.setGenre("Animal");
//        demoBook.setISBN("0156031442");
//        demoBook.setScore((float) 4.5);
//        demoBook.setImageUrl("http://www.templegrandin.com/_images/_books/AnimalsAutismTemple.png");
//        return demoBook;
//    }
//
//    private Book createBookTwo(){
//    	Book demoBook = new Book();
//        demoBook.setTitle("Organic Chemistry As a Second Language: First Semester Topics");
//        demoBook.setAuthor("David R. Klein");
//        demoBook.setDescription("Readers continue to turn to Klein's Organic Chemistry as a Second Language: First Semester Topics, 4th Edition because it enables them to better understand fundamental principles, solve problems, and focus on what they need to know to succeed. This edition explores the major principles in the field and explains why they are relevant. It is written in a way that clearly shows the patterns in organic chemistry so that readers can gain a deeper conceptual understanding of the material. Topics are presented clearly in an accessible writing style along with numerous hands-on problem solving exercises.");
//        demoBook.setGenre("Science");
//        demoBook.setISBN("1119110661");
//        demoBook.setScore((float) 4.5);
//        demoBook.setImageUrl("https://prodimage.images-bn.com/pimages/9781119110668_p0_v2_s600x595.jpg");
//        return demoBook;
//    }
}
