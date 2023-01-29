package io.my.calender.base.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table("calender_change")
public class CalenderChange {
    @Id
    private Long id;
    private Long calenderId;
    private LocalDateTime regDate;

    @Transient
    private Calender calender;
}
