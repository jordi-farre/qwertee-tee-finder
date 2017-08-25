package tee.finder.qwertee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SiteClientShould {

    private SiteClient siteClient = new SiteClient(this.getClass().getClassLoader().getResource("qwertee_rss.xml"));

    private ObjectMapper objectMapper;

    @Before
    public void initialize() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    public void should_get_site_information() throws ParseException, JsonProcessingException {
        Site expectedSite = SiteObjectMother.getExpectedSite();

        Site retrievedSite = this.siteClient.get();

        assertThat(getJsonValue(retrievedSite), is(getJsonValue(expectedSite)));
    }

    private String getJsonValue(Site retrievedSite) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(retrievedSite);
    }

}
