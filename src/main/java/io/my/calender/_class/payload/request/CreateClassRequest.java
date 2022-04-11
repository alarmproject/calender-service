package io.my.calender._class.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CreateClassRequest {
    private Long startDate;
    private Long endDate;

    private String title;
    private String content;
    private String location;

    private Long collegeId;
    private Long professorId;

    private String alarmType;

    private List<CreateClassTimeRequest> classTimeList;
}

