package tee.finder.qwertee;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Hello implements RequestHandler<String, String> {

    public String handleRequest(String input, Context context) {
        return "Hello " + input;
    }

}
