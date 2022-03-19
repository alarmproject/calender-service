package io.my.calender.base.repository;

import io.my.calender.base.entity.Calender;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CalenderRepository extends ReactiveCrudRepository<Calender, Long> {
}
