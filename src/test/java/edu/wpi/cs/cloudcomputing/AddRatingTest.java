package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import edu.wpi.cs.cloudcomputing.model.Rating;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by tonggezhu on 3/2/18.
 */
public class AddRatingTest {
    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        input = "{\"isbn\": \"test1\", \"email\": \"test2@test.com\", \"score\": 9}";
        System.out.println(input);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testListAllCachedBooks() {
        RatingBook handler = new RatingBook();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);
        System.out.println(output);
    }
}
