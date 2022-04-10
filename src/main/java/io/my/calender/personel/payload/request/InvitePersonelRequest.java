package io.my.calender.personel.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class InvitePersonelRequest {
    private Long personelCalenderId;
    private List<Long> userList;
}
