package io.my.calender.base.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClassQuery {
    private final DatabaseClient client;

    public DatabaseClient.GenericExecuteSpec searchClasses(Long classId, Long collegeId, String title, Integer perPage) {

        String query = ""
                ;

        DatabaseClient.GenericExecuteSpec sql = this.client.sql(query);

        if (classId != null && classId != 0) sql = sql.bind("classId", classId);

        return sql.bind("collegeId", collegeId)
                .bind("title", title)
                .bind("perPage", perPage)
                ;
    }
}
