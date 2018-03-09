package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import edu.wpi.cs.cloudcomputing.controller.MessageManager;
import edu.wpi.cs.cloudcomputing.controller.UserManager;
import edu.wpi.cs.cloudcomputing.messages.FriendMessage;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.utils.Common;

/**
 * Created by tonggezhu on 3/7/18.
 */
public class AddFriend implements RequestHandler<Object, String> {
    @Override
    public String handleRequest(Object input, Context context) {

        context.getLogger().log("Input: " + input);

        ResponseMessage responseMsg = new ResponseMessage();
        UserManager userManager = new UserManager();
        MessageManager messageManager = new MessageManager();
        FriendMessage message = null;
        Gson gson = new Gson();
        try {

            message = gson.fromJson(input.toString(), FriendMessage.class);
            if (message == null || message.getFromEmail() == null
                    || message.getToEmail() == null || message.getMessage() == null){
                throw new Exception();}
        }
        catch(Exception ex) {
            responseMsg.setStatus(Common.BAD_REQUEST);
            responseMsg.setContent(ex.getMessage());
        }

        try {
            if(message.getMessage().equals(Common.ADD_FRIEND_REQUEST)){
                userManager.addFriend(message.getFromEmail(), message.getToEmail());
                messageManager.addMessage(message.getFromEmail(), message.getToEmail(), Common.ADD_FRIEND_REQUEST, null, Common.FRIENDSHIP);
            }else{
                userManager.acceptAddFriend(message.getFromEmail(), message.getToEmail());
                messageManager.addMessage(message.getFromEmail(), message.getToEmail(), Common.ADD_FRIEND_ACCEPT_RESPONSE, null, Common.FRIENDSHIP);
            }
            responseMsg.setContent("true");
            responseMsg.setStatus("SUCCESS");
        }
        catch(Exception ex) {
            responseMsg.setStatus("FAILURE");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        String output = gson.toJson(responseMsg);

        context.getLogger().log("output: " + output);
        return output;

    }
}
