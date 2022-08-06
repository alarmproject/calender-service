package io.my.calender.base.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonalCalenderQuery {
    private final DatabaseClient client;

    public DatabaseClient.GenericExecuteSpec findMyPersonalCalenderList(Long userId, Long calenderId, Boolean accept, Boolean open) {

        String query = "" +
                "select " +
                "pcju.id " +
                ", pcju.accept " +
                ", pcju.content " +
                ", pcju.alarm_type as alarm_type " +
                ", pcju.content as content " +
                ", pc.id as personal_calender_id " +
                ", pc.`day` as `day` " +
                ", pc.title as title " +
                ", pc.location as location " +
                ", pc.`open` as open " +
                ", u.id as user_id " +
                ", u.name as user_name " +
                ", u.nickname as user_nickname " +
                ", u.email as user_email " +
                ", i.file_name as user_image " +
                "from " +
                "personal_calender_join_user pcju " +
                "join personal_calender pc ON pcju.personal_calender_id = pc.id " +
                "join `user` u ON pc.user_id = u.id " +
                "join image i ON i.id = u.image_id " +
                "where " +
                "pcju.user_id = :userId " +
                (accept != null ? (accept ? "and pcju.accept != 0 " : "and pcju.accept = :accept ") : "") +
                (open != null ? "and pc.`open` = :open " : "") +
                (calenderId != null && calenderId != 0 ? "and pcju.id < :calenderId " : "") +
                "limit 10";

        DatabaseClient.GenericExecuteSpec sql = this.client.sql(query);

        if (calenderId != null && calenderId != 0) sql = sql.bind("calenderId", calenderId);
        if (accept != null) sql = sql.bind("accept", accept);
        if (open != null) sql = sql.bind("open", open);

        return sql.bind("userId", userId);
    }

    public DatabaseClient.GenericExecuteSpec searchPersonalCalenderList(Long personalCalenderId, Integer perPage, String title) {
        String query = "" +
                "select " +
                "pc.id " +
                ", pc.day " +
                ", pc.title " +
                ", pc.location " +
                ", pc.`open` " +
                ", pc.reg_date_time " +
                ", pc.mod_date_time " +
                ", c.start_time " +
                ", c.end_time " +
                ", u.id as user_id " +
                ", u.name as user_name " +
                ", u.nickname as user_nickname " +
                ", u.email as user_email " +
                ", i.file_name as user_image " +
                "from " +
                "personal_calender pc " +
                "left join calender c on pc.id = c.personal_calender_id " +
                "join `user` u ON u.id = pc.user_id " +
                "join image i ON u.image_id = i.id " +
                "where " +
                (personalCalenderId != null && personalCalenderId != 0 ? "pc.id < :personalCalenderId and " : "") +
                "pc.`open` = true " +
                (title != null ? "and pc.title like concat('%', :title, '%') " : "") +
                "order by pc.id desc limit :perPage";

        DatabaseClient.GenericExecuteSpec sql = this.client.sql(query);

        if (personalCalenderId != null && personalCalenderId != 0) sql = sql.bind("personalCalenderId", personalCalenderId);
        if (title != null) sql = sql.bind("title", title);

        return sql.bind("perPage", perPage);
    }

    public DatabaseClient.GenericExecuteSpec findPersonalCalenderDetail(Long id) {
        String query = "" +
                "select " +
                "pc.id " +
                ", pc.user_id " +
                ", pc.title " +
                ", pc.content " +
                ", pc.location " +
                ", pc.`day` " +
                ", pc.`open` " +
                ", c.start_time " +
                ", c.end_time " +
                "from " +
                "personal_calender pc " +
                "join calender c on pc.id = c.personal_calender_id " +
                "where pc.id = :id";

        return this.client.sql(query).bind("id", id);
    }
}
