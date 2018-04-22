package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

/**
 * Created by tonggezhu on 3/8/18.
 */
public class DeletePrivateMessageTest {
    private  static LinkedHashMap<String, String> request;

    @BeforeClass
    public static void createInput() throws IOException {

        request = new LinkedHashMap();
        request.put("pmId","16207941196");

    }

    private Context createContext() {
        TestContext ctx = new TestContext();
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void deletePrivateMessage(){
        DeletePrivateMessage handler = new DeletePrivateMessage();
        Context ctx = createContext();
        System.out.println(request);

        String output = handler.handleRequest(request, ctx);
        System.out.println(output);
    }

}