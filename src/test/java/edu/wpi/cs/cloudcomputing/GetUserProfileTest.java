package edu.wpi.cs.cloudcomputing;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import edu.wpi.cs.cloudcomputing.model.User;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetUserProfileTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        String fromEmail =  "USER1@EMAIL.COM";
        String toEmail = "USER2@EMAIL.COM";

        JSONObject object=new JSONObject();
        object.put("fromEmail",fromEmail);
        object.put("toEmail",toEmail);

        input = object.toString();
        System.out.println(input);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testMyProfileHandler() {
        GetUserProfile handler = new GetUserProfile();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);
        System.out.println(output);
        // TODO: validate output here if needed.
//        Assert.assertEquals("Hello from Lambda!", output);
    }
}
