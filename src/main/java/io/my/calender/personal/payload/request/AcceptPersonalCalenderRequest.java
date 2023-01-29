package io.my.calender.personal.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AcceptPersonalCalenderRequest {
    private Long personalCalenderId;
    private String content;
    private String alarmType;
    private Byte accept;
}
