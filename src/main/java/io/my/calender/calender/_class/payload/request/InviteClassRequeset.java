package io.my.calender.calender._class.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class InviteClassRequeset {
    private Long classId;
    private List<Long> userList;
}
