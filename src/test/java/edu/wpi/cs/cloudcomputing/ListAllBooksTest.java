package edu.wpi.cs.cloudcomputing;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListAllBooksTest {

    private  static LinkedHashMap<String, String> request;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
       // request = new LinkedHashMap();
       // request.put("keyword","\"animal David\"");
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testListAllBooks() {
        ListAllBooks handler = new ListAllBooks();
       // SearchBooks searchBooks = new SearchBooks();
        Context ctx = createContext();

      String output = handler.handleRequest(request, ctx);
        //String output=searchBooks.handleRequest(request,ctx);
        System.out.println(output);
        // TODO: validate output here if needed.
//        Assert.assertEquals("Hello from Lambda!", output);
    }
}
