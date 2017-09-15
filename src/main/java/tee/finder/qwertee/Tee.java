package tee.finder.qwertee;

import java.util.Date;

public class Tee {

    private String title;

    private String link;

    private String image;

    private Date date;

    public Tee() {
        // TODO: Remove this constructor
    }

    public Tee(String title, String link, String image, Date date) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

}
