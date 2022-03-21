package io.my.calender.calender.payload.request._class;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class InviteClassRequeset {
    private Long classId;
    private List<Long> userList;
}
