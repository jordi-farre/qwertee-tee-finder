package tee.finder.qwertee;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class SiteAmazonS3Repository implements SiteRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteAmazonS3Repository.class);

    private AmazonS3 s3Client;

    private String bucket;

    private ObjectMapper objectMapper = new ObjectMapper();

    public SiteAmazonS3Repository(AmazonS3 s3Client, String bucket) {
        this.s3Client = s3Client;
        this.bucket = bucket;
    }

    @Override
    public void save(Site site) {
        try {
            this.s3Client.putObject(this.bucket, site.getName(), this.objectMapper.writeValueAsString(site));
        } catch (JsonProcessingException e) {
            LOGGER.error("Error saving site", e);
        }
    }

    @Override
    public Optional<Site> getByName(String name) {
        Optional<Site> result = Optional.empty();
        try {
            S3Object s3Object = this.s3Client.getObject(this.bucket, name);
            result = Optional.of(this.objectMapper.readValue(s3Object.getObjectContent(), Site.class));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error getting site", e);
        }
        return result;
    }

}
