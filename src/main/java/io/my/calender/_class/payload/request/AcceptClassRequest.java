package io.my.calender._class.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AcceptClassRequest {
    private Long classId;
    private Boolean accept;
}
