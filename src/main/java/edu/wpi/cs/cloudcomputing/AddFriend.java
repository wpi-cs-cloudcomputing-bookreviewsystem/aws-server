package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import edu.wpi.cs.cloudcomputing.controller.MessageManager;
import edu.wpi.cs.cloudcomputing.controller.UserManager;
import edu.wpi.cs.cloudcomputing.messages.PMMessage;
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
        PMMessage message = null;
        Gson gson = new Gson();
        try {

            message = gson.fromJson(input.toString(), PMMessage.class);
            if (message == null || message.getFromEmail() == null
                    || message.getToEmail() == null || message.getTitle() == null) {
                throw new Exception();
            }
        } catch (Exception ex) {
            responseMsg.setStatus(Common.BAD_REQUEST);
            responseMsg.setContent(ex.getMessage());
        }


        try {
            if (message.getTitle().equals(Common.ADD_FRIEND_REQUEST)) {
                userManager.addFriend(message.getFromEmail(), message.getToEmail());

                messageManager.addMessage(message.getFromEmail(), message.getToEmail(), Common.ADD_FRIEND_REQUEST, Common.ADD_FREIEND_MESSAGE, Common.FRIENDSHIP);

            } else if (message.getTitle().equals(Common.ADD_FRIEND_ACCEPT_RESPONSE)){
                userManager.acceptAddFriend(message.getFromEmail(), message.getToEmail());
                context.getLogger().log("finish UserManager");

                messageManager.addMessage(message.getFromEmail(), message.getToEmail(), Common.ADD_FRIEND_ACCEPT_RESPONSE, Common.ADD_FREIEND_ACCEPT_MESSAGE, Common.FRIENDSHIP);
            }else{
                messageManager.addMessage(message.getFromEmail(), message.getToEmail(),Common.ADD_FRIEND_REJECT_RESPONSE,Common.ADD_FREIEND_REJECT_MESSAGE, Common.FRIENDSHIP);
                userManager.rejectAddFriend(message.getFromEmail(), message.getToEmail());

            }
            messageManager.readMessage(message.getPmId());
            responseMsg.setContent("true");
            responseMsg.setStatus("SUCCESS");
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
