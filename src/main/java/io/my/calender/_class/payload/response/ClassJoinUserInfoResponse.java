package io.my.calender._class.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassJoinUserInfoResponse {
    private Long userId;
    private Byte accept;
    private String imageUrl;
    private String name;
    private String nickname;
}
