package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.cloudcomputing.controller.UserManager;
import edu.wpi.cs.cloudcomputing.messages.AWSLoginResponseMessage;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.messages.UserLoginMessage;

import static edu.wpi.cs.cloudcomputing.utils.Common.LOGGED_IN;

public class LoginHandler implements RequestHandler<Object, String> {
	ResponseMessage responseMsg;
	Gson gson;
	UserLoginMessage loginMessage;

	public LoginHandler() {
		responseMsg = new ResponseMessage();
		gson = new GsonBuilder().create();
	}

	@Override
    public String handleRequest(Object input, Context context) {

        try {
        	loginMessage = gson.fromJson(input.toString(), UserLoginMessage.class);
        	if (loginMessage == null || loginMessage.getEmail() == null || loginMessage.getPassword() == null) {
        		throw new Exception();
        	}
        }catch (Exception ex) {
        	responseMsg.setStatus("FAILURE");
        	responseMsg.setContent("Invalid input");
        	return gson.toJson(responseMsg);  
        }

        UserManager userManager = new UserManager();
        try {       	
        	
        	AWSLoginResponseMessage awsResponseMessage = userManager.login(loginMessage);
        	if (awsResponseMessage.getStatus().equals(LOGGED_IN)) {
        		responseMsg.setStatus("SUCCESS");        		
        		String content = gson.toJson(awsResponseMessage);
        		responseMsg.setContent(content);
        	}
        	else {
        		throw new Exception(awsResponseMessage.getStatus());
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
