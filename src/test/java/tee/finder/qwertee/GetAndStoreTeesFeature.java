package tee.finder.qwertee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.ParseException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GetAndStoreTeesFeature {


    @Mock
    private SiteRepository siteRepository;

    private SiteClient siteClient = new SiteClient(this.getClass().getClassLoader().getResource("qwertee_rss.xml"));

    private SiteService siteService;

    private ObjectMapper objectMapper;

    @Before
    public void initialize() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.siteService = new SiteService(this.siteClient, this.siteRepository);
    }

    @Test
    public void get_site_information_from_rss_and_store() throws ParseException, JsonProcessingException {
        Site expectedSite = SiteObjectMother.getExpectedSite();

        this.siteService.getAndStoreInformation();

        ArgumentCaptor<Site> siteCaptor = ArgumentCaptor.forClass(Site.class);
        Mockito.verify(this.siteRepository).save(siteCaptor.capture());
        Site savedSite = siteCaptor.getValue();
        assertThat(getJsonValue(savedSite), is(getJsonValue(expectedSite)));
    }

    private String getJsonValue(Site savedSite) throws JsonProcessingException {
        return objectMapper.writeValueAsString(savedSite);
    }

}
