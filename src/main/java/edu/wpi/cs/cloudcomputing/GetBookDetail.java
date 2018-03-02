package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.cloudcomputing.controller.BookManager;
import edu.wpi.cs.cloudcomputing.messages.GetBookDetailRequest;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;

public class GetBookDetail implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);
        Gson gson = new GsonBuilder().create();
        GetBookDetailRequest getBookDetailRequest = null;
        ResponseMessage responseMsg = new ResponseMessage();
        BookManager bookManager = new BookManager();
        try {
            getBookDetailRequest = gson.fromJson(input.toString(), GetBookDetailRequest.class);
            if (getBookDetailRequest == null || getBookDetailRequest.getIsbn() == null) {
                throw new Exception();}
            }catch (Exception ex) {
                responseMsg.setStatus("FAILURE");
                responseMsg.setContent(ex.getMessage());
                return gson.toJson(responseMsg);
            }

        try {       	
        	String book = bookManager.getBook(getBookDetailRequest.getIsbn());
        	responseMsg.setStatus("SUCCESS");
        	responseMsg.setContent(book);
        }
        catch(Exception ex) {
        	responseMsg.setStatus("FAILURE");
        	responseMsg.setContent(ex.getMessage());
        }
     
        Gson gson2 = new GsonBuilder().create();
        String output = gson2.toJson(responseMsg);
        context.getLogger().log("output: " + output);
        return output;
    }

}
