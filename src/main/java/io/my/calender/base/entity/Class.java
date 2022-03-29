package io.my.calender.base.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@Table("class")
public class Class extends BaseEntity {

    @Id
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long collegeId;
    private String title;
    private String content;
    private String location;
    private Long userId;
    private Long professorId;

    @Transient
    private User user;
}
