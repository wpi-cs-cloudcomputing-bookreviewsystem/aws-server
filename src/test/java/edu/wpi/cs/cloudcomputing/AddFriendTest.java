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
public class AddFriendTest {

    private  static LinkedHashMap<String, String> AddFriendRequest;
    private  static LinkedHashMap<String, String> AcceptAddRequest;

    @BeforeClass
    public static void createInput() throws IOException {

        AddFriendRequest = new LinkedHashMap();
        AddFriendRequest.put("fromEmail","ha.ha@ha");
        AddFriendRequest.put("toEmail","TEST2@EMAIL.COM");
        AddFriendRequest.put("title", Common.ADD_FRIEND_REQUEST);

        AcceptAddRequest = new LinkedHashMap<>();
        AcceptAddRequest.put("fromEmail","TEST2@EMAIL.COM");
        AcceptAddRequest.put("toEmail","ha.ha@ha");
        AcceptAddRequest.put("title", Common.ADD_FRIEND_ACCEPT_RESPONSE);

    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void AddFriend(){
        AddFriend handler = new AddFriend();
        Context ctx = createContext();

        String output = handler.handleRequest(AcceptAddRequest, ctx);
        System.out.println(output);

    }

}