package io.my.calender._class.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ModifyClassInfoRequest {
    private Long id;
    private String title;
    private String content;
    private String location;
    private Long professorId;
    private String alarmType;
}
