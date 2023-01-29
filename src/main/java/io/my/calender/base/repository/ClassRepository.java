package io.my.calender.base.repository;

import io.my.calender.base.entity.Class;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ClassRepository extends ReactiveCrudRepository<Class, Long> {
}
