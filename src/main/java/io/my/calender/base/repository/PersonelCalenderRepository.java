package io.my.calender.base.repository;

import io.my.calender.base.entity.PersonelCalender;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonelCalenderRepository extends ReactiveCrudRepository<PersonelCalender, Long> {
}
