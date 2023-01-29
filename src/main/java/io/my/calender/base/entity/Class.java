package io.my.calender.base.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("class")
public class Class extends BaseEntity {

    @Id
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long collegeId;
    private String title;
    private String location;
    private Long userId;
    private Long professorId;
    private String alarmType;

    @Transient
    private User user;
}
