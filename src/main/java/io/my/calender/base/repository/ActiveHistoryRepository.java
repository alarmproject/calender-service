package io.my.calender.base.repository;


import io.my.calender.base.entity.ActiveHistory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ActiveHistoryRepository extends ReactiveCrudRepository<ActiveHistory, Long> {
}
