package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import edu.wpi.cs.cloudcomputing.model.Recommendation;
import edu.wpi.cs.cloudcomputing.utils.Common;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

/**
 * Created by tonggezhu on 3/8/18.
 */
public class RejectAddFriendRequestTest {

    private  static LinkedHashMap<String, String> RejectAddRequest;

    @BeforeClass
    public static void createInput() throws IOException {


        RejectAddRequest = new LinkedHashMap<>();
        RejectAddRequest.put("fromEmail","TEST8@EMAIL.COM");
        RejectAddRequest.put("toEmail","ha.ha@ha");
        RejectAddRequest.put("message", Common.ADD_FRIEND_REJECT_RESPONSE);

    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void setRejectAddFriend(){
        RejectAddFriendRequest handler = new RejectAddFriendRequest();
        Context ctx = createContext();

        String output = handler.handleRequest(RejectAddRequest, ctx);
        System.out.println(output);
        // TODO: validate output here if needed.
//        Assert.assertEquals("Hello from Lambda!", output);
    }




}