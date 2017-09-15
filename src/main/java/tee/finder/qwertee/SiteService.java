package tee.finder.qwertee;

import tee.finder.qwertee.domain.Site;

import java.util.Optional;

public class SiteService {

    private SiteClient siteClient;
    private SiteRepository siteRepository;

    public SiteService(SiteClient siteClient, SiteRepository siteRepository) {
        this.siteClient = siteClient;
        this.siteRepository = siteRepository;
    }


    public void getAndStoreInformation() {
        this.siteClient.get().peek(s -> {
            Optional<Site> savedSite = this.siteRepository.getByName(s.getName());
            if (!savedSite.isPresent() || !savedSite.get().equals(s)) {
                this.siteRepository.save(s);
            }
        });

    }

}
