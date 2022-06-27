package io.my.calender.personal.payload.request;

import io.my.calender.personal.payload.response.PersonalCalenderJoinUserInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalCalenderDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String location;
    private String day;
    private Boolean open;
    private Long startTime;
    private Long endTime;
    private List<PersonalCalenderJoinUserInfoResponse> joinUserList;
    private Integer inviteUserCount;
    private Integer acceptUserCount;
    private Boolean isAccept;

    public void setJoinUserList(List<PersonalCalenderJoinUserInfoResponse> list) {
        this.joinUserList = list;
    }

    public void setInviteUserCount(Integer inviteUserCount) {
        this.inviteUserCount = inviteUserCount;
    }

    public void setAcceptUserCount(Integer acceptUserCount) {
        this.acceptUserCount = acceptUserCount;
    }

    public void setIsAccept(Boolean isAccept) {
        this.isAccept = isAccept;
    }


}
