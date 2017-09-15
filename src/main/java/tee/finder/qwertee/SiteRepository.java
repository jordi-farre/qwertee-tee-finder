package tee.finder.qwertee;

import java.util.Optional;

public interface SiteRepository {

    void save(Site site);

    Optional<Site> getByName(String name);

}
