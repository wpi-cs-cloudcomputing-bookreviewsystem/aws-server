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

    public void addReview(Review review) throws Exception {
        if (databaseUtil.conn == null) {
            databaseUtil.initDBConnection();
        }
        try {
            System.out.println("starttest");
            Statement statement = databaseUtil.conn.createStatement();
            System.out.println("connect is ok");
            String query = insertReviewQuery(review);
            statement.executeUpdate(query);
        } catch (Exception e) {
            throw new Exception("Failed too insert book: " + e.getMessage());
        }
    }

    public List<Review> getAllReview() throws Exception {
        if (databaseUtil.conn == null) {
            databaseUtil.initDBConnection();
        }
        List<Review> reviewList = new ArrayList<>();
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query = "SELECT * FROM review;";
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

    private String insertReviewQuery(Review review) {
        String reviewId = new UID().toString().split(":")[1];
        String Date = databaseUtil.currentDate();
        String columns = "INSERT INTO Review (review_id, review_book_id, review_user_id, review_thumb_up_num, review_content,review_datetime)";
        String values = " values ('" + reviewId + "', '" + review.getBookISBN() + "', '" + review.getReviewer().getEmail() + "', '"
                + review.getThumbUpNumber() + "', '" + review.getContent() + "'," + Date + ");";
        System.out.println(columns + values);
        return columns + values;
    }

    private Review generateBookFromResultSet(ResultSet resultSet) throws Exception {

        String bookISBN = resultSet.getString("review_book_id");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(resultSet.getString("review_user_id"));
        Integer thumb_up_num = resultSet.getInt("review_thumb_up_num");
        String content = resultSet.getString("review_content");
        Review review = new Review(content, thumb_up_num, user, bookISBN);
        return review;

    }

//    public static void main(String[] args) {
//        ReviewDAO r1 = new ReviewDAO();
//        Review r= new Review();
//        User u = new User();
//        r.setBookISBN("test");
//        r.setContent("content");
//        r.setReviewer(u);
//
//        r.setThumbUpNumber(5);
//        try {
//            r1.addReview(r);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
