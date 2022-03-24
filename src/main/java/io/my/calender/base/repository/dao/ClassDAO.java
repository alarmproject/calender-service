package io.my.calender.base.repository.dao;

import io.my.calender.base.repository.query.ClassQuery;
import io.my.calender.calender._class.payload.response.SearchClassResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class ClassDAO {
    private final ClassQuery classQuery;

    public Flux<SearchClassResponse> searchClasses(Long classId, Long collegeId, String title, Integer perPage) {
        return this.classQuery.searchClasses(classId, collegeId, title, perPage)
                .map(((row, rowMetadata) -> {


                    return SearchClassResponse.builder().build();
                }))
                .all()
                ;
    }


}
