package io.my.calender.calender;

import io.my.calender.base.context.JwtContextHolder;
import io.my.calender.base.entity.Calender;
import io.my.calender.base.entity.Class;
import io.my.calender.base.entity.ClassJoinUser;
import io.my.calender.base.entity.ClassTime;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.base.repository.CalenderRepository;
import io.my.calender.base.repository.ClassJoinUserRepository;
import io.my.calender.base.repository.ClassRepository;
import io.my.calender.base.repository.ClassTimeRepository;
import io.my.calender.base.util.DateUtil;
import io.my.calender.calender.payload.request._class.CreateClassRequest;
import io.my.calender.calender.payload.request._class.InviteClassRequeset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final DateUtil dateUtil;
    private final ClassRepository classRepository;
    private final CalenderRepository calenderRepository;
    private final ClassTimeRepository classTimeRepository;
    private final ClassJoinUserRepository classJoinUserRepository;

    public Mono<BaseResponse> createClass(CreateClassRequest requestBody) {
        Class _class = new Class();
        _class.setContent(requestBody.getContent());
        _class.setTitle(requestBody.getTitle());
        _class.setCollegeId(requestBody.getCollegeId());
        _class.setLocation(requestBody.getLocation());
        _class.setStartDate(
                this.dateUtil.unixTimeToLocalDateTime(requestBody.getStartDate()).toLocalDate());
        _class.setEndDate(
                this.dateUtil.unixTimeToLocalDateTime(requestBody.getEndDate()).toLocalDate());

        return JwtContextHolder.getMonoUserId().flatMap(userId -> {
            _class.setUserId(userId);
            return this.classRepository.save(_class);
        })
        .flatMapMany(entity -> {
            List<ClassTime> saveClassTimeList = new ArrayList<>();
            requestBody.getClassTimeList().forEach(classTimeRequest -> {
                ClassTime classTime = new ClassTime();
                classTime.setClassId(entity.getId());
                classTime.setStartTime(classTimeRequest.getStartTime());
                classTime.setEndTime(classTimeRequest.getEndTime());
                classTime.setDay(classTimeRequest.getDay());
                saveClassTimeList.add(classTime);
            });
            return this.classTimeRepository.saveAll(saveClassTimeList);
        })
        .map(entity -> {
            List<Calender> calenderEntityList = new ArrayList<>();

            LocalTime startTime = LocalTime.of(entity.getStartTime(), 0);
            LocalTime endTime = LocalTime.of(entity.getEndTime(), 0);
            DayOfWeek dayOfWeek = this.dateUtil.getDayOfWeek(entity.getDay());

            LocalDate date = _class.getStartDate();

            while (date != _class.getEndDate() && date.isBefore(_class.getEndDate())) {
                if (date.getDayOfWeek() == dayOfWeek) {
                    Calender calender = new Calender();

                    calender.setClassTimeId(entity.getId());
                    calender.setStartTime(LocalDateTime.of(date, startTime));
                    calender.setEndTime(LocalDateTime.of(date, endTime));

                    calenderEntityList.add(calender);
                    date = date.plusDays(7);
                } else {
                    date = date.plusDays(1);
                }
            }
            return calenderEntityList;
        })
        .collectList()
        .flatMapMany(calenderList -> {
            List<Calender> entityList = new ArrayList<>();
            calenderList.forEach(entityList::addAll);
            return this.calenderRepository.saveAll(entityList);
        })
        .collectList().map(list -> new BaseResponse())
        ;
    }

    public Mono<BaseResponse> inviteUser(InviteClassRequeset requestBody) {
        List<ClassJoinUser> entityList = new ArrayList<>();

        requestBody.getUserList().forEach(id -> {
            ClassJoinUser entity = new ClassJoinUser();
            entity.setClassId(requestBody.getClassId());
            entity.setUserId(id);
            entityList.add(entity);
        });

        return this.classJoinUserRepository.saveAll(entityList)
                .collectList()
                .map(list -> new BaseResponse())
                ;
    }

    public Mono<BaseResponse> joinClass(Long classId) {
        AtomicReference<Long> userId = new AtomicReference<>(0L);
        return JwtContextHolder.getMonoUserId().flatMap(id -> {
            userId.set(id);
            return this.classJoinUserRepository.findByUserIdAndClassId(id, classId);
        })
        .flatMap(entity -> {
            entity.setAccept(Boolean.TRUE);
            return this.classJoinUserRepository.save(entity);
        })
        .switchIfEmpty(saveClassJoinUser(classId, userId.get()))
        .map(entity -> new BaseResponse())
        ;
    }

    private Mono<ClassJoinUser> saveClassJoinUser(Long classId, Long userId) {
        ClassJoinUser entity = new ClassJoinUser();
        entity.setClassId(classId);
        entity.setUserId(userId);
        entity.setAccept(Boolean.TRUE);
        return this.classJoinUserRepository.save(entity);
    }


}
