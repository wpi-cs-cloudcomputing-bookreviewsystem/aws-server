package edu.wpi.cs.cloudcomputing.model.messages;

/**
 * Created by tonggezhu on 3/1/18.
 */
public class GetBookDetailRequest {
    String isbn;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
