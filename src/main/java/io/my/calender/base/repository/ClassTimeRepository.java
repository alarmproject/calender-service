package io.my.calender.base.repository;

import io.my.calender.base.entity.ClassTime;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ClassTimeRepository extends ReactiveCrudRepository<ClassTime, Long> {
}
