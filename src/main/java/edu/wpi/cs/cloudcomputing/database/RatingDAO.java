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
    DatabaseUtil databaseUtil;

    public RatingDAO() {
        this.databaseUtil = new DatabaseUtil();
    }

    public float getAvergeRatingFromBookISBN(String bookISBN) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        Float res = 0.0f;
        try {
            Statement statement = databaseUtil.conn.createStatement();

            String query = "SELECT AVG(rating_score) AS avg_rating FROM Rating WHERE rating_book_id='" + bookISBN + "';";
            System.out.println(query);
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
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String queryIfExist = "SELECT * FROM Rating WHERE rating_user_id='" + rating.getUser().getEmail() + "'and rating_book_id='" + rating.getBookISBN() + "' ;";
            System.out.println(queryIfExist);
            ResultSet resultSet = statement.executeQuery(queryIfExist);
            if (resultSet.next()) {
                String updateQuery = "UPDATE Rating SET rating_score=" + rating.getScore()
                        + "WHERE rating_user_id='" + rating.getUser().getEmail() + "'and rating_book_id='" + rating.getBookISBN() + "' ;";
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

    public Float getRatingByUserId(String useremail, String bookISBN) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        Float rating = null;
        try {
            Statement statement = databaseUtil.conn.createStatement();

            String query = "SELECT * FROM Rating WHERE rating_user_id='" + useremail + "' and rating_book_id='" + bookISBN + "';";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                rating = Float.valueOf(resultSet.getFloat("rating_score"));
            }
            resultSet.close();
            statement.close();
            return rating;

        } catch (Exception e) {
            throw new Exception("Failed in getting rating by user_email: " + e.getMessage());
        }

    }

    private String insertRatingQuery(Rating rating) {
        String ratingId = new UID().toString().split(":")[1];
        String Date = databaseUtil.currentDate();
        String columns = "INSERT INTO Rating (rating_id, rating_book_id, rating_score, rating_user_id, rating_datetime)";
        String values = " values ('" + ratingId + "', '" + rating.getBookISBN() + "', '" + rating.getScore() + "', '"
                + rating.getUser().getEmail() + "', " + Date + ");";
        System.out.println(columns + values);
        return columns + values;
    }

//    public static void main(String[] args) throws Exception {
//        User u1 = new User("test1", "test1@test.com");
//        User u2 = new User("test2", "test2@test.com");
//        String b1 = "book1";
//        String b2 = "book2";
//        Rating r1 = new Rating(b1, u1, 4.8f);
//        Rating r2 = new Rating(b1, u2, 4.0f);
//        Rating r3 = new Rating(b2, u2, 4.5f);
//        RatingDAO ratingDAO = new RatingDAO();
//        RatingDAO ratingDAO1 = new RatingDAO();
//
//        try {
//            //ratingDAO.addRating(r2);
//            System.out.println(ratingDAO1.getRatingByUserId(u2.getEmail(), b1));
//            //System.out.println(ratingDAO.getAvergeRatingFromBookISBN(b1));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
