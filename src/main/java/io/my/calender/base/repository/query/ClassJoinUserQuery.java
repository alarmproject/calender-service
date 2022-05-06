package io.my.calender.base.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClassJoinUserQuery {
    private final DatabaseClient client;

    public DatabaseClient.GenericExecuteSpec findClassJoinUserInfo(Long id) {
        String query = "" +
                "select " +
                "cju.user_id " +
                ", cju.accept " +
                ", u.name " +
                ", u.nickname " +
                ", i.file_name " +
                "from " +
                "class_join_user cju " +
                "join `user` u on cju.user_id = u.id " +
                "left join image i on u.image_id = i.id " +
                "where cju.class_id = :id";

        return this.client.sql(query).bind("id", id);
    }
}
