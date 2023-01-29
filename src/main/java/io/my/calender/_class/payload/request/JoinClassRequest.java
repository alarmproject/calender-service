package io.my.calender._class.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JoinClassRequest {
    private Long classId;
    private String content;
    private String alarmType;
}
