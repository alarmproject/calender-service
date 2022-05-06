package io.my.calender.base.repository.dao;


import io.my.calender.base.properties.ServerProperties;
import io.my.calender.base.repository.query.PersonelCalenderJoinUserQuery;
import io.my.calender.personel.payload.response.PersonelCalenderJoinUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class PersonelCalenderJoinUserDAO {
    private final ServerProperties serverProperties;
    private final PersonelCalenderJoinUserQuery personelCalenderJoinUserQuery;

    public Flux<PersonelCalenderJoinUserInfoResponse> findPersonelCalenderJoinUserInfo(Long id) {
        return this.personelCalenderJoinUserQuery.findPersonelCalenderJoinUserInfo(id)
                .map((row, rowMetadata) -> {
                    String imageUrl = row.get("file_name", String.class);
                    if (imageUrl != null) imageUrl = serverProperties.getImageUrl() + serverProperties.getImagePath() + imageUrl;

                    return PersonelCalenderJoinUserInfoResponse.builder()
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
