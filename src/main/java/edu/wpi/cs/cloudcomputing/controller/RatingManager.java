package edu.wpi.cs.cloudcomputing.controller;

import edu.wpi.cs.cloudcomputing.database.RatingDAO;
import edu.wpi.cs.cloudcomputing.database.ReviewDAO;
import edu.wpi.cs.cloudcomputing.model.Rating;
import edu.wpi.cs.cloudcomputing.model.Review;
import edu.wpi.cs.cloudcomputing.model.User;

/**
 * Created by tonggezhu on 3/2/18.
 */
public class RatingManager {
    RatingDAO ratingDAO;
    public Float AddRating(String userEmail, String ISBN, float score) throws Exception{
        ratingDAO = new RatingDAO();
        Rating rating = new Rating();

        rating.setBookISBN(ISBN);
        rating.setScore(score);
        User user = new User();
        user.setEmail(userEmail);
        rating.setUser(user);
        System.out.println(rating.getBookISBN());
        ratingDAO.addRating(rating);
        System.out.println(rating.getScore());
        Float newScore = ratingDAO.getAvergeRatingFromBookISBN(ISBN);
        return newScore;

    }
}
