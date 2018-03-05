package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.wpi.cs.cloudcomputing.controller.ReviewManager;
import edu.wpi.cs.cloudcomputing.messages.AddReviewMessage;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;

/**
 * Created by tonggezhu on 3/2/18.
 */
public class AddBookWithReview implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);
        Gson gson = new GsonBuilder().create();
        AddReviewMessage addReviewMessage = null;
        ResponseMessage responseMsg = new ResponseMessage();
        ReviewManager reviewManager = new ReviewManager();
        try {
            addReviewMessage = gson.fromJson(input.toString(), AddReviewMessage.class);
            if (addReviewMessage == null || addReviewMessage.getContent() == null
                    || addReviewMessage.getEmail() == null
                    || addReviewMessage.getIsbn() == null) {
                throw new Exception();}
        }catch (Exception ex) {
            responseMsg.setStatus("FAILURE");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        try {
            Boolean res = reviewManager.AddReview(addReviewMessage.getEmail(), addReviewMessage.getIsbn(), addReviewMessage.getContent());
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
