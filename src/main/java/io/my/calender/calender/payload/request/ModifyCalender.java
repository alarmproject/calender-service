package io.my.calender.calender.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ModifyCalender {
    private Long id;
    private Long startTime;
    private Long endTime;
}
