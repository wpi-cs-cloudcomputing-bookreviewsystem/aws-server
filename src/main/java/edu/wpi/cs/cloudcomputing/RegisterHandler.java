package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.cloudcomputing.controller.BookManager;
import edu.wpi.cs.cloudcomputing.controller.UserManager;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.messages.UserRegisterMessage;

import static edu.wpi.cs.cloudcomputing.utils.Common.LOGGED_IN;
import static edu.wpi.cs.cloudcomputing.utils.Common.USER_CREATED;

public class RegisterHandler implements RequestHandler<Object, String> {

	ResponseMessage responseMsg;
	Gson gson;
	UserRegisterMessage registerMessage;

	public RegisterHandler() {
		responseMsg = new ResponseMessage();
		gson = new GsonBuilder().create();
	}

	@Override
    public String handleRequest(Object input, Context context) {

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
        	
        	String responseContent = userManager.register(registerMessage);
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
