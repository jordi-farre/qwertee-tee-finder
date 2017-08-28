package tee.finder.qwertee;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiteAmazonS3Repository implements SiteRepository {


    private static final Logger LOG = LoggerFactory.getLogger(SiteAmazonS3Repository.class);

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
            LOG.info("Storing object " + site.getName() + " on bucket " + this.bucket);
            PutObjectResult result = this.s3Client.putObject(this.bucket, site.getName(), objectMapper.writeValueAsString(site));
            LOG.info("Result: " +  result.getContentMd5());
        } catch (JsonProcessingException e) {
            LOG.error("Error saving site", e);
        }
    }

}
