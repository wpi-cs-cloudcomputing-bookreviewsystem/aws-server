package edu.wpi.cs.cloudcomputing.model.messages;

/**
 * Created by tonggezhu on 3/4/18.
 */
public class ThumbUpReviewMessage {
    String reviewId;
    Integer num;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
