package io.my.calender.base.repository;

import io.my.calender.base.entity.ClassTime;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClassTimeRepository extends ReactiveCrudRepository<ClassTime, Long> {
    Flux<ClassTime> findAllByClassId(Long classId);

    Mono<Void> deleteByClassId(Long id);
}
