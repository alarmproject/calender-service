package io.my.calender.active.service;

import io.my.calender.base.entity.ActiveHistory;
import io.my.calender.base.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActiveService {
    private final ClassRepository classRepository;
    private final CalenderRepository calenderRepository;
    private final ClassTimeRepository classTimeRepository;
    private final ClassJoinUserRepository classJoinUserRepository;
    private final ActiveHistoryRepository activeHistoryRepository;
    private final PersonalCalenderRepository personalCalenderRepository;
    private final PersonalCalenderJoinUserRepository personalCalenderJoinUserRepository;

    public Mono<Void> modifyClassTime(Long classTimeId) {
        return this.changeClassTime(classTimeId, "의 수업 시간이 변경되었습니다.");
    }

    public Mono<Void> removeClassTime(Long classTimeId) {
        return this.changeClassTime(classTimeId, "의 수업 일정이 삭제되었습니다.");
    }

    private Mono<Void> changeClassTime(Long classTimeId, String content) {
        AtomicReference<String> classTitle = new AtomicReference<>();
        return this.classTimeRepository.findById(classTimeId).flatMap(entity ->
                this.classRepository.findById(entity.getClassId())
        ).flatMapMany(entity -> {
            classTitle.set(entity.getTitle());
            return this.classJoinUserRepository.findAllByClassId(entity.getId());
        }).collectList().flatMapMany(list -> {
            List<ActiveHistory> entityList = new ArrayList<>();
            String message = classTitle.get() + content;

            list.forEach(entity ->
                    entityList.add(
                            ActiveHistory.builder()
                                    .content(message)
                                    .userId(entity.getUserId())
                                    .build())
            );

            return this.activeHistoryRepository.saveAll(entityList);
        }).then();
    }

    public Mono<Void> modifyCalender(Long calenderId) {
        return this.changeCalender(calenderId, "의 시간이 변경되었습니다.");
    }

    public Mono<Void> removeCalender(Long calenderId) {
        return this.changeCalender(calenderId, "의 일정이 삭제되었습니다.");
    }

    private Mono<Void> changeCalender(Long calenderId, String content) {
        AtomicReference<String> message = new AtomicReference<>("");

        return this.calenderRepository.findById(calenderId)
                .flatMapMany(entity -> {
                    if (entity.getClassTimeId() != null) {
                        return this.classTimeRepository.findById(entity.getClassTimeId())
                                .flatMap(e -> this.classRepository.findById(e.getClassId()))
                                .flatMapMany(e -> {
                                    message.set(e.getTitle() + content);
                                    return this.classJoinUserRepository.findAllByClassId(e.getId());
                                })
                                .map(e -> ActiveHistory.builder().userId(e.getUserId()).content(message.get()).build());
                    } else {
                        return this.personalCalenderRepository.findById(entity.getPersonalCalenderId())
                                .flatMapMany(e -> {
                                    message.set(e.getTitle() + content);
                                    return this.personalCalenderJoinUserRepository.findAllByPersonalCalenderId(e.getId());
                                })
                                .map(e -> ActiveHistory.builder().userId(e.getUserId()).content(message.get()).build());
                    }
                })
                .collectList()
                .flatMapMany(activeHistoryRepository::saveAll)
                .then()
                ;
    }
}
