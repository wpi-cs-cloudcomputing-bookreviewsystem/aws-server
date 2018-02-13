package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.cloudcomputing.controller.BookManager;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;

public class ListAllBooks implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
    	context.getLogger().log("Input: " + input);

        ResponseMessage responseMsg = new ResponseMessage();       
        BookManager bookManager = new BookManager();
        try {       	
        	String allbooks = bookManager.getAllBooks();
        	context.getLogger().log("allbooks: " + allbooks);
        	responseMsg.setStatus("SUCCESS");
        	responseMsg.setContent(allbooks);
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
