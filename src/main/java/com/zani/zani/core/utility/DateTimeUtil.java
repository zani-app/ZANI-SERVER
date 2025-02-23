package com.zani.zani.core.utility;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 날짜 및 시간 관련 유틸리티 클래스
 */
public class DateTimeUtil {

    public static final DateTimeFormatter ISODateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter ISODateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter ISOTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter KORDateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
    public static final DateTimeFormatter NotificationDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd (EEE)", Locale.KOREAN);
    public static final DateTimeFormatter KORDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd (HH시 mm분)");
    public static final DateTimeFormatter KORDateTimeEFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd (E) HH:mm",
            Locale.KOREAN);
    public static final DateTimeFormatter DartDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    public static final DateTimeFormatter YYMMDD_Formatter = DateTimeFormatter.ofPattern("yyMMdd");

    /**
     * String을 LocalDateTime으로 변환
     *
     * @param date String
     * @return LocalDateTime
     */
    public static LocalDateTime convertStringToLocalDateTime(String date) {
        return LocalDateTime.parse(date, ISODateTimeFormatter);
    }

    /**
     * LocalDateTime을 String으로 변환
     *
     * @param date LocalDateTime
     * @return String
     */
    public static String convertLocalDateTimeToString(LocalDateTime date) {
        return date.format(ISODateTimeFormatter);
    }

    /**
     * String을 LocalTime으로 변환
     *
     * @param time String
     * @return LocalTime
     */
    public static LocalTime convertStringToLocalTime(String time) {
        return LocalTime.parse(time, ISOTimeFormatter);
    }

    /**
     * LocalTime을 String으로 변환
     *
     * @param time LocalTime
     * @return String
     */
    public static String convertLocalTimeToString(LocalTime time) {
        return time.format(ISOTimeFormatter);
    }

    /**
     * String을 LocalDate로 변환
     *
     * @param date String
     * @return LocalDate
     */
    public static LocalDate convertStringToLocalDate(String date) {
        return LocalDate.parse(date, ISODateFormatter);
    }

    /**
     * LocalDate를 String으로 변환
     *
     * @param date LocalDate
     * @return String
     */
    public static String convertLocalDateToString(LocalDate date) {
        return date.format(ISODateFormatter);
    }

    /**
     * LocalDate를 yymmdd 형식의 String으로 변환
     *
     * @param date LocalDate
     * @return String
     */
    public static String convertLocalDateToYYMMDDString(LocalDate date) { return date.format(YYMMDD_Formatter); }

    /**
     * LocalDate를 한국어 날짜 형식으로 변환 (yyyy년 MM월 dd일)
     *
     * @param date LocalDate
     * @return String
     */
    public static String convertLocalDateToKORString(LocalDateTime date) {
        return date.format(KORDateFormatter);
    }

    /**
     * String(한국어 날짜 형식, yyyy년 MM월 dd일)을 LocalDate로 변환
     *
     * @param date String
     * @return LocalDate
     */
    public static LocalDateTime convertKORStringToLocalDate(String date) {
        return LocalDateTime.parse(date, KORDateFormatter);
    }

    /**
     * LocalDateTime을 한국어 날짜 및 시간 형식으로 변환 (yyyy.MM.dd (HH시 mm분))
     *
     * @param dateTime LocalDateTime
     * @return String
     */
    public static String convertLocalDateTimeToKORString(LocalDateTime dateTime) {
        return dateTime.format(KORDateTimeFormatter);
    }

    /**
     * LocalDateTime을 한국어 날짜 및 시간 형식으로 변환 (yyyy.MM.dd (E) HH:mm)
     *
     * @param dateTime LocalDateTime
     * @return String
     */
    public static String convertLocalDateTimeToKOREString(LocalDateTime dateTime) {
        return dateTime.format(KORDateTimeEFormatter);
    }

    /**
     * LocalDate를 Dart 날짜 형식으로 변환 (yyyy.MM.dd)
     *
     * @param dateTime LocalDate
     * @return String
     */
    public static String convertLocalDateToDartString(LocalDate dateTime) {
        return dateTime.format(DartDateFormatter);
    }

    /**
     * String을 Dart 날짜 형식으로 변환 (yyyy.MM.dd)
     *
     * @param date String
     * @return LocalDate
     */
    public static LocalDate convertStringToDartDate(String date) {
        return LocalDate.parse(date, DartDateFormatter);
    }

    /**
     * 두 날짜 사이의 일 수 계산
     *
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return Integer
     */
    public static Integer calculateDaysBetween(LocalDate startDate, LocalDate endDate) {
        return (int) (endDate.toEpochDay() - startDate.toEpochDay());
    }

    /**
     * 알림 시간 포맷팅: "mins ago", "hours ago", "yyyy.MM.dd (요일)"
     *
     * @param dateTime LocalDateTime
     * @return String
     */
    public static String formatNotificationTime(LocalDateTime dateTime) {
        Duration duration = Duration.between(dateTime, LocalDateTime.now());
        long minutesAgo = duration.toMinutes();
        long hoursAgo = duration.toHours();

        if (minutesAgo < 60) {
            return minutesAgo + " mins ago";
        } else if (hoursAgo < 24) {
            return hoursAgo + " hours ago";
        } else {
            return dateTime.format(NotificationDateFormatter);
        }
    }
}
