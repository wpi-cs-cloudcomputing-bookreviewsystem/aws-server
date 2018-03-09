package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import edu.wpi.cs.cloudcomputing.controller.UserManager;
import edu.wpi.cs.cloudcomputing.messages.GetProfileMessage;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.utils.Common;

public class GetUserProfile implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);
    	ResponseMessage responseMsg = new ResponseMessage();
        UserManager userManager = new UserManager();
        GetProfileMessage message = null;
        Gson gson = new Gson();
        try {

        	message = gson.fromJson(input.toString(), GetProfileMessage.class);

            if (message == null || message.getFromEmail() == null
                    || message.getToEmail() == null){
                throw new Exception();}

        }
        catch(Exception ex) {
        	responseMsg.setStatus(Common.BAD_REQUEST);
        	responseMsg.setContent(ex.getMessage());
        }


        try {

            String res = userManager.getUserProfile(message.getFromEmail(), message.getToEmail());
            responseMsg.setContent(res);
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
