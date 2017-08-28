package tee.finder.qwertee;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class Application implements RequestHandler<ScheduledEventDto, String> {

    private Logger LOG = LoggerFactory.getLogger(Application.class);

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
