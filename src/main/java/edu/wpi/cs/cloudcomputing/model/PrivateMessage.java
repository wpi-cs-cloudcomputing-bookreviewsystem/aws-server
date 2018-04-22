package edu.wpi.cs.cloudcomputing.model;


public class PrivateMessage {

    private String pmId;
    private String senderEmail;
    private String receiverEmail;
    private String title;
    private String content;
    private String status;
    private String type;

    public PrivateMessage() {
    }

    public PrivateMessage(String pmId, String sender, String receiver, String title, String content, String status, String type) {
        this.pmId = pmId;
        this.senderEmail = sender;
        this.receiverEmail = receiver;
        this.title = title;
        this.content = content;
        this.status = status;
        this.type = type;
    }

    public String getPmId() { return pmId; }

    public void setPmId(String pmId) { this.pmId = pmId; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getSenderEmail() { return senderEmail; }

    public void setSenderEmail(String senderEmail) { this.senderEmail = senderEmail; }

    public String getReceiverEmail() { return receiverEmail; }

    public void setReceiverEmail(String receiverEmail) { this.receiverEmail = receiverEmail; }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
