package io.my.calender.base.repository.custom;

import io.my.calender.base.util.DateUtil;
import io.my.calender.calender.payload.response.CalenderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class CustomCalenderRepository {
    private final DateUtil dateUtil;
    private final DatabaseClient client;

    public Flux<CalenderListResponse> findCalenderListResponse(Long userId, LocalDate startDate, LocalDate endDate) {
        String query = "" +
                "select " +
                "c.id " +
                ", c.start_time  " +
                ", c.end_time  " +
                ", class.id as class_id " +
                ", class.title as class_title " +
                ", pc.id as personel_calender_id  " +
                ", pc.title as personel_calender_title " +
                ", class.location as class_location " +
                ", pc.location as personel_calender_location " +
                "from " +
                "calender c  " +
                "left join class_time ct on c.class_time_id = ct.id  " +
                "left join class on class.id = ct.class_id  " +
                "left join class_join_user cju on cju.class_id = class.id and cju.user_id = :userId " +
                "left join personel_calender pc on c.personel_calender_id = pc.id " +
                "left join personel_calender_join_user pcju on pcju.personel_calender_id = pc.id and pcju.user_id = :userId " +
                "where " +
                "c.start_time > :startDate " +
                "and " +
                "c.end_time < :endDate " +
                "and " +
                "(cju.accept = 1 or pcju.accept = 1)";


        return client.sql(query).bind("userId", userId)
                .bind("startDate", startDate)
                .bind("endDate", endDate).map((row, rowMetadata) -> {
                    var entity = new CalenderListResponse();
                    Long startTime = dateUtil.localDateTimeToUnixTime(row.get("start_time", LocalDateTime.class));
                    Long endTime = dateUtil.localDateTimeToUnixTime(row.get("end_time", LocalDateTime.class));
                    entity.setId(row.get("id", Long.class));
                    entity.setStartTime(startTime);
                    entity.setEndTime(endTime);
                    entity.setClassId(row.get("class_id", Long.class));
                    entity.setClassTitle(row.get("class_title", String.class));
                    entity.setClassLocation(row.get("class_location", String.class));
                    entity.setPersonelCalenderId(row.get("personel_calender_id", Long.class));
                    entity.setPersonelCalenderTitle(row.get("personel_calender_title", String.class));
                    entity.setPersonelCalenderLocation(row.get("personel_calender_location", String.class));
                    return entity;
                }).all()
                ;

    }
}
