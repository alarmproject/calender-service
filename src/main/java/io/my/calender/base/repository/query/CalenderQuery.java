package io.my.calender.base.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CalenderQuery {
    private final DatabaseClient client;

    public DatabaseClient.GenericExecuteSpec findCalenderListResponse(Long userId, LocalDate startDate, LocalDate endDate) {
        String query = "" +
                "select " +
                "c.id " +
                ", u2.id as user_id " +
                ", c.start_time  " +
                ", c.end_time  " +
                ", class.id as class_id " +
                ", class.title as class_title " +
                ", pc.id as personal_calender_id  " +
                ", pc.title as personal_calender_title " +
                ", class.location as class_location " +
                ", pc.location as personal_calender_location " +
                ", u.name as user_name " +
                ", i.file_name as filename " +
                ", p.name as professor_name " +
                ", cju.accept as class_accept " +
                ", cju.alarm_type as class_alarm_type " +
                ", pcju.accept as personal_calender_accept " +
                ", pcju.alarm_type as personal_calender_alarm_type " +
                ", (select count(*) from class_join_user where class_id = class.id) as class_invite_count " +
                ", (select count(*) from class_join_user where class_id = class.id and accept = 1) as class_accept_count " +
                ", (select count(*) from personal_calender_join_user where personal_calender_id = pc.id) as personal_calender_invite_count " +
                ", (select count(*) from personal_calender_join_user where personal_calender_id = pc.id and accept = 1) as personal_calender_accept_count " +
                "from " +
                "calender c  " +
                "left join class_time ct on c.class_time_id = ct.id  " +
                "left join class on class.id = ct.class_id  " +
                "left join class_join_user cju on cju.class_id = class.id and cju.user_id = :userId " +
                "left join personal_calender pc on c.personal_calender_id = pc.id " +
                "left join personal_calender_join_user pcju on pcju.personal_calender_id = pc.id and pcju.user_id = :userId " +
                "left join user u on pc.user_id = u.id " +
                "left join professor p on class.professor_id = p.id " +
                "left join image i on u.image_id = i.id or p.image_id = i.id " +
                "left join user u2 on pc.user_id = u2.id or class.user_id = u2.id " +
                "where " +
                "c.start_time > :startDate " +
                "and " +
                "c.end_time < :endDate " +
                "and " +
                "(cju.accept = 1 or pcju.accept = 1)";

        return client.sql(query)
                .bind("userId", userId)
                .bind("startDate", startDate)
                .bind("endDate", endDate);
    }
}
