package io.my.calender.calender.payload.request.personel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreatePersonelCalenderRequest {
    private String title;
    private String content;
    private String location;
    private Boolean open;

    private Long startTime;
    private Long endTime;
}
