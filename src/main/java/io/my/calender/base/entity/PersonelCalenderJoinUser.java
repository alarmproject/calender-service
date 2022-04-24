package io.my.calender.base.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("personel_calender_join_user")
public class PersonelCalenderJoinUser {
    @Id
    private Long id;
    private Long userId;
    private Long personelCalenderId;
    private Byte accept;
    private String content;
    private String alarmType;

    @Transient
    private User user;
    @Transient
    private PersonelCalender personelCalender;
}
