package tee.finder.qwertee;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.log4j.Logger.getLogger;

public class Application implements RequestHandler<ScheduledEventDto, String> {

    private Logger LOG = getLogger(Application.class);

    public String handleRequest(ScheduledEventDto input, Context context) {
        try {
            LOG.info("Application startup");
            SiteClient siteClient = new SiteClient(new URL("https://www.qwertee.com/rss"));
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();
            SiteRepository siteRepository = new SiteAmazonS3Repository(s3Client, "site-tees-production");
            SiteService siteService = new SiteService(siteClient, siteRepository);
            siteService.getAndStoreInformation();
        } catch (MalformedURLException e) {
            return "Error: " + e.getMessage();
        }
        return "OK";
    }

}
