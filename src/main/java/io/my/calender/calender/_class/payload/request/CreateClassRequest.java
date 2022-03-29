package io.my.calender.calender._class.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CreateClassRequest {
    private Long startDate;
    private Long endDate;

    private String title;
    private String content;
    private String location;

    private Long collegeId;
    private Long professorId;

    private List<CreateClassTimeRequest> classTimeList;
}

