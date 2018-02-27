package edu.wpi.cs.cloudcomputing.model;

import java.sql.Time;

/**
 * Created by tonggezhu on 2/25/18.
 */
public class Rating {
    private String bookISBN;
    private User user;
    private float score;

    public Rating() {
    }

    public Rating(String bookISBN, User user, float score) {
        this.bookISBN = bookISBN;
        this.user = user;
        this.score = score;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

}
