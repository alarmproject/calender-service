package io.my.calender.base.repository;

import io.my.calender.base.entity.Calender;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CalenderRepository extends ReactiveCrudRepository<Calender, Long> {
    Flux<Calender> findAllByClassTimeId(Long id);

    Mono<Calender> findByPersonalCalenderId(Long id);
}
