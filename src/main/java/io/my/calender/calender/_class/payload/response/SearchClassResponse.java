package io.my.calender.calender._class.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchClassResponse {
    private Long id;
    private Long startDate;
    private Long endDate;
    private String title;
    private String content;
    private String location;
    private String professorName;
    private String imageUrl;
    private Integer inviteUserCount;
    private Integer acceptUserCount;
    private String day;
}
