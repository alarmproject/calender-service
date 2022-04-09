package io.my.calender.base.repository;

import io.my.calender.base.entity.PersonelCalenderJoinUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonelCalenderJoinUserRepository extends ReactiveCrudRepository<PersonelCalenderJoinUser, Long> {
    Mono<PersonelCalenderJoinUser> findByUserIdAndPersonelCalenderId(Long userId, Long personelCalenderId);
    Flux<PersonelCalenderJoinUser> findAllByPersonelCalenderId(Long personelCalenderId);
}
