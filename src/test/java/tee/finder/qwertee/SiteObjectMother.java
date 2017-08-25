package tee.finder.qwertee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class SiteObjectMother {

    public static Site getExpectedSite() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        Tee teeOne = new Tee("Low Social Battery", "https://www.qwertee.com/", "https://cdn.qwertee.com/images/designs/zoom/121045.jpg", simpleDateFormat.parse("Wed, 23 Aug 2017 21:00:00 +0100"));
        Tee teeTwo = new Tee("I hate unicorns!", "https://www.qwertee.com/", "https://cdn.qwertee.com/images/designs/zoom/120766.jpg", simpleDateFormat.parse("Wed, 23 Aug 2017 21:00:00 +0100"));
        Tees tees = new Tees(teeOne, teeTwo);
        return new Site("Qwertee", "https://www.qwertee.com/", tees);
    }

}
