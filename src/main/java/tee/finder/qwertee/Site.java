package tee.finder.qwertee;


public class Site {

    private String name;

    private String url;

    private Tees tees;

    public Site() {
        // TODO: Remove this constructor
    }

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

}
