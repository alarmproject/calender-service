package io.my.calender._class.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InviteClassListResponse {
    private Long id;
    private String title;
    private String content;
    private String professorName;
    private String location;
    private String imageUrl;
}
