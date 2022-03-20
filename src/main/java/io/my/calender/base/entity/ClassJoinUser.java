package io.my.calender.base.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("class_join_user")
public class ClassJoinUser extends BaseEntity {
    @Id
    private Long id;
    private Long userId;
    private Long classId;
    private Boolean accept;

    @Transient
    private User user;
    @Transient
    private Class _class;
}
