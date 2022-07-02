package io.my.calender.base.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("personal_calender_join_user")
public class PersonalCalenderJoinUser {
    @Id
    private Long id;
    private Long userId;
    private Long personalCalenderId;
    private Byte accept;
    private String content;
    private String alarmType;

    @Transient
    private User user;
    @Transient
    private PersonalCalender personalCalender;
}
