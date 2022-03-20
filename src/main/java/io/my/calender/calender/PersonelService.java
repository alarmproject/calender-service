package io.my.calender.calender;

import io.my.calender.base.context.JwtContextHolder;
import io.my.calender.base.entity.Calender;
import io.my.calender.base.entity.PersonelCalender;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.base.repository.CalenderRepository;
import io.my.calender.base.repository.PersonelCalenderJoinUserRepository;
import io.my.calender.base.repository.PersonelCalenderRepository;
import io.my.calender.base.util.DateUtil;
import io.my.calender.calender.payload.request.personel.CreatePersonelRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

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
}
