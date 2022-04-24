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
@Table("class_time")
public class ClassTime extends BaseEntity{
    @Id
    private Long id;
    private Integer startTime;
    private Integer endTime;
    private String day;
    private Long classId;

    @Transient
    private Class _class;
}
