package tee.finder.qwertee;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.net.MalformedURLException;
import java.net.URL;

public class Application implements RequestHandler<ScheduledEventDto, String> {

    public String handleRequest(ScheduledEventDto input, Context context) {
        try {
            System.out.println("Application startup");
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
