package io.my.calender.base.repository;

import io.my.calender.base.entity.PersonalCalender;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonalCalenderRepository extends ReactiveCrudRepository<PersonalCalender, Long> {
}
