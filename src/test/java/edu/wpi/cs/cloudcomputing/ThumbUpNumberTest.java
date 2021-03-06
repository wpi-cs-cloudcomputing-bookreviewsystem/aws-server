package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by tonggezhu on 3/4/18.
 */
public class ThumbUpNumberTest {
    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = "{\"num\": 4, \"reviewId\": \"161ee0351a2\"}";
        System.out.println(input);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }



    @Test
    public void handleRequest() throws Exception {

        ThumbUpNumber handler = new ThumbUpNumber();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);
        Assert.assertEquals("{\"status\":\"SUCCESS\",\"content\":\"true\"}", output);
        // TODO: validate output here if needed.
//        Assert.assertEquals("Hello from Lambda!", output);

    }

}