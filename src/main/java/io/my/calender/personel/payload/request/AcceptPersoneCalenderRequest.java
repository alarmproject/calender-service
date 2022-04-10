package io.my.calender.personel.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AcceptPersoneCalenderRequest {
    private Long personelCalenderId;
    private Boolean accept;
}
