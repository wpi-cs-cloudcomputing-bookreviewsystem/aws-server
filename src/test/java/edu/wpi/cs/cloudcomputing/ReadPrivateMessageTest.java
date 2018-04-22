package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import edu.wpi.cs.cloudcomputing.utils.Common;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

/**
 * Created by tonggezhu on 3/8/18.
 */
public class ReadPrivateMessageTest {
    private static LinkedHashMap<String, String> request;

    @BeforeClass
    public static void createInput() throws IOException {

        request = new LinkedHashMap();
        request.put("pmId", "1621167c4cb");
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void readPrivateMessage() {
        ReadPrivateMessage handler = new ReadPrivateMessage();
        Context ctx = createContext();
        System.out.println(request);

        String output = handler.handleRequest(request, ctx);
        System.out.println(output);

    }


}