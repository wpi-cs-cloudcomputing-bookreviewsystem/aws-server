package edu.wpi.cs.cloudcomputing;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class RegisterHandlerTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        input = "{\"username\": \"TestUse\",\"email\": \"TestUser@tester.com\",\"password\": \"Password1234!\"}";
    }

    private Context createContext() {
        TestContext ctx = new TestContext();
        ctx.setFunctionName("RegisterHandler");

        return ctx;
    }

    @Test
    public void testRegisterHandler() {
        RegisterHandler handler = new RegisterHandler();
        Context ctx = createContext();
        System.out.println(input);
        String output = handler.handleRequest(input, ctx);
        System.out.println(output);
    }
}
