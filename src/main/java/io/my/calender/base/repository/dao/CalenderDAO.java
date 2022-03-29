package io.my.calender.base.repository.dao;

import io.my.calender.base.properties.ServerProperties;
import io.my.calender.base.repository.query.CalenderQuery;
import io.my.calender.base.util.DateUtil;
import io.my.calender.calender.payload.response.CalenderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CalenderDAO {
    private final DateUtil dateUtil;
    private final ServerProperties serverProperties;
    private final CalenderQuery calenderQuery;

    public Flux<CalenderListResponse> findCalenderListResponse(Long userId, LocalDate startDate, LocalDate endDate) {
        return this.calenderQuery.findCalenderListResponse(userId, startDate, endDate)
                .map((row, rowMetadata) -> {
                    Long startTime = dateUtil.localDateTimeToUnixTime(
                            Objects.requireNonNull(row.get("start_time", LocalDateTime.class)));
                    Long endTime = dateUtil.localDateTimeToUnixTime(
                            Objects.requireNonNull(row.get("end_time", LocalDateTime.class)));

                    Long classId = row.get("class_id", Long.class);
                    Long personelCalenderId = row.get("personel_calender_id", Long.class);

                    Integer inviteUserCount = 0;
                    Integer acceptUserCount = 0;

                    if (classId != null) {
                        inviteUserCount = row.get("class_invite_count", Integer.class);
                        acceptUserCount = row.get("class_accept_count", Integer.class);
                    } else if (personelCalenderId != null) {
                        inviteUserCount = row.get("personel_calender_invite_count", Integer.class);
                        acceptUserCount = row.get("personel_calender_accept_count", Integer.class);
                    }

                    String imageUrl = row.get("filename", String.class);
                    if (imageUrl != null) {
                        imageUrl = serverProperties.getImageUrl() +
                                serverProperties.getImagePath() + imageUrl;
                    }

                    String userName = row.get("professor_name", String.class);
                    if (userName == null) userName = row.get("user_name", String.class);

                    return CalenderListResponse.builder()
                            .id(row.get("id", Long.class))
                            .startTime(startTime)
                            .endTime(endTime)
                            .classId(classId)
                            .classTitle(row.get("class_title", String.class))
                            .classLocation(row.get("class_location", String.class))
                            .personelCalenderId(personelCalenderId)
                            .personelCalenderTitle(row.get("personel_calender_title", String.class))
                            .personelCalenderLocation(row.get("personel_calender_location", String.class))
                            .userName(userName)
                            .imageUrl(imageUrl)
                            .inviteUserCount(inviteUserCount)
                            .acceptUserCount(acceptUserCount)
                            .build()
                            ;
                }).all()
                ;

    }
}
