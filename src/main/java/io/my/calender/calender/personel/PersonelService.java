package io.my.calender.calender.personel;

import io.my.calender.base.context.JwtContextHolder;
import io.my.calender.base.entity.Calender;
import io.my.calender.base.entity.PersonelCalender;
import io.my.calender.base.entity.PersonelCalenderJoinUser;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.base.repository.CalenderRepository;
import io.my.calender.base.repository.PersonelCalenderJoinUserRepository;
import io.my.calender.base.repository.PersonelCalenderRepository;
import io.my.calender.base.util.DateUtil;
import io.my.calender.calender.personel.payload.request.AcceptPersoneCalenderRequest;
import io.my.calender.calender.personel.payload.request.CreatePersonelRequest;
import io.my.calender.calender.personel.payload.request.InvitePersonelRequest;
import io.my.calender.calender.personel.payload.request.ModifyPersonelCalenderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PersonelService {
    private final DateUtil dateUtil;
    private final CalenderRepository calenderRepository;
    private final PersonelCalenderRepository personelCalenderRepository;
    private final PersonelCalenderJoinUserRepository personelCalenderJoinUserRepository;

    public Mono<BaseResponse> createPersonelCalender(
            CreatePersonelRequest requestBody) {

        PersonelCalender personelCalender = new PersonelCalender();
        personelCalender.setTitle(requestBody.getTitle());
        personelCalender.setContent(requestBody.getContent());
        personelCalender.setLocation(requestBody.getLocation());
        personelCalender.setOpen(requestBody.getOpen());

        LocalDateTime startTime = dateUtil.unixTimeToLocalDateTime(requestBody.getStartTime());
        LocalDateTime endTime = dateUtil.unixTimeToLocalDateTime(requestBody.getEndTime());
        personelCalender.setDay(dateUtil.localDateTimeToDay(startTime));

        return JwtContextHolder.getMonoUserId().flatMap(userId -> {
            personelCalender.setUserId(userId);
            return personelCalenderRepository.save(personelCalender);
        })
        .flatMap(entity -> {
            Calender calender = new Calender();
            calender.setPersonelCalenderId(entity.getId());
            calender.setStartTime(startTime);
            calender.setEndTime(endTime);
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
                .map(o -> new BaseResponse());
    }

    public Mono<BaseResponse> acceeptPersonelCalender(AcceptPersoneCalenderRequest requestBody) {
        return JwtContextHolder.getMonoUserId().flatMap(userId ->
                personelCalenderJoinUserRepository.findByUserIdAndPersonelCalenderId(userId, requestBody.getPersonelCalenderId()))
                .flatMap(entity -> {
                    entity.setAccept(requestBody.getAccept());
                    return personelCalenderJoinUserRepository.save(entity);
                }).map(entity -> new BaseResponse());
    }

    public Mono<BaseResponse> modifyPersonelCalenderInfo(ModifyPersonelCalenderRequest requestBody) {
        return personelCalenderRepository.findById(requestBody.getPersonelCalenderId())
                .flatMap(entity -> {
                    entity.setTitle(requestBody.getTitle());
                    entity.setContent(requestBody.getContent());
                    entity.setLocation(requestBody.getLocation());
                    entity.setOpen(requestBody.getOpen());
                    return personelCalenderRepository.save(entity);
                })
                .map(entity -> new BaseResponse());
    }
}
