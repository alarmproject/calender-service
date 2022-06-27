package io.my.calender.personal.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ModifyPersonalCalenderRequest {
    private Long personalCalenderId;
    private String title;
    private String location;
    private Boolean open;
    private String alarmType;
}
