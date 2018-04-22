package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import edu.wpi.cs.cloudcomputing.controller.BookManager;
import edu.wpi.cs.cloudcomputing.model.messages.ResponseMessage;

public class ListAllBooks implements RequestHandler<Object, String> {

    Gson gson;
    ResponseMessage responseMsg;
    BookManager bookManager;

    public ListAllBooks() {
        System.out.println("List all book initiating");
        responseMsg = new ResponseMessage();
        bookManager = new BookManager();
        gson = new Gson();
    }

    @Override
    public String handleRequest(Object input, Context context) {

        try {
            String allBooks = bookManager.getAllBooks(null);
            context.getLogger().log("allbooks: " + allBooks);
            responseMsg.setStatus("SUCCESS");
            responseMsg.setContent(allBooks);
        } catch (Exception ex) {
            responseMsg.setStatus("FAILURE");
            responseMsg.setContent(ex.getMessage());
        }

        String output = gson.toJson(responseMsg);
        context.getLogger().log("output: " + output);
        return output;
    }

}
