package io.my.calender.base.util;

import org.springframework.stereotype.Component;

import java.time.*;

@Component
public class DateUtil {

    public Long localDateTimeToUnixTime(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public LocalDateTime unixTimeToLocalDateTime(Long unixTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(unixTime), ZoneId.systemDefault());
    }

    public String unixTimeToDay(Long unixTime) {
        DayOfWeek dayOfweek = this.unixTimeToLocalDateTime(unixTime).getDayOfWeek();
        return this.getDay(dayOfweek);
    }

    public String localDateTimeToDay(LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        return this.getDay(dayOfWeek);
    }

    public String getDay(DayOfWeek dayOfweek) {
        String result = "";

        switch (dayOfweek) {
            case SUNDAY:
                result = "일";
                break;
            case MONDAY:
                result = "월";
                break;
            case TUESDAY:
                result = "화";
                break;
            case WEDNESDAY:
                result = "수";
                break;
            case THURSDAY:
                result = "목";
                break;
            case FRIDAY:
                result = "금";
                break;
            case SATURDAY:
                result = "토";
                break;
        }
        return result;
    }

    public DayOfWeek getDayOfWeek(String day) {
        if (day.equals("일")) return DayOfWeek.SUNDAY;
        else if (day.equals("월")) return DayOfWeek.MONDAY;
        else if (day.equals("화")) return DayOfWeek.TUESDAY;
        else if (day.equals("수")) return DayOfWeek.WEDNESDAY;
        else if (day.equals("목")) return DayOfWeek.THURSDAY;
        else if (day.equals("금")) return DayOfWeek.FRIDAY;
        else if (day.equals("토")) return DayOfWeek.SATURDAY;
        throw new RuntimeException("Day is wrong");
    }

    public LocalDate findWeekStart(LocalDate date) {
        return date.minusDays(date.getDayOfWeek().getValue() - 1);
    }

    public LocalDate findWeekEnd(LocalDate date) {
        return date.plusDays(7 - date.getDayOfWeek().getValue());
    }

    public LocalDate findMonthStart(LocalDate date) {
        return date.withDayOfMonth(1);
    }

    public LocalDate findMonthEnd(LocalDate date) {
        return date.withDayOfMonth(date.lengthOfMonth());
    }

    public Integer diffrentDay(DayOfWeek day1, DayOfWeek day2) {
        return day1.getValue() - day2.getValue();
    }
}
