package io.my.calender._class.payload.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class AcceptClassRequest {
    private Long classId;
    private Byte accept;
    private String content;
    private String alarmType;
}
