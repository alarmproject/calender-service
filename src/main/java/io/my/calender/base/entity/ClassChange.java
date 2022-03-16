package io.my.calender.base.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table("class_change")
public class ClassChange {

    @Id
    private Long id;
    private Long classId;
    private LocalDateTime regDate;

    @Transient
    private Class _class;
}
