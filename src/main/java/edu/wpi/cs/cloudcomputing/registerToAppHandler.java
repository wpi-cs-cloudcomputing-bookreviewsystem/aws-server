package edu.wpi.cs.cloudcomputing;

import static edu.wpi.cs.cloudcomputing.utils.Common.USER_CREATED;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.cloudcomputing.controller.UserManager;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.messages.UserRegisterMessage;

public class registerToAppHandler implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
    	ResponseMessage responseMsg = new ResponseMessage();  
    	Gson gson = new GsonBuilder().create();
    	UserRegisterMessage registerMessage = null;
        try {
        	registerMessage = gson.fromJson(input.toString(), UserRegisterMessage.class);
        	if (registerMessage == null || registerMessage.getUsername() == null || registerMessage.getPassword() == null || registerMessage.getEmail() == null) {
        		throw new Exception("Invalid input");
        	}
        }catch (Exception ex) {
        	responseMsg.setStatus("FAILURE");
        	responseMsg.setContent(ex.getMessage());
        	return gson.toJson(responseMsg);  
        }

        UserManager userManager = new UserManager();
        try {       	
        	
        	String responseContent = userManager.registerUserToApp(registerMessage);
        	if (responseContent.equals(USER_CREATED)) {
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
