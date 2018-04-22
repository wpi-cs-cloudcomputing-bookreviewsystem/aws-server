package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.wpi.cs.cloudcomputing.controller.MessageManager;
import edu.wpi.cs.cloudcomputing.controller.UserManager;
import edu.wpi.cs.cloudcomputing.model.messages.RecommendBookMessage;
import edu.wpi.cs.cloudcomputing.model.messages.ResponseMessage;
import edu.wpi.cs.cloudcomputing.model.Book;

import java.util.List;

import static edu.wpi.cs.cloudcomputing.utils.Common.RECOMMANDATION;
import static edu.wpi.cs.cloudcomputing.utils.Common.RECOMMANDATION_BOOK;

/**
 * Created by tonggezhu on 4/4/18.
 */
public class RecommendBook implements RequestHandler<Object, String> {
    Gson gson;
    ResponseMessage responseMsg;
    UserManager userManager;
    MessageManager messageManager;
    RecommendBookMessage message;
    Book book;
    String content;

    public RecommendBook() {
        System.out.println("recommend book handler initiated");
        gson = new GsonBuilder().create();
        responseMsg = new ResponseMessage();
        messageManager = new MessageManager();
        userManager = new UserManager();
    }

    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);
        try {
            message = gson.fromJson(input.toString(), RecommendBookMessage.class);
            if (message == null || message.getIsbn()== null
                    || message.getEmail() == null) {
                throw new Exception();}
        }catch (Exception ex) {
            responseMsg.setStatus("FAILURE in translate input");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        try {
            List<String> emailList = userManager.getAllFriends(message.getEmail());
            for(String email: emailList){
                book = new Book();
                book.setTitle(message.getTitle());
                book.setImageUrl(message.getImageUrl());
                book.setDescription(message.getDescription());
                book.setAuthor(message.getAuthor());
                book.setISBN(message.getIsbn());
                content =gson.toJson(book, Book.class);
                System.out.println(email+message.getEmail()+content);
                messageManager.addMessage(message.getEmail(), email, RECOMMANDATION_BOOK, content ,RECOMMANDATION);
            }
            responseMsg.setStatus("SUCCESS");
            responseMsg.setContent("True");
        }
        catch(Exception ex) {
            responseMsg.setStatus("FAILURE");
            responseMsg.setContent(ex.getMessage());
            return gson.toJson(responseMsg);
        }

        context.getLogger().log("output: " + message.toString());
        return gson.toJson(responseMsg);
    }
}
