package io.my.calender.base.repository.dao;


import io.my.calender.base.properties.ServerProperties;
import io.my.calender.base.repository.query.PersonalCalenderJoinUserQuery;
import io.my.calender.base.util.DateUtil;
import io.my.calender.personal.payload.response.PersonalCalenderInviteResponse;
import io.my.calender.personal.payload.response.PersonalCalenderJoinUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonalCalenderJoinUserDAO {
    private final DateUtil dateUtil;
    private final ServerProperties serverProperties;
    private final PersonalCalenderJoinUserQuery personalCalenderJoinUserQuery;

    public Flux<PersonalCalenderJoinUserInfoResponse> findPersonalCalenderJoinUserInfo(Long id) {
        return this.personalCalenderJoinUserQuery.findPersonalCalenderJoinUserInfo(id)
                .map((row, rowMetadata) -> {
                    String imageUrl = row.get("file_name", String.class);
                    if (imageUrl != null) imageUrl = serverProperties.getImageUrl() + serverProperties.getImagePath() + imageUrl;

                    return PersonalCalenderJoinUserInfoResponse.builder()
                            .userId(row.get("user_id", Long.class))
                            .accept(row.get("accept", Byte.class))
                            .name(row.get("name", String.class))
                            .nickname(row.get("nickname", String.class))
                            .imageUrl(imageUrl)
                            .build();
                })
                .all();
    }

    public Flux<PersonalCalenderInviteResponse> findPersonalInvite(Long userId, int accept) {
        return this.personalCalenderJoinUserQuery.findPersonalInvite(userId, accept)
                .map((row, rowMetadata) -> {
                    Long startTime = null;
                    Long endTime = null;
                    try {
                        startTime = dateUtil.localDateTimeToUnixTime(row.get("start_time", LocalDateTime.class));
                        endTime = dateUtil.localDateTimeToUnixTime(row.get("end_time", LocalDateTime.class));
                    } catch (NullPointerException e) { /* do nothing */ }

                    return PersonalCalenderInviteResponse.builder()
                            .id(row.get("id", Long.class))
                            .title(row.get("title", String.class))
                            .location(row.get("location", String.class))
                            .alarmType(row.get("alarm_type", String.class))
                            .day(row.get("day", String.class))
                            .startTime(startTime)
                            .endTime(endTime)
                            .build();
                }).all();

    }
}
