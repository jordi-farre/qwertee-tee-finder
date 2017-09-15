package tee.finder.qwertee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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
        when(this.siteRepository.getByName(expectedSite.getName())).thenReturn(Optional.empty());

        this.siteService.getAndStoreInformation();

        ArgumentCaptor<Site> siteCaptor = ArgumentCaptor.forClass(Site.class);
        verify(this.siteRepository).save(siteCaptor.capture());
        Site savedSite = siteCaptor.getValue();
        assertThat(getJsonValue(savedSite), is(getJsonValue(expectedSite)));
    }
    
    @Test
    public void get_site_information_from_rss_and_dont_store_if_still_exists_and_has_no_changes() {
        Site expectedSite = SiteObjectMother.getExpectedSite();
        when(this.siteRepository.getByName(expectedSite.getName())).thenReturn(Optional.of(expectedSite));

        this.siteService.getAndStoreInformation();

        verify(this.siteRepository).getByName(expectedSite.getName());
        verifyNoMoreInteractions(this.siteRepository);
    }

    private String getJsonValue(Site savedSite) throws JsonProcessingException {
        return objectMapper.writeValueAsString(savedSite);
    }

}
