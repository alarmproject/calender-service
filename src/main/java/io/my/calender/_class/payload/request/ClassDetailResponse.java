package io.my.calender._class.payload.request;

import io.my.calender._class.payload.response.ClassJoinUserInfoResponse;
import io.my.calender._class.payload.response.ClassTimeListResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClassDetailResponse {
    private Long id;
    private Long userId;
    private String title;
    private Long startDate;
    private Long endDate;
    private String content;
    private String location;

    private List<ClassTimeListResponse> classTimeList;
    private List<ClassJoinUserInfoResponse> joinUserList;
    private Integer inviteUserCount;
    private Integer acceptUserCount;
    private Boolean isAccept;

    private Long professorId;
    private String professorName;
    private String alarmType;

    public void setJoinUserList(List<ClassJoinUserInfoResponse> list) {
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
