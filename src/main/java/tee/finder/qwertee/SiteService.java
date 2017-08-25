package tee.finder.qwertee;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SiteService {

    private SiteClient siteClient;
    private SiteRepository siteRepository;

    public SiteService(SiteClient siteClient, SiteRepository siteRepository) {
        this.siteClient = siteClient;
        this.siteRepository = siteRepository;
    }


    public void getAndStoreInformation() {
        this.siteClient.get().peek(s -> this.siteRepository.save(s));

    }

}
