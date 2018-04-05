package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.cloudcomputing.controller.BookManager;
import edu.wpi.cs.cloudcomputing.controller.RatingManager;
import edu.wpi.cs.cloudcomputing.controller.ReviewManager;
import edu.wpi.cs.cloudcomputing.messages.GetBookDetailRequest;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.model.Book;
import edu.wpi.cs.cloudcomputing.model.Review;

import java.util.List;

public class GetBookDetail implements RequestHandler<Object, String> {

    Gson gson;
    GetBookDetailRequest getBookDetailRequest;
    ResponseMessage responseMsg;
    BookManager bookManager;
    ReviewManager reviewManager;
    RatingManager ratingManager;

    public GetBookDetail() {
        System.out.println("get book detail initiated");
        gson = new Gson();
        responseMsg = new ResponseMessage();
        bookManager = new BookManager();
        reviewManager = new ReviewManager();
        ratingManager = new RatingManager();
    }

    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);
        try {
            getBookDetailRequest = gson.fromJson(input.toString(), GetBookDetailRequest.class);
            if (getBookDetailRequest == null || getBookDetailRequest.getIsbn() == null) {
                throw new Exception();}
            }catch (Exception ex) {
                responseMsg.setStatus("FAILURE");
                responseMsg.setContent(ex.getMessage());
                return gson.toJson(responseMsg);
            }

        try {       	
        	Book book = bookManager.getBook(getBookDetailRequest.getIsbn());
        	float score = ratingManager.getScore(getBookDetailRequest.getIsbn());
        	List<Review> reviewList = reviewManager.getReviewsByBookISBN(getBookDetailRequest.getIsbn());
        	book.setReviews(reviewList);
        	book.setScore(score);
            Gson gson2 = new GsonBuilder().create();
            String responContent = gson2.toJson(book, Book.class);
        	responseMsg.setStatus("SUCCESS");
        	responseMsg.setContent(responContent);
        }
        catch(Exception ex) {
        	responseMsg.setStatus("FAILURE");
        	responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        String response = gson.toJson(responseMsg);
        context.getLogger().log("output: " + response);
        return response;
    }

}
