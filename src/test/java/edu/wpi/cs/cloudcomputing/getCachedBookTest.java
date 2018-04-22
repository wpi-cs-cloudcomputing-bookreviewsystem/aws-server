package edu.wpi.cs.cloudcomputing;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class getCachedBookTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        input = null;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testgetCachedBook() {
        GetBookDetail handler = new GetBookDetail();
        Context ctx = createContext();
        String output = handler.handleRequest(input, ctx);
        System.out.println(output);
    }
}
