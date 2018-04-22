package edu.wpi.cs.cloudcomputing.model;


public class Review {

    private String reviewId;
    private String content;
    private Integer thumbUpNumber;
    private User reviewer;
    private String bookISBN;

    public Review() {
    }

    public Review(String content, Integer thumbUpNumber, User reviewer, String bookISBN, String reviewId) {
        this.content = content;
        this.thumbUpNumber = thumbUpNumber;
        this.reviewer = reviewer;
        this.bookISBN = bookISBN;
        this.reviewId = reviewId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getThumbUpNumber() {
        return thumbUpNumber;
    }

    public void setThumbUpNumber(Integer thumbUpNumber) {
        this.thumbUpNumber = thumbUpNumber;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

}
