package tee.finder.qwertee;

import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SiteClientShould {

    private SiteClient siteClient = new SiteClient(this.getClass().getClassLoader().getResource("qwertee_rss.xml"));

    @Test
    public void should_get_site_information() {
        Site expectedSite = this.getExpectedSite();
        ReflectionEquals reflectionEquals = new ReflectionEquals(expectedSite, null);

        Site retrievedSite = this.siteClient.get();

        assertThat(reflectionEquals.matches(retrievedSite), is(true));
    }

    private Site getExpectedSite() {
        Tee teeOne = new Tee("Low Social Battery", "https://www.qwertee.com/", "https://cdn.qwertee.com/images/designs/zoom/121045.jpg", new Date());
        Tee teeTwo = new Tee("I hate unicorns!", "https://www.qwertee.com/", "https://cdn.qwertee.com/images/designs/zoom/120766.jpg", new Date());
        Tees tees = new Tees(teeOne, teeTwo);
        return new Site("Qwertee", "https://www.qwertee.com/", tees);
    }

}
