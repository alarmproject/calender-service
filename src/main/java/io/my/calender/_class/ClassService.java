package io.my.calender._class;

import io.my.calender._class.payload.request.*;
import io.my.calender._class.payload.response.ClassJoinUserInfoResponse;
import io.my.calender._class.payload.response.InviteClassListResponse;
import io.my.calender._class.payload.response.ClassTimeListResponse;
import io.my.calender._class.payload.response.SearchClassResponse;
import io.my.calender.base.context.JwtContextHolder;
import io.my.calender.base.entity.Calender;
import io.my.calender.base.entity.Class;
import io.my.calender.base.entity.ClassJoinUser;
import io.my.calender.base.entity.ClassTime;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.base.repository.*;
import io.my.calender.base.repository.dao.ClassDAO;
import io.my.calender.base.repository.dao.ClassJoinUserDAO;
import io.my.calender.base.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final DateUtil dateUtil;
    private final ClassDAO classDAO;
    private final ClassJoinUserDAO classJoinUserDAO;

    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final CalenderRepository calenderRepository;
    private final ClassTimeRepository classTimeRepository;
    private final ClassJoinUserRepository classJoinUserRepository;

    public Mono<BaseExtentionResponse<Long>> createClass(CreateClassRequest requestBody) {
        AtomicLong atomicClassId = new AtomicLong();

        Class _class = Class.builder()
                .title(requestBody.getTitle())
                .collegeId(requestBody.getCollegeId())
                .location(requestBody.getLocation())
                .startDate(
                        this.dateUtil.unixTimeToLocalDateTime(requestBody.getStartDate()).toLocalDate()
                )
                .endDate(
                        this.dateUtil.unixTimeToLocalDateTime(requestBody.getEndDate()).toLocalDate()
                )
                .professorId(requestBody.getProfessorId())
                .alarmType(requestBody.getAlarmType())
                .build();

        return JwtContextHolder.getMonoUserId().flatMap(userId -> {
            _class.setUserId(userId);
            return this.classRepository.save(_class);
        })
        .flatMapMany(entity -> {
            atomicClassId.set(entity.getId());
            List<ClassTime> saveClassTimeList = new ArrayList<>();
            requestBody.getClassTimeList().forEach(classTimeRequest -> {
                ClassTime classTime = ClassTime.builder()
                        .classId(entity.getId())
                        .startHour(classTimeRequest.getStartHour())
                        .startMinutes(classTimeRequest.getStartMinutes())
                        .endHour(classTimeRequest.getEndHour())
                        .endMinutes(classTimeRequest.getEndMinutes())
                        .day(classTimeRequest.getDay())
                        .build();
                saveClassTimeList.add(classTime);
            });
            return this.classTimeRepository.saveAll(saveClassTimeList);
        })
        .map(entity -> {
            List<Calender> calenderEntityList = new ArrayList<>();

            LocalTime startTime = LocalTime.of(entity.getStartHour(), entity.getStartMinutes());
            LocalTime endTime = LocalTime.of(entity.getEndHour(), entity.getEndMinutes());
            DayOfWeek dayOfWeek = this.dateUtil.getDayOfWeek(entity.getDay());

            LocalDate date = _class.getStartDate();

            while (date != _class.getEndDate() && date.isBefore(_class.getEndDate())) {
                if (date.getDayOfWeek() == dayOfWeek) {
                    Calender calender = Calender.builder()
                            .classTimeId(entity.getId())
                            .startTime(LocalDateTime.of(date, startTime))
                            .endTime(LocalDateTime.of(date, endTime))
                            .build();

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
        .collectList().map(list -> new BaseExtentionResponse<>(atomicClassId.get()))
        ;
    }

    public Mono<BaseResponse> inviteUser(InviteClassRequeset requestBody) {
        List<ClassJoinUser> entityList = new ArrayList<>();

        requestBody.getUserList().forEach(id -> {
            ClassJoinUser entity = ClassJoinUser.builder()
                    .classId(requestBody.getClassId())
                    .userId(id)
                    .build();
            entityList.add(entity);
        });

        return this.classJoinUserRepository.saveAll(entityList)
                .collectList()
                .map(list -> new BaseResponse())
                ;
    }

    public Mono<BaseResponse> joinClass(JoinClassRequest requestBody) {
        AtomicReference<Long> userId = new AtomicReference<>(0L);
        return JwtContextHolder.getMonoUserId().flatMap(id -> {
            userId.set(id);
            return this.classJoinUserRepository.findByUserIdAndClassId(id, requestBody.getClassId());
        })
        .flatMap(entity -> {
            entity.setAccept((byte) 1);
            return this.classJoinUserRepository.save(entity);
        })
        .switchIfEmpty(saveClassJoinUser(requestBody, userId.get()))
        .map(entity -> new BaseResponse())
        ;
    }

    private Mono<ClassJoinUser> saveClassJoinUser(JoinClassRequest requestBody, Long userId) {
        ClassJoinUser entity = ClassJoinUser.builder()
                .classId(requestBody.getClassId())
                .userId(userId)
                .content(requestBody.getContent())
                .alarmType(requestBody.getAlarmType())
                .accept((byte)1)
                .build();
        return this.classJoinUserRepository.save(entity);
    }

    public Mono<BaseResponse> acceptClass(AcceptClassRequest requestBody) {
        return JwtContextHolder.getMonoUserId().flatMap(userId ->
                classJoinUserRepository.findByUserIdAndClassId(userId, requestBody.getClassId()))
                .flatMap(entity -> {
                    entity.setAccept(requestBody.getAccept() ? (byte) 1 : (byte) 0);
                    return classJoinUserRepository.save(entity);
                })
        .map(o -> new BaseResponse());
    }

    public Mono<BaseResponse> modifyClassInfo(ModifyClassInfoRequest requestBody) {
        return classRepository.findById(requestBody.getId())
                .flatMap(entity -> {
                    entity.setTitle(requestBody.getTitle());
                    entity.setLocation(requestBody.getLocation());
                    entity.setProfessorId(requestBody.getProfessorId());
                    entity.setAlarmType(requestBody.getAlarmType());
                    return classRepository.save(entity);
                })
                .map(entity -> new BaseResponse())
                ;
    }

    public Mono<BaseExtentionResponse<List<SearchClassResponse>>> searchClasses(Long classId, Integer perPage, String title) {
        return JwtContextHolder.getMonoUserId().flatMap(userRepository::findById)
                .flatMap(user -> classDAO.searchClasses(classId, user.getCollegeId(), title, perPage))
                .map(BaseExtentionResponse::new)
        ;
    }

    public Mono<BaseExtentionResponse<List<InviteClassListResponse>>> findInviteClassList() {
        return JwtContextHolder.getMonoUserId()
                .flatMapMany(classDAO::findInviteClassList)
                .flatMap(response ->
                    classTimeRepository.findAllByClassId(response.getId())
                        .map(entity ->
                                ClassTimeListResponse.builder()
                                        .day(entity.getDay())
                                        .startHour(entity.getStartHour())
                                        .startMinutes(entity.getStartMinutes())
                                        .endHour(entity.getEndHour())
                                        .endMinutes(entity.getEndMinutes())
                                        .build())
                        .collectList()
                        .map(list -> {
                            response.setClassTimeList(list);
                            return response;
                        })
                )
                .collectList()
                .map(BaseExtentionResponse::new)
                ;
    }

    public Mono<BaseExtentionResponse<ClassDetailResponse>> findClassDetail(Long id) {
        AtomicLong atomicUserId = new AtomicLong();
        return JwtContextHolder.getMonoUserId().flatMap(userId -> {
            atomicUserId.set(userId);
            return classDAO.findClassDetail(id);
        }).flatMap(classDetailResponse ->
            classJoinUserDAO.findClassJoinUserInfo(id)
                .collectList()
                .map(list -> {
                    Integer acceptUserCount = 0;
                    boolean isAccept = false;
                    for (ClassJoinUserInfoResponse joinUser : list) {
                        if (joinUser.getAccept() == 1) {
                            acceptUserCount++;
                        }

                        if (joinUser.getUserId() == atomicUserId.get()) {
                            isAccept = joinUser.getAccept() == 1;
                        }
                    }
                    classDetailResponse.setIsAccept(isAccept);
                    classDetailResponse.setAcceptUserCount(acceptUserCount);
                    classDetailResponse.setInviteUserCount(list.size());
                    classDetailResponse.setJoinUserList(list);
                    return new BaseExtentionResponse<>(classDetailResponse);
                })
        );
    }

    public Mono<BaseExtentionResponse<Integer>> findClassInviteCount() {
        return JwtContextHolder.getMonoUserId().flatMap(userId ->
                classJoinUserRepository.countByUserIdAndAccept(userId, 2))
                .map(BaseExtentionResponse::new);
    }
}
