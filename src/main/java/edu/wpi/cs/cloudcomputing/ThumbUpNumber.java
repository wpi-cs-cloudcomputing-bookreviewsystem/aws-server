package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import edu.wpi.cs.cloudcomputing.controller.ReviewManager;
import edu.wpi.cs.cloudcomputing.messages.AddReviewMessage;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.messages.ThumbUpReviewMessage;

import java.io.StringReader;

/**
 * Created by tonggezhu on 3/4/18.
 */
public class ThumbUpNumber implements RequestHandler<Object, String> {

    Gson gson;
    ResponseMessage responseMsg;
    ReviewManager reviewManager;
    ThumbUpReviewMessage message;

    public ThumbUpNumber() {
        System.out.println("thunb up number initiating");

        gson = new GsonBuilder().create();
        responseMsg = new ResponseMessage();
        reviewManager = new ReviewManager();
    }

    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);
        try {
            message = gson.fromJson(input.toString(), ThumbUpReviewMessage.class);
            if (message == null || message.getReviewId()== null
                    || message.getNum() == null) {
                throw new Exception();}
        }catch (Exception ex) {
            responseMsg.setStatus("FAILURE in translate input");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        try {
            Boolean res = reviewManager.thumbUpReview(message.getReviewId(), message.getNum());
            responseMsg.setStatus("SUCCESS");
            responseMsg.setContent(res.toString());
        }
        catch(Exception ex) {
            responseMsg.setStatus("FAILURE");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        context.getLogger().log("output: " + message.toString());
        return gson.toJson(responseMsg);
    }

}
