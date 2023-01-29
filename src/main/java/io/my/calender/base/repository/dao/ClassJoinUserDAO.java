package io.my.calender.base.repository.dao;

import io.my.calender._class.payload.response.ClassJoinUserInfoResponse;
import io.my.calender.base.properties.ServerProperties;
import io.my.calender.base.repository.query.ClassJoinUserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class ClassJoinUserDAO {
    private final ServerProperties serverProperties;
    private final ClassJoinUserQuery classJoinUserQuery;

    public Flux<ClassJoinUserInfoResponse> findClassJoinUserInfo(Long id) {
        return this.classJoinUserQuery.findClassJoinUserInfo(id)
            .map(((row, rowMetadata) -> {
                String imageUrl = row.get("file_name", String.class);

                if (imageUrl != null) imageUrl =
                        serverProperties.getImageUrl() +
                                serverProperties.getImagePath() +
                                imageUrl
                        ;

                return ClassJoinUserInfoResponse.builder()
                        .userId(row.get("user_id", Long.class))
                        .accept(row.get("accept", Byte.class))
                        .name(row.get("name", String.class))
                        .nickname(row.get("nickname", String.class))
                        .alarmType(row.get("alarm_type", String.class))
                        .imageUrl(imageUrl)
                        .build();
            }))
            .all();
    }

}
