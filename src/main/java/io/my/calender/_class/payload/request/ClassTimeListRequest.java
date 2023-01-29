package io.my.calender._class.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassTimeListRequest {
    private int startHour;
    private int startMinutes;
    private int endHour;
    private int endMinutes;
    private String day;
}
