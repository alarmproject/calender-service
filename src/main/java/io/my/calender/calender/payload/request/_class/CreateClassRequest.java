package io.my.calender.calender.payload.request._class;

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

    private List<CreateClassTimeRequest> classTimeList;
}

