package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.cloudcomputing.controller.UserManager;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.messages.UserLoginMessage;
import edu.wpi.cs.cloudcomputing.messages.UserRegisterMessage;

public class LoginHandler implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
    	ResponseMessage responseMsg = new ResponseMessage();  
    	Gson gson = new GsonBuilder().create();
    	UserLoginMessage loginMessage = null;
        try {
        	loginMessage = gson.fromJson(input.toString(), UserLoginMessage.class);
        	if (loginMessage == null || loginMessage.getUsername() == null || loginMessage.getPassword() == null) {
        		throw new Exception();
        	}
        }catch (Exception ex) {
        	responseMsg.setStatus("FAILURE");
        	responseMsg.setContent("Invalid input");
        	return gson.toJson(responseMsg);  
        }

        UserManager userManager = new UserManager();
        try {       	
        	
        	String responseContent = userManager.login(loginMessage);
        	if (responseContent.equals("success")) {
        		responseMsg.setStatus("SUCCESS");
            	responseMsg.setContent(responseContent);
        	}
        	else {
        		throw new Exception(responseContent);
        	}
        }
        catch(Exception ex) {
        	responseMsg.setStatus("FAILURE");
        	responseMsg.setContent(ex.getMessage());
        }
   
        String output = gson.toJson(responseMsg);  
        context.getLogger().log("output: " + output);
        return output;
    }

}
