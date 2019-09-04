package cn.edu.aufe.cstfirst.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author zhuwenwen
 * @date 2019/9/4 21:58
 **/
public class LocalDateTimeUtil {

    public static LocalDateTime plusMinute(int minute) {
        return LocalDateTime.now().plusHours(minute);
    }

    public static LocalDateTime plusMinute(LocalDateTime localDateTime, int minute) {
        return localDateTime.plusHours(minute);
    }

    public static LocalDateTime plusHour(int house) {
        return LocalDateTime.now().plusHours(house);
    }

    public static LocalDateTime plusHour(LocalDateTime localDateTime, int house) {
        return localDateTime.plusHours(house);
    }

    public static Date localDateTime2Date( LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        /**
         * Combines this date-time with a time-zone to create a  ZonedDateTime.
         */
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }
}
