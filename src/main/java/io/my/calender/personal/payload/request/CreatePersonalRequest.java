package io.my.calender.personal.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreatePersonalRequest {
    private String title;
    private String location;
    private Boolean open;
    private String alarmType;

    private Long startTime;
    private Long endTime;
}
