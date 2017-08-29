package tee.finder.qwertee;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import io.vavr.control.Either;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class SiteClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteClient.class);

    private URL url;

    public SiteClient(URL url) {
        this.url = url;
    }

    public Either<String, Site> get() {
        SyndFeedInput syndFeedInput = new SyndFeedInput();
        try (XmlReader xmlReader = new XmlReader(this.url)) {
            SyndFeed feed = syndFeedInput.build(xmlReader);
            List<Tee> tees = feed.getEntries().stream()
                    .map(this::createTee)
                    .collect(Collectors.toList());
            return Either.right(new Site("Qwertee", feed.getLink(), new Tees(tees.toArray(new Tee[]{}))));
        } catch (FeedException e) {
            LOGGER.error("Error reading feed",  e);
            return Either.left(e.getMessage());
        } catch (IOException e) {
            LOGGER.error("Error reading feed", e);
            return Either.left(e.getMessage());
        }
    }

    private Tee createTee(SyndEntry entry) {
        Document description = Jsoup.parse(entry.getDescription().getValue());
        return new Tee(entry.getTitle(), entry.getLink(), description.select("img").first().attr("src"), entry.getPublishedDate());
    }

}
