package com.idanchuang.cms.server.infrastructure.adcontentservice.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author: hulk.Wang
 **/
public class DateTimeUtils {

    private static final String timezoneOffset = "+8";

    public static Long localDatTime2timeStamp(LocalDateTime localDateTime){
        return localDateTime.toEpochSecond(ZoneOffset.of(timezoneOffset));
    }

    public static Long Date2timeStamp(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDatTime2timeStamp (localDateTime);
    }


    public static Long dateString2timeStamp(String string){
        if(StringUtils.isBlank (string)){
            return 0L;
        }
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(string,dateFormat);
        return localDatTime2timeStamp (localDateTime);
    }

    public static String localDateTime2String(LocalDateTime localDateTime, String pattern) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 时间大于等于
     * @param datetime
     * @param compareDateTime
     * @return
     */
    public static boolean localDatTimeAfterEqual(LocalDateTime datetime, LocalDateTime compareDateTime){
        return datetime.isAfter (compareDateTime) || datetime.equals (compareDateTime);
    }

    /**
     * 时间小于等于
     * @param datetime
     * @param compareDateTime
     * @return
     */
    public static boolean localDatTimeBeforeEqual(LocalDateTime datetime, LocalDateTime compareDateTime){
        return datetime.isBefore (compareDateTime) || datetime.equals (compareDateTime);
    }


    public static LocalDateTime string2LocalDateTime(String dateString){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateString, df);
    }

    public static LocalDateTime formatLocalDateTime(String dateTime) {
        if (StringUtils.isEmpty(dateTime)) {
            return null;
        }
        dateTime = dateTime.replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        try {
            return asLocalDateTime(format.parse(dateTime));
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
