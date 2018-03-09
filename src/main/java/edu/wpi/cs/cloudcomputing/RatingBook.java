package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.wpi.cs.cloudcomputing.controller.RatingManager;
import edu.wpi.cs.cloudcomputing.controller.ReviewManager;
import edu.wpi.cs.cloudcomputing.messages.AddRatingMessage;
import edu.wpi.cs.cloudcomputing.messages.AddReviewMessage;
import edu.wpi.cs.cloudcomputing.messages.RatingBookResponse;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;

/**
 * Created by tonggezhu on 3/2/18.
 */
public class RatingBook implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);
        Gson gson = new GsonBuilder().create();
        AddRatingMessage addRatingMessage = null;
        ResponseMessage responseMsg = new ResponseMessage();
        RatingManager manager = new RatingManager();
        try {
            addRatingMessage = gson.fromJson(input.toString(), AddRatingMessage.class);
            if (addRatingMessage == null || addRatingMessage.getScore() == null
                    || addRatingMessage.getEmail() == null
                    || addRatingMessage.getIsbn() == null) {
                throw new Exception();}
        }catch (Exception ex) {
            responseMsg.setStatus("FAILURE");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        try {
            Float newScore = manager.AddRating(addRatingMessage.getEmail(),addRatingMessage.getIsbn(),addRatingMessage.getScore());
            responseMsg.setStatus("SUCCESS");
            RatingBookResponse response = new RatingBookResponse();
            response.setScore(newScore);
            String content = gson.toJson(response, RatingBookResponse.class);
            responseMsg.setContent(content);
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
