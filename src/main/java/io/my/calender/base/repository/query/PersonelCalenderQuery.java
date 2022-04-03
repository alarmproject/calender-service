package io.my.calender.base.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonelCalenderQuery {
    private final DatabaseClient client;

    public DatabaseClient.GenericExecuteSpec findMyPersonelCalenderList(Long userId, Long calenderId, Boolean accept, Boolean open) {

        String query = "" +
                "select " +
                "pcju.id " +
                ", pcju.accept " +
                ", pc.id as personel_calender_id " +
                ", pc.`day` as `day` " +
                ", pc.title as title " +
                ", pc.content as content " +
                ", pc.location as location " +
                ", pc.`open` as open " +
                ", u.id as user_id " +
                ", u.name as user_name " +
                ", u.nickname as user_nickname " +
                ", u.email as user_email " +
                ", i.file_name as user_image " +
                "from " +
                "personel_calender_join_user pcju " +
                "join personel_calender pc ON pcju.personel_calender_id = pc.id " +
                "join `user` u ON pc.user_id = u.id " +
                "join image i ON i.id = u.image_id " +
                "where " +
                "pcju.user_id = :userId " +
                (accept != null ? "and pcju.accept = :accept " : "") +
                (open != null ? "and pc.`open` = :open " : "") +
                (calenderId != null && calenderId != 0 ? "and pcju.id < :calenderId " : "") +
                "limit 10";

        DatabaseClient.GenericExecuteSpec sql = this.client.sql(query);

        if (calenderId != null && calenderId != 0) sql = sql.bind("calenderId", calenderId);
        if (accept != null) sql = sql.bind("accept", accept);
        if (open != null) sql = sql.bind("open", open);

        return sql.bind("userId", userId);
    }

    public DatabaseClient.GenericExecuteSpec searchPersonelCalenderList(Long personelCalenderId, Integer perPage, String title) {
        String query = "" +
                "select " +
                "pc.id " +
                ", pc.day " +
                ", pc.title " +
                ", pc.content " +
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
                "personel_calender pc " +
                "left join calender c on pc.id = c.personel_calender_id " +
                "join `user` u ON u.id = pc.user_id " +
                "join image i ON u.image_id = i.id " +
                "where " +
                (personelCalenderId != null && personelCalenderId != 0 ? "pc.id < :personelCalenderId and " : "") +
                "pc.`open` = true " +
                (title != null ? "and pc.title like concat('%', :title, '%') " : "") +
                "order by pc.id desc limit :perPage";

        DatabaseClient.GenericExecuteSpec sql = this.client.sql(query);

        if (personelCalenderId != null && personelCalenderId != 0) sql = sql.bind("personelCalenderId", personelCalenderId);
        if (title != null) sql = sql.bind("title", title);

        return sql.bind("perPage", perPage);
    }
}
