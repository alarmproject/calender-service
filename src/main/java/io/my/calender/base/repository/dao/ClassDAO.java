package io.my.calender.base.repository.dao;

import io.my.calender.base.properties.ServerProperties;
import io.my.calender.base.repository.query.ClassQuery;
import io.my.calender.base.util.DateUtil;
import io.my.calender._class.payload.response.InviteClassListResponse;
import io.my.calender._class.payload.response.SearchClassResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class ClassDAO {
    private final DateUtil dateUtil;
    private final ClassQuery classQuery;
    private final ServerProperties serverProperties;

    public Flux<SearchClassResponse> searchClasses(Long classId, Long collegeId, String title, Integer perPage) {
        return this.classQuery.searchClasses(classId, collegeId, title, perPage)
                .map(((row, rowMetadata) -> {
                    String imageUrl = row.get("file_name", String.class);

                    if (imageUrl != null) imageUrl =
                            serverProperties.getImageUrl() +
                            serverProperties.getImagePath() +
                            imageUrl
                            ;

                    return SearchClassResponse.builder()
                            .id(row.get("id", Long.class))
                            .userId(row.get("user_id", Long.class))
                            .startDate(dateUtil.localDateTimeToUnixTime(
                                    row.get("start_date", LocalDateTime.class)
                            ))
                            .endDate(dateUtil.localDateTimeToUnixTime(
                                    row.get("end_date", LocalDateTime.class)
                            ))
                            .title(row.get("title", String.class))
                            .location(row.get("location", String.class))
                            .professorName(row.get("professor_name", String.class))
                            .inviteUserCount(row.get("invite_user_count", Integer.class))
                            .acceptUserCount(row.get("accept_user_count", Integer.class))
                            .day(row.get("day", String.class))
                            .imageUrl(imageUrl)
                            .build();
                }))
                .all()
                ;
    }

    public Flux<InviteClassListResponse> findInviteClassList(Long userId) {
        return this.classQuery.findInviteClassList(userId)
                .map((row, rowMetadata) -> {
                    String imageUrl = serverProperties.ImageUrl(row.get("file_name", String.class));
                    return InviteClassListResponse.builder()
                            .id(row.get("id", Long.class))
                            .title(row.get("title", String.class))
                            .location(row.get("location", String.class))
                            .professorName(row.get("professor_name", String.class))
                            .imageUrl(imageUrl)
                            .alarmType(row.get("alarm_type", String.class))
                            .build();
        }).all();
    }


}
