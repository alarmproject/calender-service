package io.my.calender.personel.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPersonelCalenderListResponse {
    private Long id;
    private String day;
    private String title;
    private String content;
    private String location;
    private Boolean open;
    private Boolean accept;
    private Long userId;
    private String name;
    private String nickname;
    private String email;
    private String imageUrl;
}
