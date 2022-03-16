package io.my.calender.base.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("class_time")
public class ClassTime extends BaseEntity{
    @Id
    private Long id;
    private String startTime;
    private String endTime;
    private String day;
    private Long classId;

    @Transient
    private Class _class;
}
