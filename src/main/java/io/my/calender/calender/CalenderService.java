package io.my.calender.calender;

import io.my.calender.base.context.JwtContextHolder;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.base.repository.CalenderRepository;
import io.my.calender.base.repository.dao.CalenderDAO;
import io.my.calender.base.util.DateUtil;
import io.my.calender.calender.payload.request.ModifyCalender;
import io.my.calender.calender.payload.response.CalenderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalenderService {
    private final DateUtil dateUtil;
    private final CalenderDAO calenderDAO;
    private final CalenderRepository calenderRepository;

    public Mono<BaseExtentionResponse<List<CalenderListResponse>>> getCalender(Long userId, String type, Long day, Integer year, Integer month) {
        if (userId == null) {
            return JwtContextHolder.getMonoUserId().flatMap(id -> this.getCalenders(id, type, day, year, month));
        } else {
            return this.getCalenders(userId, type, day, year, month);
        }
    }

    private Mono<BaseExtentionResponse<List<CalenderListResponse>>> getCalenders(Long userId, String type, Long day, Integer year, Integer month) {
        LocalDate date = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate;

        if (type.equals("week")) {
            date = getDate(date, day);
            startDate = dateUtil.findWeekStart(date);
            endDate = dateUtil.findWeekEnd(date).plusDays(1);
        } else if (type.equals("month")) {
            date = getDate(date, year, month);
            startDate = dateUtil.findMonthStart(date);
            endDate = dateUtil.findMonthEnd(date).plusDays(1);
        } else {
            throw new IllegalArgumentException();
        }
        return calenderDAO.findCalenderListResponse(userId, startDate, endDate)
                .switchIfEmpty(Flux.empty())
                .collectList()
                .map(list -> {
                    var responseBody = new BaseExtentionResponse<List<CalenderListResponse>>();
                    responseBody.setReturnValue(list);
                    return responseBody;
                });
    }

    private LocalDate getDate(LocalDate date, Long day) {
        if (day == null) return date;
        return dateUtil.unixTimeToLocalDateTime(day).toLocalDate();
    }

    private LocalDate getDate(LocalDate date, Integer year, Integer month) {
        if (month == null) return date;

        if (year != null) {
            date = LocalDate.of(year, month, 1);
        } else {
            date = date.withMonth(month);
        }
        return date;
    }

    public Mono<BaseResponse> modifyCalender(ModifyCalender requestBody) {
        return calenderRepository.findById(requestBody.getId()).flatMap(entity -> {
            entity.setStartTime(
                    dateUtil.unixTimeToLocalDateTime(requestBody.getStartTime())
            );
            entity.setEndTime(
                    dateUtil.unixTimeToLocalDateTime(requestBody.getEndTime())
            );
            return calenderRepository.save(entity);
        })
        .map(entity -> new BaseResponse())
        ;
    }

    public Mono<BaseResponse> removeCalender(Long id) {
        return calenderRepository.deleteById(id).thenReturn(new BaseResponse());
    }
}
