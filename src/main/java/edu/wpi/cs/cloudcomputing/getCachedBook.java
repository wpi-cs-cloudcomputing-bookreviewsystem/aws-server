package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.cloudcomputing.controller.BookManager;
import edu.wpi.cs.cloudcomputing.utils.ResponseMessage;

public class getCachedBook implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
    	
        ResponseMessage responseMsg = new ResponseMessage();
        if (input == null) {
        	responseMsg.setStatus("FAILURE");
        	responseMsg.setContent("Invalid Input request");
    	}
        BookManager bookManager = new BookManager();
        try {
        	String ISBN = String.valueOf(input);
        	String book = bookManager.getCachedBooks(ISBN);
        	context.getLogger().log("book found: " + book);
        	responseMsg.setStatus("SUCCESS");
        	responseMsg.setContent(book);
        }
        catch(Exception ex) {
        	responseMsg.setStatus("FAILURE");
        	responseMsg.setContent(ex.getMessage());
        }
     
        Gson gson = new GsonBuilder().create();
        String output = gson.toJson(responseMsg);  
        context.getLogger().log("output: " + output);
        return output;
    }

}
