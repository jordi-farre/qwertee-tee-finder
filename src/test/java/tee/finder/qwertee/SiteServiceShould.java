package tee.finder.qwertee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SiteServiceShould {

    private SiteService siteService;

    @Mock
    private SiteRepository siteRepository;

    @Mock
    private SiteClient siteClient;

    @Before
    public void initialize() {
        this.siteService = new SiteService(siteClient, siteRepository);
    }

    @Test
    public void get_site_information_and_store() {
        Site expectedSite = getExpectedSite();
        ReflectionEquals reflectionEquals = new ReflectionEquals(expectedSite, null);
        Mockito.when(this.siteClient.get()).thenReturn(expectedSite);

        this.siteService.getAndStoreInformation();

        ArgumentCaptor<Site> siteCaptor = ArgumentCaptor.forClass(Site.class);
        Mockito.verify(this.siteRepository).save(siteCaptor.capture());
        Site savedSite = siteCaptor.getValue();
        assertThat(reflectionEquals.matches(savedSite), is(true));
    }

    private Site getExpectedSite() {
        Tee teeOne = new Tee("Low Social Battery", "https://www.qwertee.com/", "https://cdn.qwertee.com/images/designs/zoom/121045.jpg", new Date());
        Tee teeTwo = new Tee("I hate unicorns!", "https://www.qwertee.com/", "https://cdn.qwertee.com/images/designs/zoom/120766.jpg", new Date());
        Tees tees = new Tees(teeOne, teeTwo);
        return new Site("Qwertee", "https://www.qwertee.com/", tees);
    }

}
