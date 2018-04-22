package edu.wpi.cs.cloudcomputing.model.messages;

/**
 * Created by tonggezhu on 3/7/18.
 */
public class GetProfileMessage{
    String fromEmail;
    String toEmail;

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
