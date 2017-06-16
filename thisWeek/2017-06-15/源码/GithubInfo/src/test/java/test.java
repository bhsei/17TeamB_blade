import org.junit.Test;
import utils.ProgressUtil;
import utils.PropsUtil;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by HMH on 2017/4/18.
 */
public class test {

    @Test
    public void testProgress() throws InterruptedException, ParseException {
        String dateStr = "2017-06-13T07:24:09Z";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
//        TimeZone timeGithub = TimeZone.getTimeZone("America/New_York");
        TimeZone timeshanghai = TimeZone.getTimeZone("England/London");
        dateFormat.setTimeZone(timeshanghai);
        Date date = dateFormat.parse(dateStr);
        System.out.println(date);
//        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(""));


    }
}
