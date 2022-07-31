package io.my.calender._class.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ModifyClassInfoRequest {
    private Long id;
    private String title;
    private String location;
    private Long professorId;
    private String alarmType;

    private Long startDate;
    private Long endDate;
    private String content;
    private List<ClassTimeListRequest> classTimeList;
    private Boolean isChangeActiveHistory;
}
