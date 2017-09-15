package tee.finder.qwertee.domain;


public class Site {

    private String name;

    private String url;

    private Tees tees;

    public Site(String name, String url, Tees tees) {
        this.name = name;
        this.url = url;
        this.tees = tees;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Tees getTees() {
        return tees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Site site = (Site) o;

        if (name != null ? !name.equals(site.name) : site.name != null) return false;
        if (url != null ? !url.equals(site.url) : site.url != null) return false;
        return tees != null ? tees.equals(site.tees) : site.tees == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (tees != null ? tees.hashCode() : 0);
        return result;
    }
}
