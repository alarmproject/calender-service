package io.my.calender.base.repository;

import io.my.calender.base.entity.PersonalCalenderJoinUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonalCalenderJoinUserRepository extends ReactiveCrudRepository<PersonalCalenderJoinUser, Long> {
    Mono<PersonalCalenderJoinUser> findByUserIdAndPersonalCalenderId(Long userId, Long personalCalenderId);
    Flux<PersonalCalenderJoinUser> findAllByPersonalCalenderId(Long personalCalenderId);

    Mono<Integer> countByUserIdAndAccept(Long userId, int accept);
}
