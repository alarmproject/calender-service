package io.my.calender.calender._class.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ModifyClassInfoRequest {
    private Long id;
    private String title;
    private String content;
    private String location;
    private Long professorId;
}
