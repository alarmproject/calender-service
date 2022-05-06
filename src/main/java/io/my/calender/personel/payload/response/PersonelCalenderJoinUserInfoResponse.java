package io.my.calender.personel.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonelCalenderJoinUserInfoResponse {
    private Long userId;
    private Byte accept;
    private String name;
    private String nickname;
    private String imageUrl;
}
