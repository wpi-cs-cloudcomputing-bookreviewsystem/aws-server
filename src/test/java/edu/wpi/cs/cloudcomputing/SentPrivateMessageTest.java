package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import edu.wpi.cs.cloudcomputing.utils.Common;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Created by tonggezhu on 3/8/18.
 */
public class SentPrivateMessageTest {

    private static LinkedHashMap<String, String> privateMessage;

    @BeforeClass
    public static void createInput() throws IOException {

        privateMessage = new LinkedHashMap<>();
        privateMessage.put("fromEmail", "TEST8@EMAIL.COM");
        privateMessage.put("toEmail", "ha.ha@ha");
        privateMessage.put("title", "\"Hello lalala\"");
        privateMessage.put("content", "\"test test test\"");
        privateMessage.put("type", Common.MESSAGE);

    }

    private Context createContext() {
        TestContext ctx = new TestContext();
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void sendPrivateMessage() {
        SentPrivateMessage handler = new SentPrivateMessage();
        Context ctx = createContext();

        String output = handler.handleRequest(privateMessage, ctx);
        System.out.println(output);

    }
}