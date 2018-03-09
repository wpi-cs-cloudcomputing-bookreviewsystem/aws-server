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
    private  static LinkedHashMap<String, String> request;

    @BeforeClass
    public static void createInput() throws IOException {

        request = new LinkedHashMap();
        request.put("pmId","162077b7908");

    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void readPrivateMessage(){
        ReadPrivateMessage handler = new ReadPrivateMessage();
        Context ctx = createContext();
        System.out.println(request);

        String output = handler.handleRequest(request, ctx);
        System.out.println(output);
        // TODO: validate output here if needed.
//        Assert.assertEquals("Hello from Lambda!", output);
    }



}