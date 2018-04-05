package edu.wpi.cs.cloudcomputing.database;

import edu.wpi.cs.cloudcomputing.model.Review;
import edu.wpi.cs.cloudcomputing.model.User;

import java.rmi.server.UID;
import java.sql.Statement;

/**
 * Created by tonggezhu on 2/28/18.
 */
public class FlagReviewDAO {
//    DatabaseUtil databaseUtil;
//
//    public FlagReviewDAO() {
//        this.databaseUtil = new DatabaseUtil();
//    }
//
//    public void addFlagReview(Review review, User reporter) throws Exception {
//        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
//            databaseUtil.initDBConnection();
//        }
//        try {
//            Statement statement = databaseUtil.conn.createStatement();
//            String insertQuery = insertflagReviewQuery(review, reporter);
//            statement.executeUpdate(insertQuery);
//            statement.close();
//            databaseUtil.conn.close();
//        } catch (Exception e) {
//            throw new Exception("Failed in inserting flag_reviews: " + e.getMessage());
//        }
//    }
//
//    public void removeFlageReview(Review review) throws Exception {
//        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
//            databaseUtil.initDBConnection();
//        }
//
//        try {
//            Statement statement = databaseUtil.conn.createStatement();
//            String deleteQuery = "DELETE FROM Flag_review WHERE flag_review_review_id='" + review.getReviewId() + "';";
//            statement.execute(deleteQuery);
//            statement.close();
//            databaseUtil.conn.close();
//
//        } catch (Exception e) {
//            throw new Exception("Failed in inserting flag_reviews: " + e.getMessage());
//        }
//    }
//
//    private String insertflagReviewQuery(Review review, User user) {
//        String flagReviewId = new UID().toString().split(":")[1];
//        String columns = "INSERT INTO Flag_Review (flag_review_id, flag_review_from_user_id,flag_review_review_id, flag_review_datetime)";
//        String values = "values ('" + flagReviewId + "','" + user.getEmail() + "','" + review.getReviewId() + "','" + databaseUtil.currentDate() + "');";
//        return columns + values;
//    }

}
