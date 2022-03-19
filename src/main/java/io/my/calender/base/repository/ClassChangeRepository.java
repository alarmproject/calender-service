package io.my.calender.base.repository;

import io.my.calender.base.entity.ClassChange;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ClassChangeRepository extends ReactiveCrudRepository<ClassChange, Long> {
}
