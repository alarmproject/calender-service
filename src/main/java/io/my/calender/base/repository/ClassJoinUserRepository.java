package io.my.calender.base.repository;

import io.my.calender.base.entity.ClassJoinUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClassJoinUserRepository extends ReactiveCrudRepository<ClassJoinUser, Long> {
    Mono<ClassJoinUser> findByUserIdAndClassId(Long userId, Long classId);
    Flux<ClassJoinUser> findAllByClassId(Long classId);
}
