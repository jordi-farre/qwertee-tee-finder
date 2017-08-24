package tee.finder.qwertee;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class SiteClient {

    private URL url;

    public SiteClient(URL url) {
        this.url = url;
    }

    public Site get() {
        SyndFeedInput syndFeedInput = new SyndFeedInput();
        try {
            SyndFeed feed = syndFeedInput.build(new XmlReader(this.url));
            List<Tee> tees = feed.getEntries().stream()
                    .map(entry -> new Tee(entry.getTitle(), entry.getLink(), entry.getDescription().getValue(), entry.getPublishedDate()))
                    .collect(Collectors.toList());
            return new Site(feed.getTitle(), feed.getLink(), new Tees(tees.toArray(new Tee[]{})));
        } catch (FeedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
