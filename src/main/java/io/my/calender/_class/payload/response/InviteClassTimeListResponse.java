package io.my.calender._class.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InviteClassTimeListResponse {
    private String day;
    private Integer startHour;
    private Integer startMinutes;
    private Integer endHour;
    private Integer endMinutes;
}
