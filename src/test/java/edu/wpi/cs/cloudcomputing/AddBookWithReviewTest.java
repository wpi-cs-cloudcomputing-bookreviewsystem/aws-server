package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

/**
 * Created by tonggezhu on 3/4/18.
 */
public class AddBookWithReviewTest {

    private  static LinkedHashMap<String, String> mao;

    @BeforeClass
    public static void createInput() throws IOException {

        mao = new LinkedHashMap();
        mao.put("ISBN","test5");
        mao.put("email","ha@ha.ha");
        mao.put("content","\"test addbookwithreview\"");
        mao.put("imageUrl", null);
        mao.put("genre", null);
        mao.put("author", "testAuthor");
        mao.put("description",null);
        mao.put("title","testTitle");
    }

    private Context createContext() {
        TestContext ctx = new TestContext();
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void AddBookWithReview() {
        AddBookWithReview handler = new AddBookWithReview();
        Context ctx = createContext();

        String output = handler.handleRequest(mao, ctx);
        System.out.println(output);
    }

}