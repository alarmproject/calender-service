package io.my.calender.personal.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalCalenderInviteResponse {
    private Long id;
    private String title;
    private String location;
    private String alarmType;
    private String day;
    private Long startTime;
    private Long endTime;
    private Integer acceptUserCount;
    private String inviteUser;

    public void setAcceptUserCount(Integer acceptUserCount) {
        this.acceptUserCount = acceptUserCount;
    }

}
