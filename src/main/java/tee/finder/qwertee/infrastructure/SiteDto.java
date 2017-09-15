package tee.finder.qwertee.infrastructure;




public class SiteDto {

    private String name;

    private String url;

    private TeesDto tees;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TeesDto getTees() {
        return tees;
    }

    public void setTees(TeesDto tees) {
        this.tees = tees;
    }
}
