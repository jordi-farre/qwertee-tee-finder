package tee.finder.qwertee;

import tee.finder.qwertee.domain.Site;

import java.util.Optional;

public interface SiteRepository {

    void save(Site site);

    Optional<Site> getByName(String name);

}
