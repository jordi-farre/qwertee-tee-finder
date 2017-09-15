package tee.finder.qwertee;

import io.vavr.control.Either;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;
import tee.finder.qwertee.domain.Site;

import java.text.ParseException;
import java.util.Optional;

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
    public void get_site_information_and_store() throws ParseException {
        Site expectedSite = SiteObjectMother.getExpectedSite();
        ReflectionEquals reflectionEquals = new ReflectionEquals(expectedSite, null);
        Mockito.when(this.siteClient.get()).thenReturn(Either.right(expectedSite));
        Mockito.when(this.siteRepository.getByName(expectedSite.getName())).thenReturn(Optional.empty());

        this.siteService.getAndStoreInformation();

        ArgumentCaptor<Site> siteCaptor = ArgumentCaptor.forClass(Site.class);
        Mockito.verify(this.siteRepository).save(siteCaptor.capture());
        Site savedSite = siteCaptor.getValue();
        assertThat(reflectionEquals.matches(savedSite), is(true));
    }

}
