package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.wpi.cs.cloudcomputing.controller.MessageManager;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.messages.UserIdMessage;


/**
 * Created by tonggezhu on 4/4/18.
 */
public class ListAllRecommendBook implements RequestHandler<Object, String> {

    Gson gson;
    ResponseMessage responseMsg;
    MessageManager messageManager;
    UserIdMessage message;

    public ListAllRecommendBook() {
        System.out.println("list all recommend books initiating");
        gson = new GsonBuilder().create();
        responseMsg = new ResponseMessage();
        messageManager = new MessageManager();
    }

    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);
        try {
            message = gson.fromJson(input.toString(), UserIdMessage.class);
            if (message == null || message.getEmail() == null) {
                throw new Exception();}
        }catch (Exception ex) {
            responseMsg.setStatus("FAILURE in translate input");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        try {
            String booklist = messageManager.getRecommendBooks(message.getEmail());
            responseMsg.setStatus("SUCCESS");
            responseMsg.setContent(booklist);
        }
        catch(Exception ex) {
            responseMsg.setStatus("FAILURE");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        context.getLogger().log("output: " + responseMsg.toString());
        return gson.toJson(responseMsg);
    }
}
