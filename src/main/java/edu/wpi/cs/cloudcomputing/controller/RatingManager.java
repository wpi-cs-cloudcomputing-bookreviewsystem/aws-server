package edu.wpi.cs.cloudcomputing.controller;

import edu.wpi.cs.cloudcomputing.database.BookDAO;
import edu.wpi.cs.cloudcomputing.database.RatingDAO;
import edu.wpi.cs.cloudcomputing.model.Book;
import edu.wpi.cs.cloudcomputing.model.Rating;

/**
 * Created by tonggezhu on 4/4/18.
 */
public class RatingManager {

    RatingDAO ratingDAO;

    public RatingManager() {
        this.ratingDAO = new RatingDAO();
    }

    public float getScore(String isbn) throws Exception {
        float rating = ratingDAO.getAvergeRatingFromBookISBN(isbn);
        return rating;
    }

    public float addRating(String Email, String isbn, float score) throws Exception{
        Rating rating = new Rating();
        rating.setScore(score);
        rating.setBookISBN(isbn);
        rating.setEmail(Email);
        ratingDAO.addRating(rating);
        return ratingDAO.getAvergeRatingFromBookISBN(isbn);
    }

}
