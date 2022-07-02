package io.my.calender.base.repository.dao;


import io.my.calender.base.properties.ServerProperties;
import io.my.calender.base.repository.query.PersonalCalenderJoinUserQuery;
import io.my.calender.personal.payload.response.PersonalCalenderJoinUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class PersonalCalenderJoinUserDAO {
    private final ServerProperties serverProperties;
    private final PersonalCalenderJoinUserQuery personalCalenderJoinUserQuery;

    public Flux<PersonalCalenderJoinUserInfoResponse> findPersonalCalenderJoinUserInfo(Long id) {
        return this.personalCalenderJoinUserQuery.findPersonelCalenderJoinUserInfo(id)
                .map((row, rowMetadata) -> {
                    String imageUrl = row.get("file_name", String.class);
                    if (imageUrl != null) imageUrl = serverProperties.getImageUrl() + serverProperties.getImagePath() + imageUrl;

                    return PersonalCalenderJoinUserInfoResponse.builder()
                            .userId(row.get("user_id", Long.class))
                            .accept(row.get("accept", Byte.class))
                            .name(row.get("name", String.class))
                            .nickname(row.get("nickname", String.class))
                            .imageUrl(imageUrl)
                            .build();
                })
                .all();
    }
}
