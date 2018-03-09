package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.cloudcomputing.controller.BookManager;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.messages.SearchBookMessage;

import javax.naming.directory.SearchControls;

public class ListAllBooks implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
    	context.getLogger().log("Input: " + input);
        SearchBookMessage message = null;
        ResponseMessage responseMsg = new ResponseMessage();       
        BookManager bookManager = new BookManager();
        Gson gson = new Gson();
        try {
            message = gson.fromJson(input.toString(), SearchBookMessage.class);
            if(message == null){
                throw new Exception();}

            }catch (Exception ex) {
                responseMsg.setStatus("FAILURE");
                responseMsg.setContent(ex.getMessage());
                return gson.toJson(responseMsg);
            }

        try {
            String allbooks = bookManager.getAllBooks(message.getSearchWord());
            context.getLogger().log("allbooks: " + allbooks);
            responseMsg.setStatus("SUCCESS");
            responseMsg.setContent(allbooks);
        } catch(Exception ex) {
        	responseMsg.setStatus("FAILURE");
        	responseMsg.setContent(ex.getMessage());
        }

        String output = gson.toJson(responseMsg);  
        context.getLogger().log("output: " + output);
        return output;
    }

}
