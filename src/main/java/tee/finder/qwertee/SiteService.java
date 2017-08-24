package tee.finder.qwertee;

public class SiteService {

    private SiteClient siteClient;
    private SiteRepository siteRepository;

    public SiteService(SiteClient siteClient, SiteRepository siteRepository) {
        this.siteClient = siteClient;
        this.siteRepository = siteRepository;
    }


    public void getAndStoreInformation() {
        Site site = this.siteClient.get();
        this.siteRepository.save(site);
    }

}
