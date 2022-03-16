package io.my.calender.base.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table("personel_calender_change")
public class PersonelCalenderChange {
    @Id
    private Long id;
    private Long personelCalenderId;
    private LocalDateTime regDateTime;
}
