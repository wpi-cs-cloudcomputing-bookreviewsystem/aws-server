package edu.wpi.cs.cloudcomputing;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import edu.wpi.cs.cloudcomputing.model.User;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class MyProfileHandlerTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
    	User userInput = new User();
    	userInput.setEmail("TestUser02244@tester.com");
    	userInput.setUsername("TestUser0224");
    	Gson gson = new Gson();
    	
        input = gson.toJson(userInput);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testMyProfileHandler() {
        MyProfileHandler handler = new MyProfileHandler();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);
        System.out.println(output);
        // TODO: validate output here if needed.
//        Assert.assertEquals("Hello from Lambda!", output);
    }
}
