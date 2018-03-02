package edu.wpi.cs.cloudcomputing;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.cloudcomputing.messages.UserRegisterMessage;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class RegisterHandlerTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
//    	UserRegisterMessage msg = new UserRegisterMessage();
//    	msg.setEmail("TestUser022417@tester.com");
//    	msg.setUsername("TestUser02417");
//    	msg.setPassword("Password1234!");
//    	Gson gson = new GsonBuilder().create();
//        input = gson.toJson(msg);
    	input = "{\"username\": \"TestUse\",\"email\": \"TestUser@tester.com\",\"password\": \"Password1234!\"}";
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
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
        // TODO: validate output here if needed.
//        Assert.assertEquals("Hello from Lambda!", output);
    }
}
