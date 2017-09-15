package tee.finder.qwertee;

import tee.finder.qwertee.domain.Site;
import tee.finder.qwertee.domain.Tee;
import tee.finder.qwertee.domain.Tees;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SiteObjectMother {

    public static Site getExpectedSite() {
        try {
            Tee teeOne = new Tee("Low Social Battery", "https://www.qwertee.com/", "https://cdn.qwertee.com/images/designs/zoom/121045.jpg", parseDate("Wed, 23 Aug 2017 21:00:00 +0100"));
            Tee teeTwo = new Tee("I hate unicorns!", "https://www.qwertee.com/", "https://cdn.qwertee.com/images/designs/zoom/120766.jpg", parseDate("Wed, 23 Aug 2017 21:00:00 +0100"));
            return getSiteWithTees(teeOne, teeTwo);
        } catch (Exception e) {
            return null;
        }
    }

    public static Site getSiteWithChanges() {
        try {
            Tee teeOne = new Tee("Low Social Battery with changes", "https://www.qwertee.com/", "https://cdn.qwertee.com/images/designs/zoom/121045.jpg", parseDate("Wed, 23 Aug 2017 21:00:00 +0100"));
            Tee teeTwo = new Tee("I hate unicorns! with changes", "https://www.qwertee.com/", "https://cdn.qwertee.com/images/designs/zoom/120766.jpg", parseDate("Wed, 23 Aug 2017 21:00:00 +0100"));
            return getSiteWithTees(teeOne, teeTwo);
        } catch (Exception e) {
            return null;
        }
    }

    private static Site getSiteWithTees(Tee... teesArray) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            Tees tees = new Tees(teesArray);
            return new Site("Qwertee", "https://www.qwertee.com/", tees);
        } catch (Exception e) {
            return null;
        }
    }

    private static Date parseDate(String source) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        return simpleDateFormat.parse(source);
    }

}
