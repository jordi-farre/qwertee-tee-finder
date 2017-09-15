package tee.finder.qwertee;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Optional;
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
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain()).withRegion(Regions.US_EAST_1)
                .build();
        this.siteRepository = new SiteAmazonS3Repository(this.s3Client, SITE_TEES_DEVELOPMENT);
    }

    @After
    public void destroy() {
        this.s3Client.deleteObject(SITE_TEES_DEVELOPMENT, site.getName());
    }

    @Test
    public void test_repository_save_json_to_s3() throws ParseException, JsonProcessingException {
        this.siteRepository.save(this.site);

        String s3Content = getS3Content();
        assertThat(s3Content, is(getJsonValue(this.site)));
    }
    
    @Test
    public void test_get_json_from_s3() throws JsonProcessingException {
        this.siteRepository.save(this.site);

        Optional<Site> savedSite = this.siteRepository.getByName(this.site.getName());

        assertThat(this.getJsonValue(savedSite.get()), is(this.getJsonValue(this.site)));
    }

    private String getJsonValue(Site site) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(site);
    }

    private String getS3Content() {
        S3Object s3Object = s3Client.getObject(SITE_TEES_DEVELOPMENT, site.getName());
        return new Scanner(s3Object.getObjectContent()).useDelimiter("\\A").next();
    }


}
