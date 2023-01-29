package io.my.calender.base.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("active_history")
public class ActiveHistory {
    @Id
    private Long id;
    private Long userId;
    private String content;
}
