package edu.wpi.cs.cloudcomputing.model;

/**
 * Created by tonggezhu on 2/25/18.
 */
public class Recommendation {
    private User user;
    private Book book;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
