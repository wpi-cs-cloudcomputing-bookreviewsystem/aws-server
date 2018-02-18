package edu.wpi.cs.cloudcomputing;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class MyProfileHandler implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
    	context.getLogger().log("Input: " + input);

        return "";
    }

}
