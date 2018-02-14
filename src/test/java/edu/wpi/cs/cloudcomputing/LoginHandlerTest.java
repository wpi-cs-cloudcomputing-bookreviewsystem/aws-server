package edu.wpi.cs.cloudcomputing;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.cloudcomputing.messages.UserLoginMessage;
import edu.wpi.cs.cloudcomputing.messages.UserRegisterMessage;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LoginHandlerTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
    	UserLoginMessage msg = new UserLoginMessage();
    	msg.setEmail("test@email.com");
    	msg.setPassword("Password1234!");
    	Gson gson = new GsonBuilder().create();
        input = gson.toJson(msg);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

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
