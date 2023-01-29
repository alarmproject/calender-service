package io.my.calender.personal.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class InvitePersonalRequest {
    private Long personalCalenderId;
    private List<Long> userList;
}
