package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import edu.wpi.cs.cloudcomputing.controller.UserManager;
import edu.wpi.cs.cloudcomputing.messages.GetProfileMessage;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.utils.Common;

public class GetUserProfile implements RequestHandler<Object, String> {

    ResponseMessage responseMsg;
    UserManager userManager;
    GetProfileMessage message;
    Gson gson;

    public GetUserProfile() {
        System.out.println("get user profile initiated");

        responseMsg = new ResponseMessage();
        userManager = new UserManager();
        gson = new Gson();
    }

    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);

        try {
            message = gson.fromJson(input.toString(), GetProfileMessage.class);
            if (message == null || message.getFromEmail() == null
                    || message.getToEmail() == null) {
                throw new Exception();
            }
        } catch (Exception ex) {
            responseMsg.setStatus(Common.BAD_REQUEST);
            responseMsg.setContent(ex.getMessage());
        }

        try {

            String res = userManager.getUserProfile(message.getFromEmail(), message.getToEmail());
            responseMsg.setContent(res);
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
