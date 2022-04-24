package io.my.calender.personel;

import io.my.calender.base.context.JwtContextHolder;
import io.my.calender.base.entity.Calender;
import io.my.calender.base.entity.PersonelCalender;
import io.my.calender.base.entity.PersonelCalenderJoinUser;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.base.repository.CalenderRepository;
import io.my.calender.base.repository.PersonelCalenderJoinUserRepository;
import io.my.calender.base.repository.PersonelCalenderRepository;
import io.my.calender.base.repository.dao.PersonelCalenderDAO;
import io.my.calender.base.util.DateUtil;
import io.my.calender.personel.payload.request.AcceptPersoneCalenderRequest;
import io.my.calender.personel.payload.request.CreatePersonelRequest;
import io.my.calender.personel.payload.request.InvitePersonelRequest;
import io.my.calender.personel.payload.request.ModifyPersonelCalenderRequest;
import io.my.calender.personel.payload.response.MyPersonelCalenderListResponse;
import io.my.calender.personel.payload.response.SearchPersonelCalenderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonelService {
    private final DateUtil dateUtil;
    private final CalenderRepository calenderRepository;
    private final PersonelCalenderDAO personelCalenderDAO;
    private final PersonelCalenderRepository personelCalenderRepository;
    private final PersonelCalenderJoinUserRepository personelCalenderJoinUserRepository;

    public Mono<BaseResponse> createPersonelCalender(
            CreatePersonelRequest requestBody) {

        LocalDateTime startTime = dateUtil.unixTimeToLocalDateTime(requestBody.getStartTime());
        LocalDateTime endTime = dateUtil.unixTimeToLocalDateTime(requestBody.getEndTime());

        PersonelCalender personelCalender = PersonelCalender.builder()
                .title(requestBody.getTitle())
                .location(requestBody.getLocation())
                .open(requestBody.getOpen())
                .alarmType(requestBody.getAlarmType())
                .day(dateUtil.localDateTimeToDay(startTime))
                .build();

        return JwtContextHolder.getMonoUserId().flatMap(userId -> {
            personelCalender.setUserId(userId);
            return personelCalenderRepository.save(personelCalender);
        })
        .flatMap(entity -> {
            Calender calender = Calender.builder()
                    .personelCalenderId(entity.getId())
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();
            return calenderRepository.save(calender);
        })
        .map(entity -> new BaseResponse())
        ;
    }

    public Mono<BaseResponse> invitePersonelCalender(InvitePersonelRequest requestBody) {
        var personelCalenderUserList = new ArrayList<PersonelCalenderJoinUser>();

        requestBody.getUserList().forEach(id -> {
            var entity = new PersonelCalenderJoinUser();
            entity.setPersonelCalenderId(requestBody.getPersonelCalenderId());
            entity.setUserId(id);
            personelCalenderUserList.add(entity);
        });

        return this.personelCalenderJoinUserRepository.saveAll(personelCalenderUserList)
                .collectList()
                .map(list -> new BaseResponse());
    }

    public Mono<BaseResponse> removePersonelCalender(Long personelCalenderId) {
        return personelCalenderRepository.deleteById(personelCalenderId)
                .thenReturn(new BaseResponse());
    }

    public Mono<BaseResponse> acceeptPersonelCalender(AcceptPersoneCalenderRequest requestBody) {
        return JwtContextHolder.getMonoUserId().flatMap(userId ->
                personelCalenderJoinUserRepository.findByUserIdAndPersonelCalenderId(userId, requestBody.getPersonelCalenderId()))
                .flatMap(entity -> {
                    entity.setAccept(requestBody.getAccept() ? (byte) 1 : (byte) 0);
                    entity.setAlarmType(requestBody.getAlarmType());
                    entity.setContent(requestBody.getContent());
                    return personelCalenderJoinUserRepository.save(entity);
                }).map(entity -> new BaseResponse());
    }

    public Mono<BaseResponse> modifyPersonelCalenderInfo(ModifyPersonelCalenderRequest requestBody) {
        return personelCalenderRepository.findById(requestBody.getPersonelCalenderId())
                .flatMap(entity -> {
                    entity.setTitle(requestBody.getTitle());
                    entity.setLocation(requestBody.getLocation());
                    entity.setOpen(requestBody.getOpen());
                    entity.setAlarmType(requestBody.getAlarmType());
                    return personelCalenderRepository.save(entity);
                })
                .map(entity -> new BaseResponse());
    }

    public Mono<BaseExtentionResponse<List<MyPersonelCalenderListResponse>>> myPersonelCalenderList(
            Long id, Boolean accept, Boolean open) {
        return JwtContextHolder.getMonoUserId()
                .flatMapMany(userId -> personelCalenderDAO.findMyPersonelCalenderList(userId, id, accept, open))
                .collectList()
                .map(BaseExtentionResponse::new)
                ;
    }

    public Mono<BaseExtentionResponse<List<SearchPersonelCalenderListResponse>>> searchPersonelCalenderList(Long personelCalenderId, Integer perPage, String title) {
        return personelCalenderDAO.searchPersonelCalenderList(personelCalenderId, perPage, title)
                .collectList()
                .map(BaseExtentionResponse::new);
    }
}
