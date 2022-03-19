package io.my.calender.calender.payload.request._class;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateClassTimeRequest {
    private Integer startTime;
    private Integer endTime;
    private String day;
}
