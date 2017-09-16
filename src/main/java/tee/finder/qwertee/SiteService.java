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
        this.siteClient.get().peek(newSite -> {
            Optional<Site> savedSite = this.siteRepository.getByName(newSite.getName());
            if (isNewSiteOrHasChanges(newSite, savedSite)) {
                this.siteRepository.save(newSite);
            }
        });

    }

    private boolean isNewSiteOrHasChanges(Site s, Optional<Site> savedSite) {
        return !savedSite.isPresent() || !savedSite.get().equals(s);
    }

}
