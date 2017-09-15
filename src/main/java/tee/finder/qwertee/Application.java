package tee.finder.qwertee;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class Application implements RequestHandler<String, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public String handleRequest(String input, Context context) {
        try {
            LOGGER.info("Application startup");
            SiteClient siteClient = new SiteClient(new URL(System.getenv("QWERTEE_URL")));
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();
            SiteRepository siteRepository = new SiteAmazonS3Repository(s3Client, System.getenv("S3_BUCKET"));
            SiteService siteService = new SiteService(siteClient, siteRepository);
            siteService.getAndStoreInformation();
        } catch (MalformedURLException e) {
            LOGGER.error("Error", e);
        }
        return "OK";
    }

}
