package tee.finder.qwertee;

public class ScheduledEventDto {

    private String account;

    private String region;

    private String time;

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

    @Override
    public String toString() {
        return "ScheduledEventDto{" +
                "account='" + account + '\'' +
                ", region='" + region + '\'' +
                ", time='" + time + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
