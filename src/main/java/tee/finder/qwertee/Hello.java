package tee.finder.qwertee;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

public class Hello implements RequestHandler<Map<String, String>, String> {

    public String handleRequest(Map<String, String> input, Context context) {
        return "Hello " + input;
    }

}
