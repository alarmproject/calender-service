package io.my.calender._class.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InviteClassListResponse {
    private Long id;
    private String title;
    private String professorName;
    private String location;
    private String imageUrl;
    private String alarmType;
    private List<InviteClassTimeListResponse> classTimeList;

    public void setClassTimeList(List<InviteClassTimeListResponse> list) {
        this.classTimeList = list;
    }
}
