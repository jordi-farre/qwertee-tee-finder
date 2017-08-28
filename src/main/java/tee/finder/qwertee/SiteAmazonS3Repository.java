package tee.finder.qwertee;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SiteAmazonS3Repository implements SiteRepository {

    private AmazonS3 s3Client;

    private String bucket;

    public SiteAmazonS3Repository(AmazonS3 s3Client, String bucket) {
        this.s3Client = s3Client;
        this.bucket = bucket;
    }

    @Override
    public void save(Site site) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println("Storing object " + site.getName() + " on bucket " + this.bucket);
            PutObjectResult result = this.s3Client.putObject(this.bucket, site.getName(), objectMapper.writeValueAsString(site));
            System.out.println("Result: " +  result.getContentMd5());
        } catch (JsonProcessingException e) {
            System.err.println("Error saving site: " + e.getMessage());
        }
    }

}
