package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import edu.wpi.cs.cloudcomputing.controller.MessageManager;
import edu.wpi.cs.cloudcomputing.controller.ReviewManager;
import edu.wpi.cs.cloudcomputing.messages.PMMessage;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.messages.ThumbUpReviewMessage;
import edu.wpi.cs.cloudcomputing.model.PrivateMessage;

import java.io.StringReader;

/**
 * Created by tonggezhu on 3/8/18.
 */
public class SentPrivateMessage implements RequestHandler<Object, String> {

    Gson gson;
    ResponseMessage responseMsg;
    MessageManager messageManager;
    PMMessage message;

    public SentPrivateMessage() {
        System.out.println("sent private msg initiating");
        gson = new GsonBuilder().create();
        responseMsg = new ResponseMessage();
        messageManager = new MessageManager();
    }

    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);
        try {
//            JsonReader reader = new JsonReader(new StringReader(input.toString()));
//            reader.setLenient(true);
            message = gson.fromJson(input.toString(), PMMessage.class);
            if (message == null || message.getFromEmail()== null
                    || message.getToEmail() == null || message.getTitle() == null ) {
                throw new Exception();}
        }catch (Exception ex) {
            responseMsg.setStatus("FAILURE in translate input");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        try {
            Boolean res = messageManager.addMessage(message.getFromEmail(), message.getToEmail(), message.getTitle(), message.getContent(), message.getType());
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
