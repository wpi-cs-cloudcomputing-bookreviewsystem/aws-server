package edu.wpi.cs.cloudcomputing.model;


/**
 * Created by tonggezhu on 2/25/18.
 */
public class Rating {
    private String bookISBN;
    private String email;
    private float score;

    public Rating() {
    }

    public Rating(String bookISBN, String email, float score) {
        this.bookISBN = bookISBN;
        this.email = email;
        this.score = score;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

}
