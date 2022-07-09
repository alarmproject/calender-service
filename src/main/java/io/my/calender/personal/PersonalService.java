package io.my.calender.personal;

import io.my.calender.base.context.JwtContextHolder;
import io.my.calender.base.entity.Calender;
import io.my.calender.base.entity.PersonalCalender;
import io.my.calender.base.entity.PersonalCalenderJoinUser;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.base.repository.CalenderRepository;
import io.my.calender.base.repository.PersonalCalenderJoinUserRepository;
import io.my.calender.base.repository.PersonalCalenderRepository;
import io.my.calender.base.repository.dao.PersonalCalenderDAO;
import io.my.calender.base.repository.dao.PersonalCalenderJoinUserDAO;
import io.my.calender.base.util.DateUtil;
import io.my.calender.personal.payload.request.*;
import io.my.calender.personal.payload.response.MyPersonalCalenderListResponse;
import io.my.calender.personal.payload.response.PersonalCalenderInviteResponse;
import io.my.calender.personal.payload.response.PersonalCalenderJoinUserInfoResponse;
import io.my.calender.personal.payload.response.SearchPersonalCalenderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class PersonalService {
    private final DateUtil dateUtil;
    private final CalenderRepository calenderRepository;
    private final PersonalCalenderRepository personalCalenderRepository;
    private final PersonalCalenderJoinUserRepository personalCalenderJoinUserRepository;

    private final PersonalCalenderDAO personalCalenderDAO;
    private final PersonalCalenderJoinUserDAO personalCalenderJoinUserDAO;


    public Mono<BaseExtentionResponse<Long>> createPersonalCalender(
            CreatePersonalRequest requestBody) {
        AtomicReference<Long> personalCalenderId = new AtomicReference<>();

        LocalDateTime startTime = dateUtil.unixTimeToLocalDateTime(requestBody.getStartTime());
        LocalDateTime endTime = dateUtil.unixTimeToLocalDateTime(requestBody.getEndTime());

        PersonalCalender personalCalender = PersonalCalender.builder()
                .title(requestBody.getTitle())
                .location(requestBody.getLocation())
                .open(requestBody.getOpen())
                .alarmType(requestBody.getAlarmType())
                .day(dateUtil.localDateTimeToDay(startTime))
                .build();

        return JwtContextHolder.getMonoUserId().flatMap(userId -> {
            personalCalender.setUserId(userId);
            return personalCalenderRepository.save(personalCalender);
        })
        .flatMap(entity -> {
            personalCalenderId.set(entity.getId());
            PersonalCalenderJoinUser e = new PersonalCalenderJoinUser();
            e.setAlarmType(requestBody.getAlarmType());
            e.setUserId(entity.getUserId());
            e.setAccept((byte) 1);
            e.setPersonalCalenderId(entity.getId());
            return personalCalenderJoinUserRepository.save(e);
        })
        .flatMap(entity -> {
            Calender calender = Calender.builder()
                    .personalCalenderId(personalCalenderId.get())
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();
            return calenderRepository.save(calender);
        })
        .map(entity -> new BaseExtentionResponse<>(personalCalenderId.get()))
        ;
    }

    public Mono<BaseResponse> invitePersonalCalender(InvitePersonalRequest requestBody) {
        var personalCalenderUserList = new ArrayList<PersonalCalenderJoinUser>();

        requestBody.getUserList().forEach(id -> {
            var entity = new PersonalCalenderJoinUser();
            entity.setPersonalCalenderId(requestBody.getPersonalCalenderId());
            entity.setUserId(id);
            personalCalenderUserList.add(entity);
        });

        return this.personalCalenderJoinUserRepository.saveAll(personalCalenderUserList)
                .collectList()
                .map(list -> new BaseResponse());
    }

    public Mono<BaseResponse> removePersonalCalender(Long personalCalenderId) {
        return personalCalenderRepository.deleteById(personalCalenderId)
                .thenReturn(new BaseResponse());
    }

    public Mono<BaseResponse> acceeptPersonalCalender(AcceptPersonalCalenderRequest requestBody) {
        return JwtContextHolder.getMonoUserId().flatMap(userId ->
                personalCalenderJoinUserRepository.findByUserIdAndPersonalCalenderId(userId, requestBody.getPersonalCalenderId()))
                .flatMap(entity -> {
                    entity.setAccept(requestBody.getAccept() ? (byte) 1 : (byte) 0);
                    entity.setAlarmType(requestBody.getAlarmType());
                    if (entity.getContent() != null)
                        entity.setContent(requestBody.getContent());
                    return personalCalenderJoinUserRepository.save(entity);
                }).map(entity -> new BaseResponse());
    }

    public Mono<BaseResponse> modifyPersonalCalenderInfo(ModifyPersonalCalenderRequest requestBody) {
        return personalCalenderRepository.findById(requestBody.getPersonalCalenderId())
                .flatMap(entity -> {
                    entity.setTitle(requestBody.getTitle());
                    entity.setLocation(requestBody.getLocation());
                    entity.setOpen(requestBody.getOpen());
                    entity.setAlarmType(requestBody.getAlarmType());
                    return personalCalenderRepository.save(entity);
                })
                .map(entity -> new BaseResponse());
    }

    public Mono<BaseExtentionResponse<List<MyPersonalCalenderListResponse>>> myPersonalCalenderList(
            Long id, Boolean accept, Boolean open) {
        return JwtContextHolder.getMonoUserId()
                .flatMapMany(userId -> personalCalenderDAO.findMyPersonalCalenderList(userId, id, accept, open))
                .collectList()
                .map(BaseExtentionResponse::new)
                ;
    }

    public Mono<BaseExtentionResponse<List<SearchPersonalCalenderListResponse>>> searchPersonalCalenderList(Long personalCalenderId, Integer perPage, String title) {
        return personalCalenderDAO.searchPersonalCalenderList(personalCalenderId, perPage, title)
                .collectList()
                .map(BaseExtentionResponse::new);
    }

    public Mono<BaseExtentionResponse<PersonalCalenderDetailResponse>> findPersonalCalenderDetail(Long id) {
        AtomicLong atomicUserId = new AtomicLong();
        return JwtContextHolder.getMonoUserId().flatMap(userId -> {
            atomicUserId.set(userId);
            return personalCalenderDAO.findPersonalCalenderDetail(id);
        }).flatMap(personalCalenderDetailResponse ->
                personalCalenderJoinUserDAO.findPersonalCalenderJoinUserInfo(id)
                .collectList()
                .map(list -> {
                    int acceptUserCount = 0;
                    boolean isAccept = false;

                    for (PersonalCalenderJoinUserInfoResponse joinUser : list) {
                        if (joinUser.getAccept() == 1) {
                            acceptUserCount++;
                        }

                        if (joinUser.getUserId() == atomicUserId.get()) {
                            isAccept = joinUser.getAccept() == 1;
                        }
                    }
                    personalCalenderDetailResponse.setIsAccept(isAccept);
                    personalCalenderDetailResponse.setAcceptUserCount(acceptUserCount);
                    personalCalenderDetailResponse.setInviteUserCount(list.size());
                    personalCalenderDetailResponse.setJoinUserList(list);

                    return new BaseExtentionResponse<>(personalCalenderDetailResponse);
                })
        );
    }

    public Mono<BaseExtentionResponse<Integer>> findPersonalInviteCount() {
        return JwtContextHolder.getMonoUserId().flatMap(userId ->
                personalCalenderJoinUserRepository.countByUserIdAndAccept(userId, 2))
                .map(BaseExtentionResponse::new);
    }

    public Mono<BaseExtentionResponse<List<PersonalCalenderInviteResponse>>> findPersonalInvite() {
        return JwtContextHolder.getMonoUserId().flatMap(userId ->
                personalCalenderJoinUserDAO.findPersonalInvite(userId, 2)
                        .flatMap(entity ->
                                personalCalenderJoinUserRepository.countByPersonalCalenderIdAndAccept(entity.getId(), 1)
                                        .map(e -> {
                                            entity.setAcceptUserCount(e - 1);
                                            return entity;
                                        })
                        ).collectList()
        ).map(BaseExtentionResponse::new);
    }
}
