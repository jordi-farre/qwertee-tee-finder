package tee.finder.qwertee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GetAndStoreTeesFeature {


    @Mock
    private SiteRepository siteRepository;

    private SiteClient siteClient = new SiteClient(this.getClass().getClassLoader().getResource("/qwertee_rss.xml"));

    private SiteService siteService;

    @Before
    public void initialize() {
        this.siteService = new SiteService(this.siteClient, this.siteRepository);
    }

    @Test
    public void get_site_information_from_rss_and_store() {
        Site expectedSite = null;
        ReflectionEquals reflectionEquals = new ReflectionEquals(expectedSite, null);

        this.siteService.getAndStoreInformation();

        ArgumentCaptor<Site> siteCaptor = ArgumentCaptor.forClass(Site.class);
        Mockito.verify(this.siteRepository).save(siteCaptor.capture());
        Site savedSite = siteCaptor.getValue();
        assertThat(reflectionEquals.matches(savedSite), is(true));
    }
    
}
