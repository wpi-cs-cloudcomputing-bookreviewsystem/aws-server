package edu.wpi.cs.cloudcomputing;

import com.amazonaws.PredefinedClientConfigurations;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import edu.wpi.cs.cloudcomputing.controller.MessageManager;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.model.PrivateMessage;

/**
 * Created by tonggezhu on 3/8/18.
 */
public class IgnorePrivateMessage implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("IgnorePrivateMessage Input: "+ input);
        MessageManager messageManager = new MessageManager();
        ResponseMessage responseMsg = new ResponseMessage();
        PrivateMessage message = new PrivateMessage();
        Gson gson = new Gson();

        try{
            message = gson.fromJson(input.toString(), PrivateMessage.class);
            if(message == null || message.getPmId() == null){
                throw new Exception();
            }
        }catch (Exception e){
            responseMsg.setStatus("FAILURE in translate input");
            responseMsg.setContent(e.getMessage());
            return gson.toJson(responseMsg);

        }

        try {
            Boolean res = messageManager.ignoreMessage(message.getPmId());
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
