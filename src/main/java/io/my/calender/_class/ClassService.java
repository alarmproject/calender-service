package io.my.calender._class;

import io.my.calender._class.payload.request.*;
import io.my.calender._class.payload.response.ClassJoinUserInfoResponse;
import io.my.calender._class.payload.response.InviteClassListResponse;
import io.my.calender._class.payload.response.ClassTimeListResponse;
import io.my.calender._class.payload.response.SearchClassResponse;
import io.my.calender.base.context.JwtContextHolder;
import io.my.calender.base.entity.*;
import io.my.calender.base.entity.Class;
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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
    private final ActiveHistoryRepository activeHistoryRepository;

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
        .flatMap(entity -> {
            atomicClassId.set(entity.getId());
            ClassJoinUser e = ClassJoinUser.builder()
                    .classId(atomicClassId.get())
                    .alarmType(requestBody.getAlarmType())
                    .userId(entity.getUserId())
                    .content(requestBody.getContent())
                    .accept((byte)1)
                    .build();
            return classJoinUserRepository.save(e);
        })
        .flatMapMany(entity -> {
            List<ClassTime> saveClassTimeList = new ArrayList<>();
            requestBody.getClassTimeList().forEach(classTimeRequest -> {
                ClassTime classTime = ClassTime.builder()
                        .classId(atomicClassId.get())
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
        .map(entity -> makeCalenderByClassTime(entity, _class))
        .collectList()
        .flatMapMany(calenderList -> {
            List<Calender> entityList = new ArrayList<>();
            calenderList.forEach(entityList::addAll);
            return this.calenderRepository.saveAll(entityList);
        })
        .collectList().map(list -> new BaseExtentionResponse<>(atomicClassId.get()))
        ;
    }

    private List<Calender> makeCalenderByClassTime(ClassTime entity, Class _class) {
        List<Calender> calenderEntityList = new ArrayList<>();
        LocalDate date = _class.getStartDate();
        LocalTime startTime = LocalTime.of(entity.getStartHour(), entity.getStartMinutes());
        LocalTime endTime = LocalTime.of(entity.getEndHour(), entity.getEndMinutes());
        DayOfWeek dayOfWeek = this.dateUtil.getDayOfWeek(entity.getDay());

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
        AtomicReference<Long> userId = new AtomicReference<>();
        return JwtContextHolder.getMonoUserId().flatMap(id -> {
            userId.set(id);
            return this.classJoinUserRepository.findByUserIdAndClassId(id, requestBody.getClassId());
        })
        .flatMap(entity -> {
            entity.setAccept((byte) 1);
            return this.classJoinUserRepository.save(entity);
        })
        .switchIfEmpty(Mono.defer(() -> saveClassJoinUser(requestBody, userId.get())))
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
                    entity.setAlarmType(requestBody.getAlarmType());
                    if (requestBody.getContent() != null)
                        entity.setContent(requestBody.getContent());
                    return classJoinUserRepository.save(entity);
                })
        .map(o -> new BaseResponse());
    }

    public Mono<BaseResponse> modifyClassInfo(ModifyClassInfoRequest requestBody) {

        return JwtContextHolder.getMonoUserId()
                .flatMap(userId -> classJoinUserRepository.findByUserIdAndClassId(userId, requestBody.getId()))
                .flatMap(entity -> classJoinUserRepository.save(entity.toBuilder().content(requestBody.getContent()).build()))
                .flatMap(entity -> {
                    if (requestBody.getIsChangeActiveHistory()) {
                        return classJoinUserRepository.findAllByClassId(requestBody.getId())
                                .map(ClassJoinUser::getUserId)
                                .flatMap(userId -> {
                                    ActiveHistory activeHistory = ActiveHistory.builder().userId(userId).content("<b>" + requestBody.getTitle() + "</b>의 수업이 변경되었습니다.").build();
                                    return activeHistoryRepository.save(activeHistory);
                                }).collectList().thenReturn(Optional.empty());
                    }
                    return Mono.just(Optional.empty());
                })
                .flatMap(optional -> classRepository.findById(requestBody.getId()))
                .flatMap(entity -> {
                    entity.setTitle(requestBody.getTitle());
                    entity.setLocation(requestBody.getLocation());
                    entity.setProfessorId(requestBody.getProfessorId());
                    entity.setAlarmType(requestBody.getAlarmType());
                    return classRepository.save(entity);
                })
                .flatMap(entity -> classTimeRepository.deleteByClassId(entity.getId()).thenReturn(Optional.empty()))
                .flatMapMany(empty -> {
                    List<ClassTime> classTimeList = requestBody.getClassTimeList().stream().map(classTimeRequest ->
                            ClassTime.builder()
                                .classId(requestBody.getId())
                                .startHour(classTimeRequest.getStartHour())
                                .startMinutes(classTimeRequest.getStartMinutes())
                                .endHour(classTimeRequest.getEndHour())
                                .endMinutes(classTimeRequest.getEndMinutes())
                                .day(classTimeRequest.getDay())
                                .build())
                            .collect(Collectors.toList());
                    return classTimeRepository.saveAll(classTimeList);
                })
                .map(entity -> {
                    Class _class = Class.builder()
                            .title(requestBody.getTitle())
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
                    return this.makeCalenderByClassTime(entity, _class);
                })
                .collectList()
                .flatMapMany(list -> {
                    List<Calender> calenderList = new ArrayList<>();
                    list.forEach(calenderList::addAll);
                    return calenderRepository.saveAll(calenderList);
                })
                .collectList()
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
            return classDAO.findClassDetail(id, userId);
        }).flatMap(classDetailResponse ->
            classJoinUserDAO.findClassJoinUserInfo(id)
                .collectList()
                .map(list -> {
                    ClassDetailResponse responseBody = classDetailResponse;
                    Integer acceptUserCount = 0;
                    boolean isAccept = false;
                    for (ClassJoinUserInfoResponse joinUser : list) {
                        if (joinUser.getAccept() == 1) {
                            acceptUserCount++;
                        }

                        if (joinUser.getUserId() == atomicUserId.get()) {
                            isAccept = joinUser.getAccept() == 1;
                        }

                        if (joinUser.getUserId() == atomicUserId.get()) {
                            responseBody = classDetailResponse.toBuilder().alarmType(joinUser.getAlarmType()).build();
                        }
                    }

                    responseBody = responseBody.toBuilder().isAccept(isAccept)
                            .acceptUserCount(acceptUserCount)
                            .inviteUserCount(list.size())
                            .joinUserList(list).build();
                    return new BaseExtentionResponse<>(responseBody);
                }
            )
        );
    }

    public Mono<BaseExtentionResponse<Integer>> findClassInviteCount() {
        return JwtContextHolder.getMonoUserId().flatMap(userId ->
                classJoinUserRepository.countByUserIdAndAccept(userId, 2))
                .map(BaseExtentionResponse::new);
    }

    public Mono<BaseResponse> removeClass(Long id) {
        return classRepository.deleteById(id).thenReturn(new BaseResponse());
    }
}
