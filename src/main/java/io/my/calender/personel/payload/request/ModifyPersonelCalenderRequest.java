package io.my.calender.personel.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ModifyPersonelCalenderRequest {
    private Long personelCalenderId;
    private String title;
    private String location;
    private Boolean open;
    private String alarmType;
}
