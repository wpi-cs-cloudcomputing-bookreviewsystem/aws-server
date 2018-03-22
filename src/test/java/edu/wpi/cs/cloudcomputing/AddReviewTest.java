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
    private  static LinkedHashMap<String, String> mao;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = "{\"isbn\"=\"0156031442\", \"email\"=\"ha@ha.ha\", \"content\"=\"clondcomputing\"}";
//        mao = new LinkedHashMap();
//        mao.put("isbn","test");
//        mao.put("email","test@email.com");
//        mao.put("content","testtes tetes");
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testListAllCachedBooks() {
        AddReview handler = new AddReview();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);
        System.out.println(output);
        // TODO: validate output here if needed.
//        Assert.assertEquals("Hello from Lambda!", output);
    }
}
