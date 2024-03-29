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
public class SearchClassResponse {
    private Long id;
    private Long userId;
    private Long startDate;
    private Long endDate;
    private String title;
    private String location;
    private String professorName;
    private String imageUrl;
    private Integer inviteUserCount;
    private Integer acceptUserCount;
    private List<ClassTimeListResponse> classTimeList;
}
