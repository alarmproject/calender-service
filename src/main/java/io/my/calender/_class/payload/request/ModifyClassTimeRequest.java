package io.my.calender._class.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ModifyClassTimeRequest {
    private Long id;
    private String day;
    private Integer startHour;
    private Integer startMinutes;
    private Integer endHour;
    private Integer endMinutes;
}
