package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import edu.wpi.cs.cloudcomputing.controller.MessageManager;
import edu.wpi.cs.cloudcomputing.controller.UserManager;
import edu.wpi.cs.cloudcomputing.messages.FriendMessage;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.utils.Common;
import sun.text.normalizer.CharTrie;

/**
 * Created by tonggezhu on 3/8/18.
 */
public class RejectAddFriendRequest implements RequestHandler<Object, String> {
    @Override
    public String handleRequest(Object input, Context context) {

        context.getLogger().log("RejectAddFriendRequest Input: " + input);

        UserManager userManager = new UserManager();
        MessageManager messageManager = new MessageManager();
        FriendMessage message = new FriendMessage();
        ResponseMessage responseMsg = new ResponseMessage();
        Gson gson = new Gson();
        try {

            message = gson.fromJson(input.toString(), FriendMessage.class);
            if (message == null || message.getFromEmail() == null
                    || message.getToEmail() == null ){
                throw new Exception();}
        }
        catch(Exception ex) {
            responseMsg.setStatus(Common.BAD_REQUEST);
            responseMsg.setContent(ex.getMessage());
        }

        try{
            messageManager.addMessage(message.getFromEmail(), message.getToEmail(),Common.ADD_FRIEND_REJECT_RESPONSE,null, Common.FRIENDSHIP);
            userManager.rejectAddFriend(message.getFromEmail(), message.getToEmail());
            responseMsg.setStatus(Common.SUCCESS);
            responseMsg.setContent("true");
        }catch (Exception ex){
            responseMsg.setStatus("FAILURE");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        String output = gson.toJson(responseMsg);

        context.getLogger().log("output: " + output);

        return output;

    }
}
