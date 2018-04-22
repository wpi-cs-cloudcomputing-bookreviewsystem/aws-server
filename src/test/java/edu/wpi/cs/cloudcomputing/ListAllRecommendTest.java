package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by tonggezhu on 4/4/18.
 */
public class ListAllRecommendTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        input = "{\"email\": \"wli6@wpi.edu\"}";
    }

    private Context createContext() {
        TestContext ctx = new TestContext();
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testListAllBooks() {
       ListAllRecommendBook handler = new ListAllRecommendBook();
        // SearchBooks searchBooks = new SearchBooks();
        Context ctx = createContext();
        String output = handler.handleRequest(input, ctx);
        //String output=searchBooks.handleRequest(request,ctx);
        System.out.println(output);

    }
}
