package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import edu.wpi.cs.cloudcomputing.controller.MessageManager;
import edu.wpi.cs.cloudcomputing.messages.FriendMessage;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.model.PrivateMessage;
import edu.wpi.cs.cloudcomputing.utils.Common;

/**
 * Created by tonggezhu on 3/8/18.
 */
public class ReadPrivateMessage implements RequestHandler<Object, String> {
    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("ReadPrivateMessage Input: " + input);
        PrivateMessage message = new PrivateMessage();
        MessageManager messageManager = new MessageManager();
        Gson gson = new Gson();
        ResponseMessage responseMsg = new ResponseMessage();
        try {

            message = gson.fromJson(input.toString(), PrivateMessage.class);
            if (message == null || message.getPmId() == null){
                throw new Exception();}
        }
        catch(Exception ex) {
            responseMsg.setStatus(Common.BAD_REQUEST);
            responseMsg.setContent(ex.getMessage());
        }

        try {
            messageManager.readMessage(message.getPmId());
            responseMsg.setContent("true");
            responseMsg.setStatus("SUCCESS");


        } catch(Exception ex) {
            responseMsg.setStatus("FAILURE");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        String output = gson.toJson(responseMsg);

        context.getLogger().log("output: " + output);
        return output;
    }
}
