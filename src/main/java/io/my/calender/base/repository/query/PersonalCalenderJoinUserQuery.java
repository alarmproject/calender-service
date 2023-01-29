package io.my.calender.base.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class PersonalCalenderJoinUserQuery {
    private final DatabaseClient client;


    public DatabaseClient.GenericExecuteSpec findPersonalCalenderJoinUserInfo(Long id) {
        String query = "" +
                "select " +
                "pcju.user_id " +
                ", pcju.accept " +
                ", u.name " +
                ", u.nickname " +
                ", i.file_name " +
                "from " +
                "personal_calender_join_user pcju " +
                "join `user` u on pcju.user_id = u.id " +
                "left join image i on u.image_id = i.id " +
                "where pcju.personal_calender_id = :id";

        return this.client.sql(query).bind("id", id);
    }

    public DatabaseClient.GenericExecuteSpec findPersonalInvite(Long userId, int accept) {
        String query = "" +
                "select " +
                "pc.id " +
                ", pc.title " +
                ", pc.location " +
                ", pc.alarm_type " +
                ", pc.`day` " +
                ", c.start_time " +
                ", c.end_time " +
                ", u.name " +
                ", i.file_name " +
                "from " +
                "personal_calender_join_user pcju " +
                "join personal_calender pc on pcju .personal_calender_id = pc.id " +
                "join `user` u on pc.user_id = u.id " +
                "left join calender c on pc.id = c.personal_calender_id " +
                "left join image i on u.image_id = i.id " +
                "where pcju.user_id = :id and pcju.accept = :accept";

        return this.client.sql(query).bind("id", userId).bind("accept", accept);
    }
}
