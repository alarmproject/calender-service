package io.my.calender.base.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("class_join_user")
public class ClassJoinUser extends BaseEntity {
    @Id
    private Long id;
    private Long userId;
    private Long classId;
    private Byte accept;
    private String content;
    private String alarmType;

    @Transient
    private User user;
    @Transient
    private Class _class;

    public void setAccept(Byte accept) {
        this.accept = accept;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }
}
