package edu.wpi.cs.cloudcomputing.messages;

/**
 * Created by tonggezhu on 3/7/18.
 */
public class FriendMessage {
    String fromEmail;
    String toEmail;
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }
}
