package tee.finder.qwertee.domain;

import java.util.Date;

public class Tee {

    private String title;

    private String link;

    private String image;

    private Date date;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tee tee = (Tee) o;

        if (title != null ? !title.equals(tee.title) : tee.title != null) return false;
        if (link != null ? !link.equals(tee.link) : tee.link != null) return false;
        if (image != null ? !image.equals(tee.image) : tee.image != null) return false;
        return date != null ? date.equals(tee.date) : tee.date == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
