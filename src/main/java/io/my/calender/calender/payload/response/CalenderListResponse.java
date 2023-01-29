package io.my.calender.calender.payload.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalenderListResponse {
    private Long id;
    private Long userId;
    private Long startTime;
    private Long endTime;

    private Long classId;
    private String classTitle;
    private String classLocation;

    private Long personalCalenderId;
    private String personalCalenderTitle;
    private String personalCalenderLocation;

    private String userName;
    private String imageUrl;
    private Integer inviteUserCount;
    private Integer acceptUserCount;

    private Boolean accept;
    private String alarmType;
}
