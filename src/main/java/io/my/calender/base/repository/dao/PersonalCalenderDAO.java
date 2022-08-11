package io.my.calender.base.repository.dao;

import io.my.calender.base.properties.ServerProperties;
import io.my.calender.base.repository.query.PersonalCalenderQuery;
import io.my.calender.base.util.DateUtil;
import io.my.calender.personal.payload.request.PersonalCalenderDetailResponse;
import io.my.calender.personal.payload.response.MyPersonalCalenderListResponse;
import io.my.calender.personal.payload.response.SearchPersonalCalenderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class PersonalCalenderDAO {
    private final DateUtil dateUtil;
    private final ServerProperties serverProperties;
    private final PersonalCalenderQuery personalCalenderQuery;

    public Flux<MyPersonalCalenderListResponse> findMyPersonalCalenderList(Long userId, Long personalCalenderId, Boolean accept, Boolean open) {
        return this.personalCalenderQuery.findMyPersonalCalenderList(userId, personalCalenderId, accept, open)
            .map((row, rowMetadata) -> {

                String imageUrl = row.get("user_image", String.class);
                if (imageUrl != null) imageUrl = serverProperties.getImageUrl() + serverProperties.getImagePath() + imageUrl;

                return MyPersonalCalenderListResponse.builder()
                        .id(row.get("id", Long.class))
                        .day(row.get("day", String.class))
                        .accept(row.get("accept", Boolean.class))
                        .open(row.get("open", Boolean.class))
                        .title(row.get("title", String.class))
                        .content(row.get("content", String.class))
                        .location(row.get("location", String.class))
                        .userId(row.get("user_id", Long.class))
                        .name(row.get("user_name", String.class))
                        .nickname(row.get("user_nickname", String.class))
                        .email(row.get("user_email", String.class))
                        .alarmType(row.get("alarm_type", String.class))
                        .imageUrl(imageUrl)
                        .build();
            })
            .all();
    }

    public Flux<SearchPersonalCalenderListResponse> searchPersonalCalenderList(Long personalCalenderId, Integer perPage, String title) {
        return this.personalCalenderQuery.searchPersonalCalenderList(personalCalenderId, perPage, title)
                .map((row, rowMetadata) -> {
                    String imageUrl = row.get("user_image", String.class);
                    if (imageUrl != null) imageUrl = serverProperties.getImageUrl() + serverProperties.getImagePath() + imageUrl;

                    return SearchPersonalCalenderListResponse.builder()
                            .id(row.get("id", Long.class))
                            .title(row.get("title", String.class))
                            .location(row.get("location", String.class))
                            .open(row.get("open", Boolean.class))
                            .day(row.get("day", String.class))
                            .regDateTime(
                                    dateUtil.localDateTimeToUnixTime(row.get("reg_date_time", LocalDateTime.class))
                            )
                            .modDateTime(
                                    dateUtil.localDateTimeToUnixTime(row.get("mod_date_time", LocalDateTime.class))
                            )
                            .startTime(
                                    dateUtil.localDateTimeToUnixTime(row.get("start_time", LocalDateTime.class))
                            )
                            .endTime(
                                    dateUtil.localDateTimeToUnixTime(row.get("end_time", LocalDateTime.class))
                            )
                            .userId(row.get("user_id", Long.class))
                            .name(row.get("user_name", String.class))
                            .nickname(row.get("user_nickname", String.class))
                            .email(row.get("user_email", String.class))
                            .imageUrl(imageUrl)
                            .build()
                            ;
                })
                .all()
                ;
    }


    public Mono<PersonalCalenderDetailResponse> findPersonalCalenderDetail(Long id, Long userId) {
        return this.personalCalenderQuery.findPersonalCalenderDetail(id, userId).map((row, rowMetadata) ->
                PersonalCalenderDetailResponse.builder()
                    .id(row.get("id", Long.class))
                    .userId(row.get("user_id", Long.class))
                    .title(row.get("title", String.class))
                    .content(row.get("content", String.class))
                    .location(row.get("location", String.class))
                    .day(row.get("day", String.class))
                    .open(row.get("open", Boolean.class))
                    .startTime(
                            dateUtil.localDateTimeToUnixTime(row.get("start_time", LocalDateTime.class))
                    )
                    .endTime(
                            dateUtil.localDateTimeToUnixTime(row.get("end_time", LocalDateTime.class))
                    )
                    .build()
        ).one();
    }
}
