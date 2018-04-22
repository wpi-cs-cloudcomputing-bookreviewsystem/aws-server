package edu.wpi.cs.cloudcomputing;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.cloudcomputing.model.messages.UserLoginMessage;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LoginHandlerTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        UserLoginMessage msg = new UserLoginMessage();
        msg.setEmail("TestUser022412@tester.com");
        msg.setPassword("Password1234!");
        Gson gson = new GsonBuilder().create();
        input = gson.toJson(msg);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();
        ctx.setFunctionName("LoginHandler");

        return ctx;
    }

    @Test
    public void testLoginHandler() {
        LoginHandler handler = new LoginHandler();
        Context ctx = createContext();
        String output = handler.handleRequest(input, ctx);
        System.out.println(output);
    }
}
