package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import edu.wpi.cs.cloudcomputing.controller.MessageManager;
import edu.wpi.cs.cloudcomputing.controller.UserManager;
import edu.wpi.cs.cloudcomputing.model.messages.PMMessage;
import edu.wpi.cs.cloudcomputing.model.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.utils.Common;

/**
 * Created by tonggezhu on 3/8/18.
 */
public class RejectAddFriendRequest implements RequestHandler<Object, String> {
    UserManager userManager;
    MessageManager messageManager;
    PMMessage message;
    ResponseMessage responseMsg;
    Gson gson;

    public RejectAddFriendRequest() {
        System.out.println("Reject friend request initiating");
        userManager = new UserManager();
        messageManager = new MessageManager();
        responseMsg = new ResponseMessage();
        gson = new Gson();
    }

    @Override
    public String handleRequest(Object input, Context context) {

        context.getLogger().log("RejectAddFriendRequest Input: " + input);

        UserManager userManager = new UserManager();
        MessageManager messageManager = new MessageManager();
        PMMessage message = new PMMessage();
        ResponseMessage responseMsg = new ResponseMessage();
        Gson gson = new Gson();
        try {

            message = gson.fromJson(input.toString(), PMMessage.class);
            if (message == null || message.getFromEmail() == null
                    || message.getToEmail() == null) {
                throw new Exception();
            }
        } catch (Exception ex) {
            responseMsg.setStatus(Common.BAD_REQUEST);
            responseMsg.setContent(ex.getMessage());
        }

        try {
            messageManager.addMessage(message.getFromEmail(), message.getToEmail(), Common.ADD_FRIEND_REJECT_RESPONSE, null, Common.FRIENDSHIP);
            userManager.rejectAddFriend(message.getFromEmail(), message.getToEmail());
            responseMsg.setStatus(Common.SUCCESS);
            responseMsg.setContent("true");
        } catch (Exception ex) {
            responseMsg.setStatus("FAILURE");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        String output = gson.toJson(responseMsg);

        context.getLogger().log("output: " + output);

        return output;

    }
}
