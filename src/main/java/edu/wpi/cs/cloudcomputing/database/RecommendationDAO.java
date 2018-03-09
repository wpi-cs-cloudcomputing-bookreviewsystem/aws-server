package edu.wpi.cs.cloudcomputing.database;

import edu.wpi.cs.cloudcomputing.model.Recommendation;
import edu.wpi.cs.cloudcomputing.model.Review;
import edu.wpi.cs.cloudcomputing.model.User;

import java.rmi.server.UID;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonggezhu on 3/1/18.
 */
public class RecommendationDAO {
    DatabaseUtil databaseUtil;

    public RecommendationDAO() {
        databaseUtil = new DatabaseUtil();
    }

    public void addRecommendation(Recommendation recommendation) throws Exception{
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }

        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query = insertRecommendationQuery(recommendation);
            statement.execute(query);
        } catch (Exception e) {
            throw new Exception("Failed too insert recommendation: " + e.getMessage());
        }
    }

    public List<String> getRecommendationByUser(String useremail) throws Exception{
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        List<String> bookISBNList = new ArrayList<>();

        return bookISBNList;
    }

    private String insertRecommendationQuery(Recommendation recommendation) {
        String recommendationId = new UID().toString().split(":")[1];
        String date = databaseUtil.currentDate();
        String columns = "INSERT INTO Recommendation(recommendation_id, recommendation_user_id,recommendation_book_id, recommendation_datetime)" ;
        String values = "values ('" + recommendationId + "','" + recommendation.getUser().getEmail() + "','" + recommendation.getBook().getISBN() + "'," + date  + ");";
        return columns + values;
    }
}
