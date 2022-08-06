package io.my.calender.base.repository.dao;

import io.my.calender._class.payload.request.ClassDetailResponse;
import io.my.calender._class.payload.response.ClassTimeListResponse;
import io.my.calender.base.properties.ServerProperties;
import io.my.calender.base.repository.query.ClassQuery;
import io.my.calender.base.util.DateUtil;
import io.my.calender._class.payload.response.InviteClassListResponse;
import io.my.calender._class.payload.response.SearchClassResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@RequiredArgsConstructor
public class ClassDAO {
    private final DateUtil dateUtil;
    private final ClassQuery classQuery;
    private final ServerProperties serverProperties;

    public Mono<List<SearchClassResponse>> searchClasses(Long classId, Long collegeId, String title, Integer perPage) {
        return this.classQuery.searchClasses(classId, collegeId, title, perPage)
                .map(((row, rowMetadata) -> {
                    String imageUrl = row.get("file_name", String.class);

                    if (imageUrl != null) imageUrl =
                            serverProperties.getImageUrl() +
                            serverProperties.getImagePath() +
                            imageUrl

                            ;
                    List<ClassTimeListResponse> classTimeList = new ArrayList<>();

                    classTimeList.add(
                            ClassTimeListResponse.builder()
                                    .day(row.get("day", String.class))
                                    .startHour(row.get("start_hour", Integer.class))
                                    .startMinutes(row.get("start_minutes", Integer.class))
                                    .endHour(row.get("end_hour", Integer.class))
                                    .endMinutes(row.get("end_minutes", Integer.class))
                                    .build()
                    );

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
                            .classTimeList(classTimeList)
                            .imageUrl(imageUrl)
                            .build();
                }))
                .all().collectList()
                .map(list -> {
                    AtomicLong atomicClassId = new AtomicLong();
                    atomicClassId.set(0L);

                    for (int index = list.size() - 1; index>=0; index--) {
                        SearchClassResponse response = list.get(index);
                        if (atomicClassId.get() != response.getId()) {
                            atomicClassId.set(response.getId());
                        } else {
                            response.getClassTimeList().addAll(
                                    list.get(index + 1).getClassTimeList()
                            );
                            list.remove(index + 1);
                        }
                    }

                    return list;
                })
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


    public Mono<ClassDetailResponse> findClassDetail(Long id, Long userId) {
        return this.classQuery.findClassDetail(id, userId)
                .map(((row, rowMetadata) -> {

                    List<ClassTimeListResponse> list = new ArrayList<>();
                    list.add(
                            ClassTimeListResponse.builder()
                                    .day(row.get("day", String.class))
                                    .startHour(row.get("start_hour", Integer.class))
                                    .startMinutes(row.get("start_minutes", Integer.class))
                                    .endHour(row.get("end_hour", Integer.class))
                                    .endMinutes(row.get("end_minutes", Integer.class))
                                    .build()
                    );

                    return ClassDetailResponse.builder()
                            .id(row.get("id", Long.class))
                            .userId(row.get("user_id", Long.class))
                            .title(row.get("title", String.class))
                            .startDate(dateUtil.localDateTimeToUnixTime(
                                    row.get("start_date", LocalDateTime.class)
                            ))
                            .endDate(dateUtil.localDateTimeToUnixTime(
                                    row.get("end_date", LocalDateTime.class)
                            ))
                            .content(row.get("content", String.class))
                            .location(row.get("location", String.class))
                            .professorId(row.get("professor_id", Long.class))
                            .professorName(row.get("professor_name", String.class))
                            .classTimeList(list)
                            .build();
                })).all().collectList()
                .map(list -> {
                    if (list.isEmpty()) return ClassDetailResponse.builder().build();
                    ClassDetailResponse response = list.get(0);

                    list.remove(0);

                    for (ClassDetailResponse e : list) {
                        response.getClassTimeList().addAll(e.getClassTimeList());
                    }
                    return response;
                })
                ;
    }
}
