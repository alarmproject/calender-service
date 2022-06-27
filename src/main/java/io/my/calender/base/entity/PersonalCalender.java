package io.my.calender.base.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("personel_calender")
public class PersonalCalender {

    @Id
    private Long id;
    private String day;
    private String title;
    private String location;
    private Boolean open;
    private Long userId;
    private String alarmType;

    @Transient
    private User user;
}
