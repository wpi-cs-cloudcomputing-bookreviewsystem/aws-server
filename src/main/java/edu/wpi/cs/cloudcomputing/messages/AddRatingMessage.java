package edu.wpi.cs.cloudcomputing.messages;

/**
 * Created by tonggezhu on 3/2/18.
 */
public class AddRatingMessage {
    Float score;
    String isbn;
    String email;

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
