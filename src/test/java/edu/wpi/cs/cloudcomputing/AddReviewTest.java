package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Created by tonggezhu on 3/2/18.
 */
public class AddReviewTest {
    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        input = "{\"isbn\"=\"0156031442\", \"email\"=\"ha@ha.ha\", \"content\"=\"clondcomputing\"}";
    }

    private Context createContext() {
        TestContext ctx = new TestContext();
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testListAllCachedBooks() {
        AddReview handler = new AddReview();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);
        System.out.println(output);
    }
}
