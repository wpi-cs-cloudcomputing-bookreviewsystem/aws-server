package edu.wpi.cs.cloudcomputing.controller;

import edu.wpi.cs.cloudcomputing.database.ReviewDAO;
import edu.wpi.cs.cloudcomputing.model.Review;
import edu.wpi.cs.cloudcomputing.model.User;

import java.rmi.server.UID;
import java.util.List;

public class ReviewManager {
    ReviewDAO reviewDAO;

    public List<Review> getReviewsByBookISBN(String isbn) throws Exception{
        reviewDAO = new ReviewDAO();
        List<Review> reviewList = reviewDAO.getAllReviewByBookISBN(isbn);
        return reviewList;
    }

    public boolean AddReview(String userEmail, String ISBN, String content) throws Exception{
        reviewDAO = new ReviewDAO();
        Review review = new Review();
        String reviewId =System.currentTimeMillis()%100000000+"";
        review.setReviewId(reviewId);
        review.setThumbUpNumber(0);
        review.setContent(content);
        review.setBookISBN(ISBN);
        User user = new User();
        user.setEmail(userEmail);
        review.setReviewer(user);
        reviewDAO.addReview(review);
        return true;

    }

    public boolean thumbUpReview(String reviewId, Integer thumbUpNum) throws Exception{
        Boolean res = false;
        reviewDAO = new ReviewDAO();
        Review review = new Review();
        review.setThumbUpNumber(thumbUpNum+1);
        review.setReviewId(reviewId);
        res = reviewDAO.thumbUpReview(review);
        return res;
    }

}
