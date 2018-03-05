package edu.wpi.cs.cloudcomputing.database;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.wpi.cs.cloudcomputing.model.Book;
import edu.wpi.cs.cloudcomputing.model.Review;
import edu.wpi.cs.cloudcomputing.model.User;

import java.rmi.server.UID;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by tonggezhu on 2/25/18.
 */
public class ReviewDAO {

    DatabaseUtil databaseUtil;

    public ReviewDAO() {
        this.databaseUtil = new DatabaseUtil();
    }

    public boolean addReview(Review review) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }

        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query = insertReviewQuery(review);
            System.out.println(query);
            statement.execute(query);
            return true;
        } catch (Exception e) {
            throw new Exception("Failed too insert book: " + e.getMessage());
        }
    }

    public List<Review> getAllReviewByBookISBN(String bookISBN) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        List<Review> reviewList = new ArrayList<>();
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query = "SELECT * FROM Review WHERE review_book_id='" + bookISBN +"';";
            ResultSet resultSet = statement.executeQuery(query);
            while ((resultSet.next())) {
                Review review = generateBookFromResultSet(resultSet);
                reviewList.add(review);
            }
            resultSet.close();
            statement.close();
            databaseUtil.conn.close();

            return reviewList;

        } catch (Exception e) {
            throw new Exception("Failed in getting reviews: " + e.getMessage());
        }
    }

    public List<Review> getAllReviewByUserId(String userEmail) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        List<Review> reviewList = new ArrayList<>();
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query = "SELECT * FROM review WHERE review_user_id='" + userEmail +"';";
            ResultSet resultSet = statement.executeQuery(query);
            while ((resultSet.next())) {
                Review review = generateBookFromResultSet(resultSet);
                reviewList.add(review);
            }
            resultSet.close();
            statement.close();
            databaseUtil.conn.close();
            return reviewList;
        } catch (Exception e) {
            throw new Exception("Failed in getting reviews: " + e.getMessage());
        }
    }

    public boolean deleteReview(String reviewId) throws Exception {
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String deleteQuery ="DELETE FROM Review WHERE review_id='" + reviewId + "';";
            statement.execute(deleteQuery);
            statement.close();
            databaseUtil.conn.close();
            return true;
        } catch (Exception e) {
            throw new Exception("Failed to delete book: " + e.getMessage());
        }
    }

    public boolean thumbUpReview(Review review) throws Exception{
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        try {
            Statement statement = databaseUtil.conn.createStatement();

            String updateQuery = "UPDATE Review SET review_thumb_up_num=" + review.getThumbUpNumber()
                    + " WHERE review_id='" + review.getReviewId() + "' ;";
            //System.out.println(updateQuery);
            statement.execute(updateQuery);
            statement.close();
            databaseUtil.conn.close();
            return true;
        } catch (Exception e) {
            throw new Exception("Failed to update thumbUpNumber: " + e.getMessage());
        }

    }

    private String insertReviewQuery(Review review) {

        String Date = databaseUtil.currentDate();
        String columns = "INSERT INTO Review (review_id, review_book_id, review_user_id, review_thumb_up_num, review_content,review_datetime)";
        String values = " values ('" + review.getReviewId() + "', '" + review.getBookISBN() + "', '" + review.getReviewer().getEmail() + "', '"
                + review.getThumbUpNumber() + "', '" + review.getContent() + "'," + Date + ");";
        System.out.println(columns + values);
        return columns + values;
    }

    private Review generateBookFromResultSet(ResultSet resultSet) throws Exception {

        String bookISBN = resultSet.getString("review_book_id");
        UserDAO userDAO = new UserDAO();
        System.out.println(resultSet.getString("review_user_id"));
        User user = userDAO.getUser(resultSet.getString("review_user_id"));

        Integer thumb_up_num = resultSet.getInt("review_thumb_up_num");
        String content = resultSet.getString("review_content");
        String reviewId = resultSet.getString("review_id");
        Review review = new Review(content, thumb_up_num, user, bookISBN, reviewId);
        return review;

    }

    public static void main(String[] args) {
        ReviewDAO r1 = new ReviewDAO();

        Review review = new Review();
        review.setThumbUpNumber(4);
        review.setBookISBN("0156031442");
        review.setReviewId();
        User u = new User();
        u.setEmail("USER1@EMAIL.COM");
        review.setReviewer(u);
        review.setContent("review4");
        try{
        r1.addReview(review);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
