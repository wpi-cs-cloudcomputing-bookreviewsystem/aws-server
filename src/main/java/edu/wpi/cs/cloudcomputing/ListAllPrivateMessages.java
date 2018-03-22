package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import edu.wpi.cs.cloudcomputing.controller.MessageManager;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.messages.SearchBookMessage;
import edu.wpi.cs.cloudcomputing.messages.UserIdMessage;

/**
 * Created by tonggezhu on 3/22/18.
 */
public class ListAllPrivateMessages implements RequestHandler<Object, String> {
    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);

        UserIdMessage message = null;
        ResponseMessage responseMsg = new ResponseMessage();
        MessageManager messageManager = new MessageManager();
        Gson gson = new Gson();
        try {
            message = gson.fromJson(input.toString(), UserIdMessage.class);
            if(message == null || message.getEmail() == null){
                throw new Exception();}

        }catch (Exception ex) {
            responseMsg.setStatus("FAILURE");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        try {
            String allMessages= messageManager.getAllMessage(message.getEmail());
            context.getLogger().log("allMessages: " + allMessages);
            responseMsg.setStatus("SUCCESS");
            responseMsg.setContent(allMessages);
        } catch(Exception ex) {
            responseMsg.setStatus("FAILURE");
            responseMsg.setContent(ex.getMessage());
        }

        String output = gson.toJson(responseMsg);
        context.getLogger().log("output: " + output);
        return output;
    }
}
