package io.my.calender.base.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("personel_calender")
public class PersonelCalender {

    @Id
    private Long id;
    private String day;
    private String title;
    private String content;
    private String location;
    private Boolean open;
    private Long userId;

    @Transient
    private User user;
}
