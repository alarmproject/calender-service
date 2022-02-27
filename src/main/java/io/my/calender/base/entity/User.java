package io.my.calender.base.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("user")
public class User extends BaseEntity {

    @Id
    private Long id;
    private Byte type;
    private String name;
    private String nickname;
    private String password;
    private String email;
    private String deviceToken;
    private String collegeEmail;

    private Long imageId;
    private Long collegeId;

    @Transient
    private Image image;



}
