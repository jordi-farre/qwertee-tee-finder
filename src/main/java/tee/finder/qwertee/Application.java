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

import java.util.Map;
import java.util.Scanner;

public class Application implements RequestHandler<ScheduledEventDto, String> {

    public String handleRequest(ScheduledEventDto input, Context context) {
        return "Application " + input;
    }

    public static void main(String[] args) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIXIAXLVEIY3MZBZQ", "p1ETpXepOtexQ/9NzzUV9Aht58v6s11Ibg5ODVHm");
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.US_EAST_1)
                .build();
        s3Client.putObject("site-tees", "development/test", "newContent");
        S3Object test = s3Client.getObject("site-tees", "test");
        System.out.println(new Scanner(test.getObjectContent(), "UTF-8").useDelimiter("\\A").next());
    }

}
