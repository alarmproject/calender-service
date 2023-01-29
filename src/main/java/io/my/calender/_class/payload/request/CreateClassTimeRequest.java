package io.my.calender._class.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateClassTimeRequest {
    private Integer startHour;
    private Integer endHour;
    private Integer startMinutes;
    private Integer endMinutes;
    private String day;
}
