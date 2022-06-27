package io.my.calender.personal.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalCalenderJoinUserInfoResponse {
    private Long userId;
    private Byte accept;
    private String name;
    private String nickname;
    private String imageUrl;
}
