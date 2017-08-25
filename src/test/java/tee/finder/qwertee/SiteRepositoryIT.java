package tee.finder.qwertee;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Scanner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SiteRepositoryIT {

    public static final String SITE_TEES_DEVELOPMENT = "site-tees-development";

    private AmazonS3 s3Client;

    private ObjectMapper objectMapper = new ObjectMapper();

    private SiteRepository siteRepository;

    Site site = SiteObjectMother.getExpectedSite();

    @Before
    public void initialize() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("", "");
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.US_EAST_1)
                .build();
        this.siteRepository = new SiteAmazonS3Repository(this.s3Client, SITE_TEES_DEVELOPMENT);
    }

    @After
    public void destroy() {
        this.s3Client.deleteObject(SITE_TEES_DEVELOPMENT, site.getName());
    }

    @Test
    public void test_repository_save_json_to_s3() throws ParseException, JsonProcessingException {
        this.siteRepository.save(site);

        String s3Content = getS3Content();
        assertThat(s3Content, is(getJsonValue()));
    }

    private String getJsonValue() throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(site);
    }

    private String getS3Content() {
        S3Object s3Object = s3Client.getObject(SITE_TEES_DEVELOPMENT, site.getName());
        return new Scanner(s3Object.getObjectContent()).useDelimiter("\\A").next();
    }


}
