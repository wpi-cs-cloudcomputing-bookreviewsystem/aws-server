package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.wpi.cs.cloudcomputing.controller.BookManager;
import edu.wpi.cs.cloudcomputing.controller.ReviewManager;
import edu.wpi.cs.cloudcomputing.model.messages.AddBookWithReviewMessage;
import edu.wpi.cs.cloudcomputing.model.messages.ResponseMessage;

/**
 * Created by tonggezhu on 3/2/18.
 */
public class AddBookWithReview implements RequestHandler<Object, String> {

    Gson gson;
    AddBookWithReviewMessage message;
    ResponseMessage responseMsg;
    ReviewManager reviewManager;
    BookManager bookManager;

    public AddBookWithReview() {
        System.out.println("Add Book With Review initiating");
        gson = new GsonBuilder().create();
        responseMsg = new ResponseMessage();
        reviewManager = new ReviewManager();
        bookManager = new BookManager();

    }

    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);
        try {
            message = gson.fromJson(input.toString(), AddBookWithReviewMessage.class);
            if (message == null || message.getAuthor() == null || message.getISBN() == null
                    || message.getTitle() == null || message.getEmail() == null
                    || message.getContent() == null) {
                throw new Exception();}
        }catch (Exception ex) {
            responseMsg.setStatus("FAILURE to parser Json");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        try {
            reviewManager.AddReview(message.getEmail(), message.getISBN(), message.getContent());
            Boolean res = bookManager.addBook(message.getTitle(), message.getAuthor(), message.getISBN(),
                    message.getDescription(), message.getImageUrl(), message.getGenre());
            responseMsg.setStatus("SUCCESS");
            responseMsg.setContent(res.toString());
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
