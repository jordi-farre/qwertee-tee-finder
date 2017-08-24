package tee.finder.qwertee;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

public class Application implements RequestHandler<ScheduledEventDto, String> {

    public String handleRequest(ScheduledEventDto input, Context context) {
        return "Application " + input;
    }

}
