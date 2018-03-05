package edu.wpi.cs.cloudcomputing.messages;

/**
 * Created by tonggezhu on 3/2/18.
 */
public class AddReviewMessage {
    //xmlHttp.send(JSON.stringify({"content":reviewText, "email":user.email, "isbn":isbn}));
    String content;
    String email;
    String isbn;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
