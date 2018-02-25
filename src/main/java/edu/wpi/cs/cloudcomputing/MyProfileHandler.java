package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.cloudcomputing.controller.UserManager;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.messages.StatusMessage;
import edu.wpi.cs.cloudcomputing.model.User;
import edu.wpi.cs.cloudcomputing.utils.Common;

public class MyProfileHandler implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
    	ResponseMessage responseMsg = new ResponseMessage();       
        UserManager userManager = new UserManager();
        try { 
        	
        	Gson gson = new Gson();
        	User inputUser = gson.fromJson(input.toString(), User.class);
        	
        	StatusMessage statusMessage = userManager.getUserProfile(inputUser.getEmail());
        	responseMsg.setStatus(statusMessage.getStatus());
        	responseMsg.setContent(statusMessage.getMessage());
        }
        catch(Exception ex) {
        	responseMsg.setStatus(Common.BAD_REQUEST);
        	responseMsg.setContent(ex.getMessage());
        }
     
        Gson gson = new GsonBuilder().create();
        String output = gson.toJson(responseMsg);  
        context.getLogger().log("output: " + output);
        return output;
    }

}
