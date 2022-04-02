package io.my.calender._class;

import io.my.calender.base.payload.BaseResponse;
import io.my.calender.base.repository.CalenderRepository;
import io.my.calender.base.repository.ClassTimeRepository;
import io.my.calender.base.util.DateUtil;
import io.my.calender._class.payload.request.ModifyClassTimeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ClassTimeService {
    private final DateUtil dateUtil;
    private final CalenderRepository calenderRepository;
    private final ClassTimeRepository classTimeRepository;

    public Mono<BaseResponse> modifyClassTime(ModifyClassTimeRequest requestBody) {
        return this.classTimeRepository.findById(requestBody.getId())
                .flatMap(entity -> {
                    entity.setStartTime(requestBody.getStartTime());
                    entity.setEndTime(requestBody.getEndTime());
                    entity.setDay(requestBody.getDay());
                    return this.classTimeRepository.save(entity);
                })
                .flatMapMany(entity -> calenderRepository.findAllByClassTimeId(requestBody.getId()))
                .map(entity -> {
                    DayOfWeek dayOfWeek = dateUtil.getDayOfWeek(requestBody.getDay());
                    Integer diffrentDay = dateUtil.diffrentDay(
                            dayOfWeek,
                            entity.getStartTime().getDayOfWeek()
                    );

                    LocalDate changeDate = entity.getStartTime()
                            .toLocalDate()
                            .plusDays(diffrentDay);

                    LocalDateTime startTime = LocalDateTime.of(changeDate, LocalTime.of(requestBody.getStartTime(), 0));
                    LocalDateTime endTime = LocalDateTime.of(changeDate, LocalTime.of(requestBody.getEndTime(), 0));

                    entity.setStartTime(startTime);
                    entity.setEndTime(endTime);
                    return entity;
                })
                .collectList()
                .flatMapMany(calenderRepository::saveAll)
                .collectList()
                .map(list -> new BaseResponse());
    }

    public Mono<BaseResponse> removeClassTime(Long id) {
        return this.classTimeRepository.deleteById(id).map(o -> new BaseResponse());
    }
}
