package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.cloudcomputing.controller.BookManager;
import edu.wpi.cs.cloudcomputing.controller.MessageManager;
import edu.wpi.cs.cloudcomputing.controller.RatingManager;
import edu.wpi.cs.cloudcomputing.messages.PMMessage;
import edu.wpi.cs.cloudcomputing.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.messages.SearchBookMessage;
import edu.wpi.cs.cloudcomputing.model.Rating;

import javax.naming.directory.SearchControls;

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
