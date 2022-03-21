package io.my.calender.base.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class DateUtilTest {
    DateUtil dateUtil = new DateUtil();

    @Test
    void localDateTimeToUnixTime() {
        Long firstUnixTime = this.dateUtil.localDateTimeToUnixTime(LocalDateTime.now());
        Long secondUnixTime = this.dateUtil.localDateTimeToUnixTime(LocalDateTime.now());

        log.info("firstUnixTime: {}", firstUnixTime);
        log.info("secondUnixTime: {}", secondUnixTime);

        assertTrue(firstUnixTime <= secondUnixTime);
    }

    @Test
    void unixTimeToLocalDateTime() {
        LocalDateTime firstUnixTime = this.dateUtil.unixTimeToLocalDateTime(1647423158970L);
        LocalDateTime secondUnixTime = this.dateUtil.unixTimeToLocalDateTime(1647423158972L);

        log.info("firstUnixTime: {}", firstUnixTime);
        log.info("secondUnixTime: {}", secondUnixTime);

        assertTrue(firstUnixTime.isBefore(secondUnixTime));
    }

    @Test
    void unixTimeToDay() {
        Date date = new Date();
        date.setYear(122); // 2022 = 1900 + 122
        date.setMonth(2);  // 3 = 2 + 1
        date.setDate(19);
        assertEquals("토", dateUtil.unixTimeToDay(date.getTime()));
    }

    @Test
    void localDateTimeToDay() {
        LocalDateTime of = LocalDateTime.of(
                LocalDate.of(2022, 3, 19),
                LocalTime.now()
        );
        assertEquals("토", dateUtil.localDateTimeToDay(of));
    }

    @Test
    void getDay() {
        assertEquals("일", this.dateUtil.getDay(DayOfWeek.SUNDAY));
        assertEquals("월", this.dateUtil.getDay(DayOfWeek.MONDAY));
        assertEquals("화", this.dateUtil.getDay(DayOfWeek.TUESDAY));
        assertEquals("수", this.dateUtil.getDay(DayOfWeek.WEDNESDAY));
        assertEquals("목", this.dateUtil.getDay(DayOfWeek.THURSDAY));
        assertEquals("금", this.dateUtil.getDay(DayOfWeek.FRIDAY));
        assertEquals("토", this.dateUtil.getDay(DayOfWeek.SATURDAY));
    }

    @Test
    void getDayOfWeek() {
        assertEquals(DayOfWeek.SUNDAY, this.dateUtil.getDayOfWeek("일"));
        assertEquals(DayOfWeek.MONDAY, this.dateUtil.getDayOfWeek("월"));
        assertEquals(DayOfWeek.TUESDAY, this.dateUtil.getDayOfWeek("화"));
        assertEquals(DayOfWeek.WEDNESDAY, this.dateUtil.getDayOfWeek("수"));
        assertEquals(DayOfWeek.THURSDAY, this.dateUtil.getDayOfWeek("목"));
        assertEquals(DayOfWeek.FRIDAY, this.dateUtil.getDayOfWeek("금"));
        assertEquals(DayOfWeek.SATURDAY, this.dateUtil.getDayOfWeek("토"));
        assertThrows(RuntimeException.class, () -> this.dateUtil.getDayOfWeek("?"));
    }

    @Test
    void findWeekStart() {
        LocalDate weekStartDate = LocalDate.of(2022, 03, 21);
        LocalDate randomDate = LocalDate.of(2022, 03, 25);

        assertEquals(weekStartDate, this.dateUtil.findWeekStart(randomDate));
    }

    @Test
    void findWeekEnd() {
        LocalDate weekEndDate = LocalDate.of(2022, 03, 27);
        LocalDate randomDate = LocalDate.of(2022, 03, 25);
        assertEquals(weekEndDate, this.dateUtil.findWeekEnd(randomDate));
    }

    @Test
    void findMonthStart() {
        LocalDate monthStartDate = LocalDate.of(2022, 03, 01);
        LocalDate randomDate = LocalDate.of(2022, 03, 21);
        assertEquals(monthStartDate, this.dateUtil.findMonthStart(randomDate));

        randomDate = LocalDate.of(2022, 03, 3);
        assertEquals(monthStartDate, this.dateUtil.findMonthStart(randomDate));

        randomDate = LocalDate.of(2022, 03, 31);
        assertEquals(monthStartDate, this.dateUtil.findMonthStart(randomDate));
    }

    @Test
    void findMonthEnd() {
        LocalDate monthEndDate = LocalDate.of(2022, 03, 31);
        LocalDate randomDate = LocalDate.of(2022, 03, 22);
        assertEquals(monthEndDate, this.dateUtil.findMonthEnd(randomDate));

        randomDate = LocalDate.of(2022, 03, 21);
        assertEquals(monthEndDate, this.dateUtil.findMonthEnd(randomDate));

        randomDate = LocalDate.of(2022, 03, 1);
        assertEquals(monthEndDate, this.dateUtil.findMonthEnd(randomDate));
    }



}