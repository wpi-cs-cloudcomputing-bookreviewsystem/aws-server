package edu.wpi.cs.cloudcomputing.database;

import edu.wpi.cs.cloudcomputing.model.Rating;
import edu.wpi.cs.cloudcomputing.model.User;

import java.rmi.server.UID;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Created by tonggezhu on 2/25/18.
 */
public class RatingDAO {

    public float getAvergeRatingFromBookISBN(String bookISBN) throws Exception {

        Float res = 0.0f;
        try {
            Statement statement = DatabaseUtil.getConnection().createStatement();

            String query = "SELECT AVG(rating_score) AS avg_rating FROM Rating WHERE rating_book_id='" + bookISBN + "';";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                res = resultSet.getFloat("avg_rating");
            }
            resultSet.close();
            statement.close();

            return res;

        } catch (Exception e) {
            throw new Exception("Failed in getting average rating: " + e.getMessage());
        }
    }

    public boolean addRating(Rating rating) throws Exception {

        try {
            Statement statement = DatabaseUtil.getConnection().createStatement();
            String queryIfExist = "SELECT * FROM Rating WHERE rating_user_id='" + rating.getEmail() + "'and rating_book_id='" + rating.getBookISBN() + "' ;";
            ResultSet resultSet = statement.executeQuery(queryIfExist);
            if (resultSet.next()) {
                String updateQuery = "UPDATE Rating SET rating_score=" + rating.getScore()
                        + "WHERE rating_user_id='" + rating.getEmail() + "'and rating_book_id='" + rating.getBookISBN() + "' ;";
                statement.execute(updateQuery);
                statement.close();
                return true;

            } else {
                String query = insertRatingQuery(rating);
                statement.execute(query);
                statement.close();
                return true;
            }
        } catch (Exception e) {
            throw new Exception("Failed in insert rating: " + e.getMessage());
        }
    }

    private String insertRatingQuery(Rating rating) {
        String ratingId = new UID().toString().split(":")[1];
        String Date = DatabaseUtil.currentDate();
        String columns = "INSERT INTO Rating (rating_id, rating_book_id, rating_score, rating_user_id, rating_datetime)";
        String values = " values ('" + ratingId + "', '" + rating.getBookISBN() + "', '" + rating.getScore() + "', '"
                + rating.getEmail() + "', " + Date + ");";
        return columns + values;
    }

}
