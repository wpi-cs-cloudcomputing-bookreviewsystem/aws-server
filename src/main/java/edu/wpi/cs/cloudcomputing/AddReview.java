package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.stream.JsonReader;
import edu.wpi.cs.cloudcomputing.controller.ReviewManager;
import edu.wpi.cs.cloudcomputing.model.messages.AddReviewMessage;
import edu.wpi.cs.cloudcomputing.model.messages.ResponseMessage;

import java.io.StringReader;


/**
 * Created by tonggezhu on 3/2/18.
 */
public class AddReview implements RequestHandler<Object, String> {


    Gson gson;
    ResponseMessage responseMsg;
    ReviewManager reviewManager;
    AddReviewMessage addReviewMessage;

    public AddReview() {
        System.out.println("Add review initiating");
        gson = new GsonBuilder().create();
        responseMsg = new ResponseMessage();
        reviewManager = new ReviewManager();
    }

    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);

        try {
            JsonReader reader = new JsonReader(new StringReader(input.toString()));
            reader.setLenient(true);
            addReviewMessage = gson.fromJson(reader, AddReviewMessage.class);
            System.out.println("input class: " + input.getClass());
            System.out.println("input is " + input.toString());
            if (addReviewMessage == null || addReviewMessage.getIsbn() == null
                    || addReviewMessage.getEmail() == null
                    || addReviewMessage.getContent() == null) {
                throw new Exception();
            }
        } catch (Exception ex) {
            responseMsg.setStatus("FAILURE in trans");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        try {
            reviewManager.AddReview(addReviewMessage.getEmail(), addReviewMessage.getIsbn(), addReviewMessage.getContent());
            responseMsg.setStatus("SUCCESS");
            responseMsg.setContent("true");
        } catch (Exception ex) {
            responseMsg.setStatus("FAILURE");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        context.getLogger().log("output: " + addReviewMessage.toString());
        return gson.toJson(responseMsg);
    }

}
