package io.my.calender.personel.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreatePersonelRequest {
    private String title;
    private String content;
    private String location;
    private Boolean open;

    private Long startTime;
    private Long endTime;
}
