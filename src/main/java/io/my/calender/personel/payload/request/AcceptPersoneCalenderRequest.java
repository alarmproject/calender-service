package io.my.calender.personel.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AcceptPersoneCalenderRequest {
    private Long personelCalenderId;
    private Boolean accept;
}
