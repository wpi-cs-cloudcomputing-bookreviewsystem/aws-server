package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Created by tonggezhu on 3/25/18.
 */
public class ListAllPrivateMessagesTest {
    private static LinkedHashMap<String, String> request;

    @BeforeClass
    public static void createInput() throws IOException {
        request = new LinkedHashMap();
        request.put("email", "ha@ha.ha");
    }

    private Context createContext() {
        TestContext ctx = new TestContext();
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testListAllBooks() {
        ListAllPrivateMessages handler = new ListAllPrivateMessages();
        Context ctx = createContext();
        String output = handler.handleRequest(request, ctx);
        System.out.println(output);

    }
}
