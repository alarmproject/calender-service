package io.my.calender.base.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClassQuery {
    private final DatabaseClient client;

    public DatabaseClient.GenericExecuteSpec searchClasses(Long classId, Long collegeId, String title, Integer perPage) {
        String query = "" +
                "select " +
                "c.id " +
                ", c.start_date " +
                ", c.end_date " +
                ", c.title " +
                ", c.content " +
                ", c.location " +
                ", p.name as professor_name " +
                ", i.file_name " +
                ", (select count(*) from class_join_user where class_id = c.id) as invite_user_count " +
                ", (select count(*) from class_join_user where class_id = c.id and accept = 1) as accept_user_count " +
                ", GROUP_CONCAT (ct.`day`) as day " +
                "from " +
                "class c " +
                "left join professor p ON c.professor_id = p.id " +
                "left join image i on p.image_id = i.id " +
                "left outer join class_time ct on c.id = ct.class_id " +
                "where " +
                (
                    classId != null && classId != 0 ? "c.id < :id and " : ""
                ) +
                "c.college_id = :collegeId and " +
                "c.title like CONCAT('%', :title, '%') " +
                "group by c.id order by c.id desc limit :perPage"
                ;

        DatabaseClient.GenericExecuteSpec sql = this.client.sql(query);

        if (classId != null && classId != 0) sql = sql.bind("id", classId);

        return sql.bind("collegeId", collegeId)
                .bind("title", title)
                .bind("perPage", perPage)
                ;
    }

    public DatabaseClient.GenericExecuteSpec findInviteClassList(Long userId) {

        String query = "" +
                "SELECT " +
                "c.id " +
                ", c.title " +
                ", c.content " +
                ", c.location " +
                ", p.name as professor_name " +
                ", i.file_name " +
                "from " +
                "class_join_user cju " +
                "join class c on cju.class_id = c.id " +
                "left join professor p on c.professor_id = p.id " +
                "left join image i on p.image_id = i.id " +
                "where cju.user_id = :userId and cju.accept = 0";

        return this.client.sql(query).bind("userId", userId);
    }
}
