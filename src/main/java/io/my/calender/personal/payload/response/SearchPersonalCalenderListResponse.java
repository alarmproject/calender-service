package io.my.calender.personal.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPersonalCalenderListResponse {
    private Long id;
    private String day;
    private String title;
    private String location;
    private Boolean open;
    private Long regDateTime;
    private Long modDateTime;
    private Long startTime;
    private Long endTime;
    private Long userId;
    private String name;
    private String nickname;
    private String email;
    private String imageUrl;
}
