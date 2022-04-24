package io.my.calender.personel.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AcceptPersoneCalenderRequest {
    private Long personelCalenderId;
    private String content;
    private String alarmType;
    private Boolean accept;
}
