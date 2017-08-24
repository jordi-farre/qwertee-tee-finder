package tee.finder.qwertee;

import java.util.List;

public class ScheduledEventDto {

    private String account;

    private String region;

    private String detail;

    private String detailType;

    private String source;

    private String time;

    private List<String> resources;

    private String id;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "ScheduledEventDto{" +
                "account='" + account + '\'' +
                ", region='" + region + '\'' +
                ", detail='" + detail + '\'' +
                ", detailType='" + detailType + '\'' +
                ", source='" + source + '\'' +
                ", time='" + time + '\'' +
                ", id='" + id + '\'' +
                ", resources=" + resources +
                ", id='" + id + '\'' +
                '}';
    }
}
