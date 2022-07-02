package io.my.calender.base.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;


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
}
