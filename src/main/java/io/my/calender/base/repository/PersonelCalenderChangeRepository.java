package io.my.calender.base.repository;

import io.my.calender.base.entity.PersonelCalenderChange;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonelCalenderChangeRepository extends ReactiveCrudRepository<PersonelCalenderChange, Long> {
}
