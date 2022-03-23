package io.my.calender.calender.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ModifyCalender {
    private Long id;
    private Long startTime;
    private Long endTime;
}
