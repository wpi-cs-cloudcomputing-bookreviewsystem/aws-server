package edu.wpi.cs.cloudcomputing.database;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.wpi.cs.cloudcomputing.model.Book;
import edu.wpi.cs.cloudcomputing.model.Review;
import edu.wpi.cs.cloudcomputing.model.User;

import java.rmi.server.UID;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by tonggezhu on 2/25/18.
 */
public class ReviewDAO {

    public void addReview(Review review) throws Exception {

        try {
            PreparedStatement statement = null;
            String date  = DatabaseUtil.currentDate();
            String query = "INSERT INTO Review (review_id, review_book_id, review_user_id, review_thumb_up_num, review_content,review_datetime) " +
                    "values ( ? , ? , ? , ? , ? , STR_TO_DATE( ?, '%Y-%m-%d %H:%i:%s'))";
            statement = DatabaseUtil.getConnection().prepareStatement(query);
            statement.setString(1, review.getReviewId());
            statement.setString(2, review.getBookISBN());
            statement.setString(3, review.getReviewer().getEmail());
            statement.setInt(4, review.getThumbUpNumber());
            statement.setString(5, review.getContent());
            statement.setString(6, date);
            statement.execute();

            statement.close();

        } catch (Exception e) {
            throw new Exception("Failed too insert book: " + e.getMessage());
        }
    }

    public List<Review> getAllReviewByBookISBN(String bookISBN) throws Exception {

        List<Review> reviewList = new ArrayList<>();
        try {
            Statement statement = DatabaseUtil.getConnection().createStatement();
            String query = "SELECT * FROM Review WHERE review_book_id='" + bookISBN +"';";
            ResultSet resultSet = statement.executeQuery(query);
            while ((resultSet.next())) {
                Review review = generateBookFromResultSet(resultSet);
                reviewList.add(review);
            }
            resultSet.close();
            statement.close();
            return reviewList;

        } catch (Exception e) {
            throw new Exception("Failed in getting reviews: " + e.getMessage());
        }
    }

    public boolean thumbUpReview(Review review) throws Exception{

        try {
            Statement statement = DatabaseUtil.getConnection().createStatement();

            String updateQuery = "UPDATE Review SET review_thumb_up_num=" + review.getThumbUpNumber()
                    + " WHERE review_id='" + review.getReviewId() + "' ;";
            statement.execute(updateQuery);
            statement.close();
            return true;
        } catch (Exception e) {
            throw new Exception("Failed to update thumbUpNumber: " + e.getMessage());
        }

    }

    private Review generateBookFromResultSet(ResultSet resultSet) throws Exception {

        String bookISBN = resultSet.getString("review_book_id");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(resultSet.getString("review_user_id"));

        Integer thumb_up_num = resultSet.getInt("review_thumb_up_num");
        String content = resultSet.getString("review_content");
        String reviewId = resultSet.getString("review_id");
        Review review = new Review(content, thumb_up_num, user, bookISBN, reviewId);
        return review;

    }
}
