package io.my.calender.calender._class.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ModifyClassTime {
    private Long id;
    private String day;
    private Integer startTime;
    private Integer endTime;
}
