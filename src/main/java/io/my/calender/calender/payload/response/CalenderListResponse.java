package io.my.calender.calender.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CalenderListResponse {
    private Long id;
    private Long startTime;
    private Long endTime;

    private Long classId;
    private String classTitle;
    private String classLocation;

    private Long personelCalenderId;
    private String personelCalenderTitle;
    private String personelCalenderLocation;
}
