package io.my.calender.base.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table("calender")
public class Calender extends BaseEntity {
    @Id
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long classTimeId;
    private Long personelCalenderId;

    @Transient
    private ClassTime classTime;
    @Transient
    private PersonelCalender personelCalender;

}
