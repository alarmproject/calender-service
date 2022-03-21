package io.my.calender.calender.payload.request.personel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class InvitePersonelRequest {
    private Long personelCalenderId;
    private List<Long> userList;
}
